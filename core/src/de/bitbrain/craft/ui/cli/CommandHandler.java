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

package de.bitbrain.craft.ui.cli;

import java.util.HashMap;
import java.util.Map;

import de.bitbrain.craft.core.API;

/**
 * Handler to handle commands and delegate functionality
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class CommandHandler implements Command {

  private Map<String, Command> commands = new HashMap<String, Command>();

  /**
   * Registers a new command to the handler
   * 
   * @param id
   *          command id
   * @param command
   *          target command
   */
  public void register(String id, Command command) {
    if (!commands.containsKey(id)) {
      commands.put(id, command);
    }
  }

  /**
   * 
   * 
   * @param string
   */
  public void executeString(API api, String string) {
    String[] partials = partialString(string);
    if (partials.length > 0) {
      execute(api, partials);
    }
  }

  @Override
  public void execute(API api, String... args) {
    if (args.length > 0) {
      Command command = commands.get(args[0]);
      if (command != null) {
        command.execute(api, toArguments(args));
      }
    }
  }

  private String[] toArguments(String[] partials) {
    if (partials.length > 1) {
      String[] shortened = new String[partials.length - 1];
      for (int i = 1; i < partials.length; ++i) {
        shortened[i - 1] = partials[i];
      }
      return shortened;
    } else {
      return new String[] {};
    }
  }

  private String[] partialString(String string) {
    return string.split(" ");
  }
}
