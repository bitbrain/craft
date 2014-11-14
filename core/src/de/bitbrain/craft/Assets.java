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

package de.bitbrain.craft;

import com.badlogic.gdx.graphics.Color;


/**
 * Resource file which contains all resource keys
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public final class Assets {
	
	// ---------- GENERAL --------
	
	public static final String DIR_IMAGES 				= "images/";
	public static final String DIR_FONTS 			  	= "fonts/";
	public static final String DIR_AUDIO 				= "audio/";
	public static final String DIR_LANGUAGE   			= "i18n/";	
	public static final String DIR_PARTICLES 	    	= "particles/";
	public static final String DIR_ICONS					= DIR_IMAGES + "icons/";
	
	public static final String SQL_INIT 					= "game.sql";
	public static final String SQL_UPDATE				= "update.sql";
	
	// ---------- Particles -----------	
	
	public static final String PRT_GREEN_SMALL   		= DIR_PARTICLES + "particles-green-small.p";
	
	// ---------- BUNDLES -----------	
	
	public static final String BDL_GENERAL 				= DIR_LANGUAGE + "general";
	public static final String BDL_ITEMS 			    = DIR_LANGUAGE + "items";
	public static final String BDL_RECIPES 				= DIR_LANGUAGE + "recipes";
	
	// ---------- FONTS -----------	
	/**
	 * grass green color
	 */
	public static final String FNT_SMALL			    	= DIR_FONTS + "small.fnt";
	public static final String FNT_MEDIUM			    = DIR_FONTS + "medium.fnt";
	public static final String FNT_LARGER 				= DIR_FONTS + "larger.fnt";

	// ---------- SOUNDS ----------
	
	public static final String SND_BEEP 					= DIR_AUDIO + "button_01.mp3";		
	public static final String SND_ABORT 			    = DIR_AUDIO + "abort.mp3";	
	public static final String SND_CONFIRM 				= DIR_AUDIO + "confirm.mp3";	
	public static final String SND_POP 					= DIR_AUDIO + "pop.mp3";	
	public static final String SND_TAB					= DIR_AUDIO + "tab.mp3";	
	
	// ---------- MUSIC ----------
	
	//public static final String MSC_MENU_01 				= DIR_AUDIO + "menu.mp3";		
	
	// ---------- TEXTURES --------

	public static final String TEX_BACKGROUND_01 		= DIR_IMAGES + "background.png";
	public static final String TEX_LOGO 				= DIR_IMAGES + "logo.png";
	public static final String TEX_BUTTON_GREEN 		= DIR_IMAGES + "button.png";
	public static final String TEX_BUTTON_GREEN_DARK 	= DIR_IMAGES + "button_down.png";
	public static final String TEX_PANEL_V 		        = DIR_IMAGES + "panel_medium_vertical.png";
	public static final String TEX_PANEL_DOWN_V 		= DIR_IMAGES + "panel_medium_vertical_down.png";
	public static final String TEX_PANEL_HOVER_V		= DIR_IMAGES + "panel_medium_vertical_hover.png";
	public static final String TEX_JEWELER 				= DIR_IMAGES + "icons/ico_jewel_diamond_medium.png";
	public static final String TEX_ENGINEER 			= DIR_IMAGES + "icons/ico_fab_power_medium.png";
	public static final String TEX_ALCHEMIST 			= DIR_IMAGES + "icons/ico_alchemy_flasks_overview.png";
	public static final String TEX_PANEL_DARK_H 		= DIR_IMAGES + "panel_dark_horizontal.png";
	public static final String TEX_PANEL_SMALL_H 		= DIR_IMAGES + "panel_small_horizontal.png";
	public static final String TEX_PANEL_SMALL_L_H 		= DIR_IMAGES + "panel_small_light_horizontal.png";
	public static final String TEX_PANEL_MEDIUM_BOX 	= DIR_IMAGES + "panel_medium_box.png";
	public static final String TEX_PANEL_TAB			= DIR_IMAGES + "panel_tab.png";
	public static final String TEX_PANEL_TAB_ACTIVE		= DIR_IMAGES + "panel_tab_active.png";
	public static final String TEX_PANEL_ITEM			= DIR_IMAGES + "item_background.png";
	public static final String TEX_ICON_BACKGROUND   	= DIR_IMAGES + "icon_background.png";
	public static final String TEX_PANEL_9patch			= DIR_IMAGES + "ui/panel.9.png";
	public static final String TEX_PANEL_HIGHLIGHT_9patch = DIR_IMAGES + "ui/panel_highlight.9.png";
	// ---------- COLORS ----------
	
	public static final Color CLR_GREEN_GRASS 			= Color.valueOf("b8d41c");
	public static final Color CLR_GREEN_GRASS_LIGHT 	= Color.valueOf("c7e03d");
	public static final Color CLR_BROWN_TEAK 			= Color.valueOf("5f1b10");
	public static final Color CLR_INACTIVE   			= Color.valueOf("d5601a");
	public static final Color CLR_YELLOW_SAND 			= Color.valueOf("dcc77d");
	public static final Color CLR_YELLOW_SAND_A 		= Color.valueOf("dcc77d99");
	public static final Color CLR_BLUE_SKY				= Color.valueOf("84f2ff");
}
