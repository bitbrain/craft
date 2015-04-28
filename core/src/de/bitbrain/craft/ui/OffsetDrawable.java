package de.bitbrain.craft.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class OffsetDrawable implements Drawable {

  private Drawable drawable;

  private Vector2 offset;

  public OffsetDrawable(Drawable drawable) {
    this.drawable = drawable;
    offset = new Vector2();
  }

  @Override
  public void draw(Batch batch, float x, float y, float width, float height) {
    drawable.draw(batch, x + offset.x, y + offset.y, width, height);
  }

  public void setOffset(float x, float y) {
    setOffsetX(x);
    setOffsetY(y);
  }

  public void setOffsetX(float x) {
    offset.x = x;
  }

  public void setOffsetY(float y) {
    offset.y = y;
  }

  @Override
  public float getLeftWidth() {
    return drawable.getLeftWidth();
  }

  @Override
  public void setLeftWidth(float leftWidth) {
    drawable.setLeftWidth(leftWidth);
  }

  @Override
  public float getRightWidth() {
    return drawable.getRightWidth();
  }

  @Override
  public void setRightWidth(float rightWidth) {
    drawable.setRightWidth(rightWidth);
  }

  @Override
  public float getTopHeight() {
    return drawable.getTopHeight();
  }

  @Override
  public void setTopHeight(float topHeight) {
    drawable.setTopHeight(topHeight);
  }

  @Override
  public float getBottomHeight() {
    return drawable.getBottomHeight();
  }

  @Override
  public void setBottomHeight(float bottomHeight) {
    drawable.setBottomHeight(bottomHeight);
  }

  @Override
  public float getMinWidth() {
    return drawable.getMinWidth();
  }

  @Override
  public void setMinWidth(float minWidth) {
    drawable.setMinWidth(minWidth);
  }

  @Override
  public float getMinHeight() {
    return drawable.getMinHeight();
  }

  @Override
  public void setMinHeight(float minHeight) {
    drawable.setMinHeight(minHeight);
  }

}
