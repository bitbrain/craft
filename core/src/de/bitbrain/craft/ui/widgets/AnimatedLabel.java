package de.bitbrain.craft.ui.widgets;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.bitbrain.craft.animations.IntegerValueTween;
import de.bitbrain.craft.animations.TweenAnimations.TweenType;
import de.bitbrain.craft.util.IntegerValueProvider;

public class AnimatedLabel extends Label implements IntegerValueProvider {

  public static class AnimatedLabelStyle extends LabelStyle {

    public int textSpeed = 12;
    
    public AnimatedLabelStyle(LabelStyle original) {
      background = original.background;
      font = original.font;
      fontColor = original.fontColor;
    }
    
    public AnimatedLabelStyle() { }
  }

  private int pointer;
  
  private CharSequence fullText;
  
  static {
    Tween.registerAccessor(AnimatedLabel.class, new IntegerValueTween());
  }

  public AnimatedLabel(CharSequence text, AnimatedLabelStyle style, TweenManager tweenManager) {
    super(text, style);
    this.fullText = text;
    Tween.to(this, TweenType.VALUE.ordinal(), 10f / style.textSpeed).target(text.length())
        .ease(TweenEquations.easeNone).start(tweenManager);
    super.setText(text);
  }
  
  @Override
  public void draw(Batch batch, float parentAlpha) {
    setText(fullText.subSequence(0, pointer));
    super.draw(batch, parentAlpha);
    setText(fullText);
  }

  @Override
  public int getValue() {
    return pointer;
  }

  @Override
  public void setValue(int value) {
    pointer = value;
  }
  
}
