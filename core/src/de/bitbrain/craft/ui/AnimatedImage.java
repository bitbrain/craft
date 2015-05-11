package de.bitbrain.craft.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.bitbrain.craft.graphics.AnimatedDrawable;

public class AnimatedImage extends Image {

  private final AnimatedDrawable drawable;

  public AnimatedImage(Texture texture, float frameTick) {
    super(new AnimatedDrawable(new Animation(frameTick, new TextureRegion(texture))));
    this.drawable = (AnimatedDrawable) getDrawable();
  }

  @Override
  public void act(float delta) {
    drawable.act(delta);
    super.act(delta);

  }
}
