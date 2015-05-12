package de.bitbrain.craft.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.models.Profession;

public class Professor {

  private AnimatedSprite sprite;

  public Professor(Profession profession) {
    sprite =
        new AnimatedSprite(SharedAssetManager.get(profession.getIcon(), Texture.class), 0.2f, PlayMode.LOOP_RANDOM);
  }

  public Sprite getSprite() {
    return sprite;
  }

  public void setSize(float width, float height) {
    sprite.setSize(width, height);
  }

  public void setOrigin(float x, float y) {
    sprite.setOrigin(x, y);
  }

  public void setColor(Color color) {
    sprite.setColor(color);
  }

  public void setPosition(float x, float y) {
    sprite.setPosition(x, y);
  }

  public void draw(Batch batch, float alpha) {
    sprite.draw(batch, alpha);
  }

  public float getWidth() {
    return sprite.getWidth();
  }

  public float getHeight() {
    return sprite.getHeight();
  }

  public void setX(float x) {
    sprite.setX(x);
  }

  public void setY(float y) {
    sprite.setY(y);
  }

}
