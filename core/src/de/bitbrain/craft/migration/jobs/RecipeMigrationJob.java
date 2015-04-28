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
package de.bitbrain.craft.migration.jobs;

import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.core.RecipeDataBuilder;
import de.bitbrain.craft.migration.Migrate;
import de.bitbrain.craft.migration.Migrations;
import de.bitbrain.craft.models.Profession;

/**
 * Migrates data at the beginning for new users
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class RecipeMigrationJob {

  @Migrate(Migrations.RELEASE)
  public void migrateRecipesForAlchemist(API api) {
    // Acid 1
    api.registerRecipe(new RecipeDataBuilder(ItemId.ACID_1, Profession.ALCHEMIST)
      .addIngredient(ItemId.WATER, 1).addIngredient(ItemId.EARTH, 1)
      .amount(1).build());
    // Acid 2
    api.registerRecipe(new RecipeDataBuilder(ItemId.ACID_2, Profession.ALCHEMIST)
    .addIngredient(ItemId.WATER, 2).addIngredient(ItemId.EARTH, 2).addIngredient(ItemId.EARTH, 1)
    .amount(1).build());
    // Phiole 1
    api.registerRecipe(new RecipeDataBuilder(ItemId.PHIOLE_1, Profession.ALCHEMIST)
    .addIngredient(ItemId.EARTH, 1).addIngredient(ItemId.FIRE, 1)
    .amount(5).build());
    // Phiole 2
    api.registerRecipe(new RecipeDataBuilder(ItemId.PHIOLE_2, Profession.ALCHEMIST)
    .addIngredient(ItemId.EARTH, 2).addIngredient(ItemId.FIRE, 2)
    .amount(5).build());
  }
}
