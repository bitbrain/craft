package de.bitbrain.craft.ui.cli.commands;

import com.badlogic.gdx.Gdx;

import de.bitbrain.craft.core.API;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.ui.cli.Command;

public class AddXpCommand implements Command {

  @Override
  public void execute(API api, String... args) {
    try {
      if (args.length == 1) {
        int xp = Integer.valueOf(args[0]);
        if (Profession.current != null) {
          api.addXp(Profession.current, xp);
          Gdx.app.log("INFO", "Added " + args[0] + " xp.");
        } else {
          Gdx.app.log("ERROR", "Adding xp only possible in ingame mode.");
        }
      } else {
        Gdx.app.log("ERROR", "No xp amount specified.");
      }
    } catch (Exception e) {
      Gdx.app.log("ERROR", e.getMessage());
    }
  }

}
