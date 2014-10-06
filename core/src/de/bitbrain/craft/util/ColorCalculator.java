/*
 * Craft - Crafting game for Android, PC and Browser.
 * Copyright (C) 2014 Miguel Gonzalez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package de.bitbrain.craft.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.google.common.primitives.Doubles;
import com.google.common.util.concurrent.AtomicLongMap;

/**
 * Provides color calculation for images, based on:
 * http://charlesleifer.com/blog/using-python-and-k-means-to-find-the-dominant-colors-in-images/
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ColorCalculator {
	
	public Color getColor(Texture texture) {
		Color color = new Color(Color.WHITE);
		TextureData data = texture.getTextureData();
		data.prepare();
		Pixmap map = data.consumePixmap();		
		try {
			Result result = analyseColors(map);
			color = result.color;
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.dispose();
		
		return color;
	}
	
	private static Result analyseColors(Pixmap map) throws IOException {
       
        int height = map.getHeight();
        int width = map.getWidth();

        Map<Color, Double> colorDist = new HashMap<Color, Double>();
        AtomicLongMap<Color> colorCount = AtomicLongMap.create();

        final int alphaThershold = 10;
        long pixelCount = 0;
        long avgAlpha = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = map.getPixel(x, y);
                int[] rgbArr = getRGBArr(rgb);

                if (rgbArr[0] <= alphaThershold)
                   continue; //ignore

                avgAlpha += rgbArr[0];

                Color clr = new Color(rgbArr[1], rgbArr[2], rgbArr[3], 1f);
                colorCount.getAndIncrement(clr);
                if (!colorDist.containsKey(clr)) {
                    double dist = 0.0d;

                    for (int y2 = 0; y2 < height; y2++) {
                        for (int x2 = 0; x2 < width; x2++) {
                            int rgb2 = map.getPixel(x2, y2);
                            int[] rgbArr2 = getRGBArr(rgb2);

                            if (rgbArr2[0] <= alphaThershold)
                                continue; //ignore

                            dist += Math.sqrt(Math.pow((double) (rgbArr2[1] - rgbArr[1]), 2) +
                                              Math.pow((double) (rgbArr2[2] - rgbArr[2]), 2) +
                                              Math.pow((double) (rgbArr2[3] - rgbArr[3]), 2));
                        } // for-x2
                    } // inner for-y2 loop

                    colorDist.put(clr, dist);
                }
                pixelCount++;
            } // for-x
        } // outer for-y loop

        // clamp alpha
        avgAlpha = avgAlpha / pixelCount;
        if (avgAlpha >= (255 - alphaThershold))
            avgAlpha = 255;

        // sort RGB distances
        ValueComparator bvc = new ValueComparator(colorDist);
        TreeMap<Color, Double> sorted_map = new TreeMap<Color, Double>(bvc);
        sorted_map.putAll(colorDist);

        // take weighted average of top 2% colors
        double threshold = 0.02;
        int nrToThreshold = Math.max(1, (int)(colorDist.size() * threshold));
        int mostThreshold = Math.max(1, (int)(colorDist.size() * 0.8));
        Map<Color, Double> clrsDist = new HashMap<Color, Double>();
        java.util.List<Double> topDist = new ArrayList<Double>();
        java.util.List<Double> mostDist = new ArrayList<Double>();
        java.util.List<Double> allDist = new ArrayList<Double>();
        int i = 0;
        for (Map.Entry<Color, Double> e : sorted_map.entrySet()) {
            Double distance = 1.0d / Math.max(1.0, e.getValue());
            if (i < nrToThreshold) {
                Color clr = e.getKey();
                clrsDist.put(clr, distance);
                topDist.add(e.getValue());
            }
            if (i < mostThreshold) {
                mostDist.add(e.getValue());
            }
            allDist.add(e.getValue());
            i++;
        }

        // calculate statistics
        double[] allDistsArr = Doubles.toArray(allDist);
        double allDistsMean = StatUtils.mean(allDistsArr);
        double allDistsVariance = StatUtils.variance(allDistsArr);

        Skewness skewness = new Skewness();
        double skAll = skewness.evaluate(allDistsArr);
        double[] target = new double[mostDist.size()];
        for (int d = 0; d < target.length; d++) {
           target[d] = mostDist.get(d).doubleValue();
        }
        double skMost = skewness.evaluate(target);
        Kurtosis kurtosis = new Kurtosis();
        double kurtAll = kurtosis.evaluate(allDistsArr);
        target = new double[mostDist.size()];
        for (int d = 0; d < target.length; d++) {
           target[d] = mostDist.get(d).doubleValue();
        }
        double kurtMost = kurtosis.evaluate(target);
        target = new double[topDist.size()];
        for (int d = 0; d < target.length; d++) {
           target[d] = mostDist.get(d).doubleValue();
        }
        double[] topDistsArr = target;
        double topDistsMean = StatUtils.mean(topDistsArr);
        double topDistsVariance = StatUtils.variance(topDistsArr);

        //
        double sumDist = 0.0d;
        double sumR = 0.0d;
        double sumG = 0.0d;
        double sumB = 0.0d;
        for (Map.Entry<Color,Double> e : clrsDist.entrySet()) {
            sumR += e.getKey().r * e.getValue();
            sumG += e.getKey().g * e.getValue();
            sumB += e.getKey().b * e.getValue();
            sumDist += e.getValue();
        }
        Color dominantColor = new Color((int) (sumR / sumDist),
                                        (int) (sumG / sumDist),
                                        (int) (sumB / sumDist), 1f);

        return new Result(dominantColor, allDistsMean, topDistsMean, skAll, skMost, kurtAll, kurtMost, nrToThreshold, colorDist.size());
    }





    private static int[] getRGBArr(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{alpha, red, green, blue};
    }


    static class Result {

        public final Color color;
        public final double allMean;
        public final double topMean;
        public final int nrToThreshold;
        public final int nrColors;

        // Skewness:    http://en.wikipedia.org/wiki/Skewness
        //              http://de.wikipedia.org/wiki/Schiefe_(Statistik)
        public double skAll;
        public double skMost;

        // Kurtosis: http://en.wikipedia.org/wiki/Kurtosis
        //           http://de.wikipedia.org/wiki/W%C3%B6lbung_(Statistik)
        public double kurtAll;
        public double kurtMost;

        public Result(Color color, double allMean, double topMean, double skAll, double skMost, double kurtAll, double kurtMost, int nrToThreshold, int nrColors) {
            this.color = color;
            this.allMean = allMean;
            this.topMean = topMean;
            this.skAll = skAll;
            this.skMost = skMost;
            this.kurtAll = kurtAll;
            this.kurtMost = kurtMost;
            this.nrToThreshold = nrToThreshold;
            this.nrColors = nrColors;
        }

        public String getHexCode() {
            return String.format("%1$02x%2$02x%3$02x",
                    color.r,
                    color.g,
                    color.b);
        }

    }


    static class ValueComparator implements Comparator {

        Map<Color, Double> base;

        public ValueComparator(Map<Color, Double> base) {
            this.base = base;
        }

        public int compare(Object a, Object b) {
            if (base.get(a) < base.get(b)) {
                return -1;
            } else if (base.get(a) == base.get(b)) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
