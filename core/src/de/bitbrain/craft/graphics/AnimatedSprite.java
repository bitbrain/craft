package de.bitbrain.craft.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.google.inject.Inject;

import de.bitbrain.craft.animations.Animator;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.inject.SharedInjector;

public class AnimatedSprite extends Sprite {
  
  @Inject
  private Animator animator;
  
  private Texture texture;
  
  private float time;
  
  private PlayMode mode;
  
  private String animationId;
  
  public AnimatedSprite(Texture texture, float time, PlayMode mode) {
    this.texture = texture;
    this.time = time;
    this.mode = mode;
    SharedInjector.get().injectMembers(this);
  }
  
  @PostConstruct
  public void initView() {
    animationId = animator.register(texture, time, mode);
  }
  
  @Override
  public void draw(Batch batch, float alphaModulation) {
    updateRegion();
    super.draw(batch, alphaModulation);
  }
  
  @Override
  public void draw(Batch batch) {
    updateRegion();
    super.draw(batch);
  }
  
  private void updateRegion() {
    setRegion(animator.getRegion(animationId));
  }
}
