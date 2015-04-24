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

import de.bitbrain.craft.core.ItemId;
import de.bitbrain.jpersis.annotations.PrimaryKey;

/**
 * Recipe which describes how to make items out of other items
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Recipe {

  @PrimaryKey(true)
  private int id;

  private ItemId itemId;

  private int amount;

  private Profession profession;

  public int getAmount() {
    return amount;
  }

  public int getId() {
    return id;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public ItemId getItemId() {
    return itemId;
  }

  public void setItemId(ItemId itemId) {
    this.itemId = itemId;
  }

  public void setProfession(Profession profession) {
    this.profession = profession;
  }

  public Profession getProfession() {
    return profession;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + amount;
    result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
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
    Recipe other = (Recipe) obj;
    if (amount != other.amount)
      return false;
    if (itemId == null) {
      if (other.itemId != null)
        return false;
    } else if (!itemId.equals(other.itemId))
      return false;
    return true;
  }

}
