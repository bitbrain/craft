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

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Bundles;

/**
 * Profession of a player
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public enum Profession {
	
	JEWELER {

		@Override
		public String getIcon() {
			return Assets.TEX_JEWELER;
		}

		@Override
		public String getName() {
			return Bundles.general.get(Bundles.JEWELER);
		}

		@Override
		public String getDescription() {
			return "jeweler-info";
		}
	},
	
	ENGINEER {

		@Override
		public String getIcon() {
			return Assets.TEX_ENGINEER;
		}

		@Override
		public String getName() {
			return Bundles.general.get(Bundles.ENGINEER);
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	
	ALCHEMIST {

		@Override
		public String getIcon() {
			return Assets.TEX_ALCHEMIST;
		}

		@Override
		public String getName() {
			return Bundles.general.get(Bundles.ALCHEMIST);
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return null;
		}
		
	};

	public abstract String getIcon();
	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public static Profession current;
}
