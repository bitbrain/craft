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

package de.bitbrain.craft.events;

import de.bitbrain.craft.models.Item;

/**
 * Message special for elements
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ItemEvent extends Event<Item> {

  public static final String ITEM = "item";

  public static final String AMOUNT = "amount";

  private int amount;

  /**
   * @param type
   * @param model
   */
  public ItemEvent(de.bitbrain.craft.events.Event.EventType type, Item item, int amount) {
    super(type, item);
    this.amount = amount;
  }

  public int getAmount() {
    return amount;
  }
}
