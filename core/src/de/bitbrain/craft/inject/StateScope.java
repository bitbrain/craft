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

package de.bitbrain.craft.inject;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

/**
 * Scope implementation for state based injection behavior
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class StateScope implements Scope {

	private Class<?> state;

	private final ThreadLocal<Map<Class<?>, Object>> values = new ThreadLocal<Map<Class<?>, Object>>();
	private final Map<Key<?>, Class<?>> keys = new HashMap<Key<?>, Class<?>>();

	public void enter(Class<?> state) {
		this.state = state;
	}

	public void leave() {
		values.remove();
	}

	public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
		return new Provider<T>() {
			@SuppressWarnings("unchecked")
			public T get() {
				keys.put(key, state);
				Map<Class<?>, Object> map = getScopedObjectMap();

				if (!map.containsKey(state)) {
					T t = unscoped.get();
					map.put(state, t);
				}
				return (T) map.get(state);
			}
		};
	}

	private <T> Map<Class<?>, Object> getScopedObjectMap() {
		Map<Class<?>, Object> scopedObjects = values.get();
		if (scopedObjects == null) {
			scopedObjects = new HashMap<Class<?>, Object>();
			values.set(scopedObjects);
		}
		return scopedObjects;
	}
}