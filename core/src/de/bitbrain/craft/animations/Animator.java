package de.bitbrain.craft.animations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.bitbrain.craft.inject.StateScoped;

@StateScoped
public class Animator {
  
  private Map<String, AnimationData> animations = new HashMap<String, AnimationData>();
  
  
  /**
   * Returns the ID which can be referred to this particular animation
   * 
   * @param texture
   * @param interval
   * @param mode
   * @return
   */
  public String register(Texture texture, float interval, PlayMode mode) {
    AnimationData data = new AnimationData();
    String id = UUID.randomUUID().toString();    
    Animation animation = new Animation(interval, prepareFrames(texture));
    animation.setPlayMode(mode);
    data.animation = animation;
    animations.put(id, data);    
    return id;
  }
  
  public TextureRegion getRegion(String id) {
    AnimationData data = animations.get(id);
    if (data != null) {
      return data.animation.getKeyFrame(data.time);
    } else {
      return new TextureRegion();
    }
  }
  
  public void unregister(String id) {
    animations.remove(id);
  }
  
  public void clear() {
    animations.clear();
  }
  
  public void act(float delta) {
    for (AnimationData data : animations.values()) {
      data.time += delta;
    }
  }
  
  private TextureRegion[] prepareFrames(Texture texture) {
    List<TextureRegion> regions = new ArrayList<TextureRegion>();
    int frames = (int) Math.floor(texture.getWidth() / texture.getHeight());
    for (int i = 0; i < frames; ++i) {
      TextureRegion region =
          new TextureRegion(texture, i * texture.getHeight(), 0, texture.getHeight(), texture.getHeight());
      regions.add(region);
    }
    return regions.toArray(new TextureRegion[regions.size()]);
  }
  
  private class AnimationData {
    
    public Animation animation;
    
    public float time;
  }

}
