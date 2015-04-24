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

import com.badlogic.gdx.graphics.Color;

import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.graphics.Effect;
import de.bitbrain.craft.graphics.Icon;
import de.bitbrain.jpersis.annotations.PrimaryKey;

/**
 * Item which can be used for crafting
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Item {

  /** if the item's amount is set to the following value, it is stated as infinite */
  public static final int INFINITE_AMOUNT = -1;

  @PrimaryKey
  private ItemId id;

  private Icon icon;

  private Rarity rarity = Rarity.COMMON;

  private int level = 1;

  private Class<? extends Effect> effect = Effect.class;

  public Item() {

  }

  public Item(ItemId id, Icon icon, Rarity rarity) {
    this.id = id;
    this.icon = icon;
    this.rarity = rarity;
  }

  public Icon getIcon() {
    return icon;
  }

  public ItemId getId() {
    return id;
  }

  public Rarity getRarity() {
    return rarity;
  }

  public void setIcon(Icon icon) {
    this.icon = icon;
  }

  public void setId(ItemId id) {
    this.id = id;
  }

  public void setRarity(Rarity rarity) {
    this.rarity = rarity;
  }

  public void setEffect(Class<? extends Effect> effect) {
    this.effect = effect;
  }

  public Class<? extends Effect> getEffect() {
    return effect;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    Item other = (Item) obj;
    if (id != other.id)
      return false;
    return true;
  }

  public static enum Rarity {
    COMMON("dddddd", 1),
    RARE("00ff00", 2),
    SUPERIOR("0043b1", 3),
    EPIC("8500b0", 4),
    UNIQUE("ff6600", 5),
    LEGENDARY("ff00d2", 6);

    private Color color;

    private int level;

    Rarity(String color, int level) {
      this.color = Color.valueOf(color);
      this.level = level;
    }

    public int getLevel() {
      return level;
    }

    public Color getColor() {
      return color;
    }
  }
}
