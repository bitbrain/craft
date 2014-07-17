package de.bitbrain.craft.audio;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;

public class ButtonSoundListener extends ClickListener {
	
	private final String soundID;
	
	private float pitch = 1.5f;
	
	public ButtonSoundListener() {
		this(Assets.SOUND_BUTTON_01);
	}
	
	public ButtonSoundListener(String soundID) {
		this.soundID = soundID;
	}
	
	public ButtonSoundListener(float pitch) {
		this();
		this.pitch = pitch;
	}

	@Override
	public void clicked(InputEvent event, float x, float y) {
		super.clicked(event, x, y);
		
		Sound s = SharedAssetManager.get(soundID, Sound.class);
		s.play(0.4f, pitch, 1.0f);
	}
}
