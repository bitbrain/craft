package de.bitbrain.craft.events;

import com.badlogic.gdx.audio.Sound;

public class SoundPlayEvent extends Event<Sound> {

	public SoundPlayEvent(Sound model, float volume, float pitch, float pan) {
		super(EventType.PLAY, model, volume, pitch, pan);
	}

}
