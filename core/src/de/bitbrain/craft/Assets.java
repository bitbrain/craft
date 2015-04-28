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

  public static final String DIR_IMAGES = "images/";
  public static final String DIR_FONTS = "fonts/";
  public static final String DIR_AUDIO = "audio/";
  public static final String DIR_LANGUAGE = "i18n/";
  public static final String DIR_PARTICLES = "particles/";
  public static final String DIR_ICONS = DIR_IMAGES + "icons/";

  public static final String SQL_INIT = "game.sql";
  public static final String SQL_UPDATE = "update.sql";

  // ---------- Particles -----------

  public static final String PRT_GREEN_SMALL = DIR_PARTICLES + "particles-green-small.p";

  // ---------- BUNDLES -----------

  public static final String BDL_GENERAL = DIR_LANGUAGE + "general";
  public static final String BDL_ITEMS = DIR_LANGUAGE + "items";
  public static final String BDL_ITEMS_DESCRIPTIONS = DIR_LANGUAGE + "items-description";
  public static final String BDL_RECIPES = DIR_LANGUAGE + "recipes";

  // ---------- FONTS -----------
  /**
   * grass green color
   */
  public static final String FNT_SMALLEST = DIR_FONTS + "smallest.fnt";
  public static final String FNT_SMALL = DIR_FONTS + "small.fnt";
  public static final String FNT_MEDIUM = DIR_FONTS + "medium.fnt";
  public static final String FNT_LARGER = DIR_FONTS + "larger.fnt";
  public static final String FNT_MONO = DIR_FONTS + "mono.fnt";
  // ---------- SOUNDS ----------

  public static final String SND_BEEP = DIR_AUDIO + "button_01.mp3";
  public static final String SND_ABORT = DIR_AUDIO + "abort.mp3";
  public static final String SND_CONFIRM = DIR_AUDIO + "confirm.mp3";
  public static final String SND_POP = DIR_AUDIO + "pop.mp3";
  public static final String SND_POP_ALT = DIR_AUDIO + "short_pop.mp3";
  public static final String SND_TAB = DIR_AUDIO + "tab.mp3";

  // ---------- TEXTURES --------

  public static final String TEX_ALCHEMIST = DIR_IMAGES + "character/alchemist.png";
  public static final String TEX_CIRCLE = DIR_IMAGES + "ui/circle.png";
  public static final String TEX_BOWL = DIR_IMAGES + "ui/bowl.png";
  public static final String TEX_TABLE = DIR_IMAGES + "ui/table.png";
  public static final String TEX_CHECK = DIR_IMAGES + "ui/check.png";
  public static final String TEX_STAR = DIR_IMAGES + "ui/star.png";
  public static final String TEX_TAB_GRADIENT = DIR_IMAGES + "ui/tab_gradient.png";
  public static final String TEX_CURSOR = DIR_IMAGES + "cursor.png";
  public static final String TEX_BACKGROUND_01 = DIR_IMAGES + "background.png";
  public static final String TEX_LOGO = DIR_IMAGES + "logo.png";
  public static final String TEX_BUTTON_GREEN = DIR_IMAGES + "button.png";
  public static final String TEX_BUTTON_RED = DIR_IMAGES + "button_red.png";
  public static final String TEX_BUTTON_GREEN_DARK = DIR_IMAGES + "button_down.png";
  public static final String TEX_BUTTON_RED_DARK = DIR_IMAGES + "button_red_down.png";
  public static final String TEX_JEWELER_PREVIEW = DIR_IMAGES + "icons/jewel_diamond_medium.png";
  public static final String TEX_ENGINEER_PREVIEW = DIR_IMAGES + "icons/fab_power_medium.png";
  public static final String TEX_ALCHEMIST_PREVIEW = DIR_IMAGES + "icons/alchemy_flasks_overview.png";
  public static final String TEX_PANEL_SMALL_H = DIR_IMAGES + "panel_small_horizontal.png";
  public static final String TEX_PANEL_SMALL_L_H = DIR_IMAGES + "panel_small_light_horizontal.png";
  public static final String TEX_PANEL_9patch = DIR_IMAGES + "ui/panel.9.png";
  public static final String TEX_PANEL_TRANSPARENT_9patch = DIR_IMAGES + "ui/panel_transparent.9.png";
  public static final String TEX_PANEL_BLACK_9patch = DIR_IMAGES + "ui/panel_black.9.png";
  public static final String TEX_PANEL_HIGHLIGHT_9patch = DIR_IMAGES + "ui/panel_highlight.9.png";
  public static final String TEX_PANEL_TAB_9patch = DIR_IMAGES + "ui/panel_tab.9.png";
  public static final String TEX_PANEL_TAB_ACTIVE_9patch = DIR_IMAGES + "ui/panel_tab_active.9.png";
  public static final String TEX_PANEL_BAR_9patch = DIR_IMAGES + "ui/panel_bar.9.png";

  // ---------- COLORS ----------

  public static final Color CLR_GREEN_GRASS = Color.valueOf("b8d41c");
  public static final Color CLR_GREEN_GRASS_LIGHT = Color.valueOf("c7e03d");
  public static final Color CLR_BROWN_TEAK = Color.valueOf("5f1b10");
  public static final Color CLR_INACTIVE = Color.valueOf("d5601a");
  public static final Color CLR_YELLOW_SAND = Color.valueOf("dcc77d");
  public static final Color CLR_YELLOW_SAND_A = Color.valueOf("dcc77d99");
  public static final Color CLR_BLUE_SKY = Color.valueOf("89789a");
  public static final Color CLR_RED = Color.valueOf("e74d23");
  public static final Color CLR_RED_LIGHT = Color.valueOf("f45f37");
}
