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

import de.bitbrain.jpersis.annotations.PrimaryKey;

/**
 * Progress of a given player
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Progress {

	@PrimaryKey(true)
	private int id;
	
	private Profession profession;
	
	private int xp;
	
	private int playerId;
	
	public Progress() {
		
	}
	
	public Progress(int playerId, Profession profession) {
		this.playerId = playerId;
		this.profession = profession;
		this.xp = 0;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @return the profession
	 */
	public Profession getProfession() {
		return profession;
	}
	
	/**
	 * @return the xp
	 */
	public int getXp() {
		return xp;
	}
	
	/**
	 * @return the level
	 */
	public int getLevel() {
		return xp / 200 + 1;
	}
	
	public float getCurrentProgress() {
		return (getXp() - getXpMax(getLevel() - 1)) / getXpMax();
	}
	
	public int getXpMax() {
		return getXpMax(getLevel());
	}
	
	private int getXpMax(int level) {
		return level * 200 * level;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	/**
	 * @param profession the profession to set
	 */
	public void setProfession(Profession profession) {
		this.profession = profession;
	}
	
	/**
	 * @param xp the xp to set
	 */
	public void setXp(int xp) {
		this.xp = xp;
	}

	public void addXp(int xp) {
		this.xp += Math.abs(xp);
	}
}
