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

package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.models.OwnedItem;
import de.bitbrain.jpersis.annotations.Delete;
import de.bitbrain.jpersis.annotations.Insert;
import de.bitbrain.jpersis.annotations.Mapper;
import de.bitbrain.jpersis.annotations.Select;
import de.bitbrain.jpersis.annotations.Update;

/**
 * Data mapper for {@link de.bitbrain.craft.models.OwnedItem}
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@Mapper("de.bitbrain.craft.models.OwnedItem")
public interface OwnedItemMapper {

  @Select(condition = "player_id = $1")
  Collection<OwnedItem> findAllByPlayerId(int playerId);

  @Select(condition = "item_id = $1 AND player_id = $2")
  OwnedItem findById(ItemId itemId, int playerId);

  @Update
  boolean update(OwnedItem ownedItem);

  @Insert
  boolean insert(OwnedItem ownedItem);

  @Delete
  boolean delete(OwnedItem ownedItem);

  @Delete
  boolean delete(Collection<OwnedItem> items);
}
