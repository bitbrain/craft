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

package de.bitbrain.craft.util;

import java.util.Collection;

import de.bitbrain.craft.db.ProgressMapper;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.models.Progress;
import de.bitbrain.jpersis.MapperManager;

/**
 * 
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class DirectPlayerDataProvider implements PlayerDataProvider {
	
	private Collection<Progress> progress;
	
	private int playerID;
	
	public DirectPlayerDataProvider(int playerID) {		
			this.playerID = playerID;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.util.PlayerDataProvider#getLevel(de.bitbrain.craft.models.Profession)
	 */
	@Override
	public int getLevel(Profession profession) {
		
		refresh();
		
		for (Progress p : progress) {
			if (p.getProfession().equals(profession)) {
				return p.getLevel();
			}
		}
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.util.PlayerDataProvider#getProgress(de.bitbrain.craft.models.Profession)
	 */
	@Override
	public float getProgress(Profession profession) {
		
		refresh();
		
		for (Progress p : progress) {
			if (p.getProfession().equals(profession)) {
				return p.getXp();
			}
		}
		
		return 0;
	}

	public void refresh() {
		if (progress == null) {
			ProgressMapper mapper = MapperManager.getInstance().getMapper(ProgressMapper.class);		
			progress = mapper.progressOfPlayer(playerID);	
		}
	}
}
