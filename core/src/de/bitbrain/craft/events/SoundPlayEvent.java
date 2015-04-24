package de.bitbrain.craft.events;

import com.badlogic.gdx.audio.Sound;

public class SoundPlayEvent extends Event<Sound> {

  public static final String PITCH = "pitch";
  public static final String VOLUME = "volume";
  public static final String PAN = "pan";

  public SoundPlayEvent(Sound model, float volume, float pitch, float pan) {
    super(EventType.PLAY, model);
    setParam(VOLUME, volume);
    setParam(PITCH, pitch);
    setParam(PAN, pan);
  }

}
