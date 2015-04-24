package de.bitbrain.craft.ui;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.animations.Animations.AnimationType;
import de.bitbrain.craft.graphics.GraphicsFactory;
import de.bitbrain.craft.util.Fadeable;

public class LoadingIndicator extends Actor implements Fadeable, Disposable {

  private static final float TIME = 0.08f;

  private static final int PADDING = 5;

  private List<Sprite> blocks;

  private TweenManager tweenManager;

  private float alpha = 0.1f;

  public LoadingIndicator(TweenManager tweenManager) {
    this.tweenManager = tweenManager;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    parentAlpha *= alpha;
    if (blocks == null) {
      blocks = new ArrayList<Sprite>();
      Texture texture = GraphicsFactory.createTexture(8, 8, Assets.CLR_YELLOW_SAND);
      for (int i = 0; i < 8; ++i) {
        Sprite block = new Sprite(texture);
        block.setAlpha(0f);
        Tween.from(block, AnimationType.ALPHA.ordinal(), TIME * 7).target(1f).delay(TIME * (8 - i)).repeat(Tween.INFINITY, 0f)
            .ease(TweenEquations.easeOutQuad).start(tweenManager);
        blocks.add(block);
      }
      Tween.to(this, AnimationType.ALPHA.ordinal(), TIME * 8).target(0.7f).repeatYoyo(Tween.INFINITY, 0f)
          .ease(TweenEquations.easeInOutQuad).start(tweenManager);
    }
    final int BLOCK_SIZE = (int) (getWidth() / 3f);
    // BOTTOM ROW
    for (int i = 0; i < 3; ++i) {
      Sprite block = blocks.get(i);
      block.setBounds(getX() + i * BLOCK_SIZE + PADDING, getY() + PADDING, BLOCK_SIZE - PADDING * 2, BLOCK_SIZE
          - PADDING * 2);
      block.draw(batch, parentAlpha);
    }
    // RIGHT
    Sprite right = blocks.get(3);
    right.setBounds(getX() + BLOCK_SIZE * 2 + PADDING, getY() + BLOCK_SIZE + PADDING, BLOCK_SIZE - PADDING * 2,
        BLOCK_SIZE - PADDING * 2);
    right.draw(batch, parentAlpha);
    // LEFT
    Sprite left = blocks.get(7);
    left.setBounds(getX() + PADDING, getY() + BLOCK_SIZE + PADDING, BLOCK_SIZE - PADDING * 2, BLOCK_SIZE - PADDING * 2);
    left.draw(batch, parentAlpha);
    // TOP ROW
    for (int i = 4; i < 7; ++i) {
      Sprite block = blocks.get(i);
      block.setBounds(getX() + (6 - i) * BLOCK_SIZE + PADDING, getY() + PADDING + getHeight() - BLOCK_SIZE, BLOCK_SIZE
          - PADDING * 2, BLOCK_SIZE - PADDING * 2);
      block.draw(batch, parentAlpha);
    }
  }

  @Override
  public float getAlpha() {
    return alpha;
  }

  @Override
  public void setAlpha(float alpha) {
    this.alpha = alpha;
  }

  @Override
  public void dispose() {
    if (!blocks.isEmpty()) {
      Texture texture = blocks.get(0).getTexture();
      texture.dispose();
      blocks.clear();
    }

  }

}
