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

package de.bitbrain.craft.graphics;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;

import de.bitbrain.craft.SharedAssetManager;

/**
 * Manages particle effect rendering
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ParticleRenderer {
    
    private Map<ParticleEffect, Boolean> effects;
    
    public ParticleRenderer() {
            effects = new ConcurrentHashMap<ParticleEffect, Boolean>();
    }
    
    public ParticleEffect create(String particleResource) {
    	return create(particleResource, false);
    }
    
    public ParticleEffect create(String particleResource, boolean endless) {
    		ParticleEffect original = SharedAssetManager.get(particleResource, ParticleEffect.class);
            ParticleEffect copy = new ParticleEffect(original);                
            effects.put(copy, endless);
            return copy;
    }
    
    public void setColor(ParticleEffect effect, float[] colors, float[] timeline) {
            for (ParticleEmitter emitter : effect.getEmitters()) {
                    emitter.getTint().setTimeline(timeline);
                    emitter.getTint().setColors(colors);
            }
    }
    
    public void setParticleCount(ParticleEffect effect, int count) {
            for (ParticleEmitter emitter : effect.getEmitters()) {
                    emitter.setMaxParticleCount(count);
            }
    }
    
    public int getParticleCount(ParticleEffect effect) {
            
            int count = 0;
            
            for (ParticleEmitter emitter : effect.getEmitters()) {
                    if (count < emitter.getMaxParticleCount()) {
                            count = emitter.getMaxParticleCount();
                    }
            }
            
            return count;
    }
    
    public void render(Batch batch, float delta) {
            
            for (Entry<ParticleEffect, Boolean> entries : effects.entrySet()) {
                    
                    if (!entries.getValue() && entries.getKey().isComplete()) {
                            ParticleEffect effect = entries.getKey();
                            effects.remove(effect);
                    } else {                                
                            entries.getKey().draw(batch, delta);
                    }
            }
    }
    
    public void unload(ParticleEffect effect) {
            effects.remove(effect);
    }
    
    public void setEndless(ParticleEffect effect, boolean endless) {
            
            if (effect != null) {
                    effects.put(effect, endless);
                    
                    for (ParticleEmitter emitter : effect.getEmitters()) {
                            emitter.setContinuous(endless);
                    }
            }
    }
    
    public void clear() {
            for (Entry<ParticleEffect, Boolean> entries : effects.entrySet()) {
                    entries.getKey().setDuration(0);
            }
            
            effects.clear();
    }
}