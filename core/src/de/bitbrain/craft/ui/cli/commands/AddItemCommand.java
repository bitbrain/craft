package de.bitbrain.craft.ui.cli.commands;

import com.badlogic.gdx.Gdx;

import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.ui.cli.Command;

public class AddItemCommand implements Command {

  @Override
  public void execute(API api, String... args) {
    try {
      int playerId = Player.getCurrent().getId();
      if (args.length == 2) {
        ItemId id = Enum.valueOf(ItemId.class, args[0].toUpperCase());
        int amount = Integer.valueOf(args[1]);
        Item item = api.addItem(playerId, id, amount);
        if (item != null) {
          Gdx.app.log("INFO", "Added successfully " + amount + "x " + id);
        } else {
          Gdx.app.log("ERROR", "Item with id " + id + "  not found.");
        }
      } else {
        int amount = 1;
        if (args.length == 1) {
          amount = Integer.valueOf(args[0]);
          if (amount < 1) {
            Gdx.app.log("ERROR", "Invalid item amount: " + amount);
            return;
          }
        }
        for (ItemId id : ItemId.values()) {
          Item item = api.addItem(playerId, id, amount);
          if (item != null) {
            Gdx.app.log("INFO", "Added successfully " + amount + "x " + id);
          } else {
            Gdx.app.log("ERROR", "Item with id " + id + " not found.");
          }
        }
      }
    } catch (Exception e) {
      Gdx.app.log("ERROR", e.getMessage());
    }
  }

}
