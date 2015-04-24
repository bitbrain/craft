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

import de.bitbrain.craft.events.Event.EventType;

/**
 * Event bus implementation
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface EventBus {

  /**
   * Subscribes a new object to the bus
   * 
   * @param obj
   *          object to subscribe
   */
  public void subscribe(Object obj);

  /**
   * Unsubscribes an existing object from this bus
   * 
   * @param obj
   *          object to unsubscribe
   */
  public void unsubscribe(Object obj);

  /**
   * Fires an event
   * 
   * @param event
   */
  public <T> void fireEvent(Event<T> event);

  /**
   * Fires a new event
   * 
   * @param type
   *          event type
   * @param item
   *          element
   */
  public <T> void fireEvent(EventType type, T item);
}
