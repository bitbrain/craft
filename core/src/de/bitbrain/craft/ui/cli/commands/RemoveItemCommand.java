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
		if (args.length == 2) {
			ItemId id = Enum.valueOf(ItemId.class, args[0].toUpperCase());
			int amount = Integer.valueOf(args[1]);
			if (api.removeItem(Player.getCurrent().getId(), id.getId(), amount)) {
				Gdx.app.log("INFO", "Removed successfully.");
			} else {
				Gdx.app.log("ERROR", "Item with id'" + id + "' not found.");
			}
		} else {
			Gdx.app.log("ERROR", "Not enough arguments. Required: 2 [item_id amount]");
		}
		} catch (Exception e) {
			Gdx.app.log("ERROR", e.getMessage());
		}
	}

}
