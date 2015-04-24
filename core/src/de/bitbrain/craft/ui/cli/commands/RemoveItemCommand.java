package de.bitbrain.craft.ui.cli.commands;

import com.badlogic.gdx.Gdx;

import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.ui.cli.Command;

public class RemoveItemCommand implements Command {

  @Override
  public void execute(API api, String... args) {
    try {
      int playerId = Player.getCurrent().getId();
      if (args.length == 2) {
        ItemId id = Enum.valueOf(ItemId.class, args[0].toUpperCase());
        int amount = Integer.valueOf(args[1]);
        if (api.removeItem(playerId, id, amount)) {
          Gdx.app.log("INFO", "Removed successfully.");
        } else {
          Gdx.app.log("ERROR", "Item with id'" + id + "' not found.");
        }
      } else {
        ItemId id = null;
        if (args.length == 1) {
          id = Enum.valueOf(ItemId.class, args[0].toUpperCase());
        }
        if (id != null) {
          api.removeItem(playerId, id);
        } else {
          api.clearItems(playerId);
        }
      }
    } catch (Exception e) {
      Gdx.app.log("ERROR", e.getMessage());
    }
  }
}
