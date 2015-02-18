/*
 * Craft - Crafting game for Android, PC and Browser.
 * Copyright (C) 2014 Miguel Gonzalez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package de.bitbrain.craft.models;

import de.bitbrain.craft.audio.SoundType;
import de.bitbrain.jpersis.annotations.PrimaryKey;

/**
 * Item sound which can be played
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class SoundConfig {

	@PrimaryKey
	private String id;

	private float volume, pitch, pan;

	private SoundType type;

	public SoundConfig() {
	}

	public SoundConfig(String id, SoundType type) {
		this.id = id;
		this.setType(type);
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getPan() {
		return pan;
	}

	public void setPan(float pan) {
		this.pan = pan;
	}

	public SoundType getType() {
		return type;
	}

	public void setType(SoundType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Float.floatToIntBits(pan);
		result = prime * result + Float.floatToIntBits(pitch);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + Float.floatToIntBits(volume);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SoundConfig other = (SoundConfig) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Float.floatToIntBits(pan) != Float.floatToIntBits(other.pan))
			return false;
		if (Float.floatToIntBits(pitch) != Float.floatToIntBits(other.pitch))
			return false;
		if (type != other.type)
			return false;
		if (Float.floatToIntBits(volume) != Float.floatToIntBits(other.volume))
			return false;
		return true;
	}
	
	
}
