package de.bitbrain.craft.ui.cli.commands;

import de.bitbrain.craft.ui.cli.Command;
import de.bitbrain.craft.ui.cli.CommandHandler;

/**
 * Implementation of a command which listens to adding
 * 
 * @author Miguel Gonzalez
 *
 */
public class SetCommand extends CommandHandler implements Command {

  public SetCommand() {
    register("xp", new SetXpCommand());
  }
}
