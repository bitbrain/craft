package de.bitbrain.craft.ui.cli.commands;

import com.badlogic.gdx.Gdx;

import de.bitbrain.craft.core.API;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.ui.cli.Command;

public class SetXpCommand implements Command {

  @Override
  public void execute(API api, String... args) {
    try {
      if (args.length == 1) {
        int xp = Integer.valueOf(args[0]);
        if (Profession.current != null) {
          api.setXp(Profession.current, xp);
          Gdx.app.log("INFO", "Setting xp to " + args[0] + ".");
        } else {
          Gdx.app.log("ERROR", "Setting xp only possible in ingame mode.");
        }
      } else {
        Gdx.app.log("ERROR", "No xp amount specified.");
      }
    } catch (Exception e) {
      Gdx.app.log("ERROR", e.getMessage());
    }
  }

}
