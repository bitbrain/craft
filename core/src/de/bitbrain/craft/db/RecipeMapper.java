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
import de.bitbrain.craft.models.Recipe;
import de.bitbrain.jpersis.annotations.Count;
import de.bitbrain.jpersis.annotations.Delete;
import de.bitbrain.jpersis.annotations.Insert;
import de.bitbrain.jpersis.annotations.Mapper;
import de.bitbrain.jpersis.annotations.Select;
import de.bitbrain.jpersis.annotations.Update;

/**
 * Data mapper for {@link de.bitbrain.craft.models.Recipe}
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@Mapper("de.bitbrain.craft.models.Recipe")
public interface RecipeMapper {

	@Select
	Collection<Recipe> findAll();

	@Select(condition = "id = $1")
	Recipe findById(int id);

	@Select(condition = "item_id = $1")
	Recipe findByItemId(ItemId itemId);

	@Insert
	boolean insert(Recipe recipe);

	@Insert
	boolean insert(Collection<Recipe> recipes);

	@Update
	boolean update(Recipe recipe);

	@Update
	boolean update(Collection<Recipe> recipes);

	@Delete
	boolean delete(Recipe recipe);

	@Delete
	boolean delete(Collection<Recipe> recipes);

	@Count
	int count();
}