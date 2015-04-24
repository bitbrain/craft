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
 * Migration model for data migration
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Migration {

  @PrimaryKey(true)
  private int id;

  private String version;

  private int playerId;

  public Migration() {

  }

  public Migration(String version, int playerId) {
    this.version = version;
    this.playerId = playerId;
  }

  public int getId() {
    return id;
  }

  public int getPlayerId() {
    return playerId;
  }

  public String getVersion() {
    return version;
  }

}
