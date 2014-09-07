package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.OwnedItem;
import de.myreality.jpersis.annotations.DataMapper;
import de.myreality.jpersis.annotations.Select;

@DataMapper(
		model = "de.bitbrain.craft.models.OwnedItem", 
		table="owned_item", 
		primaryKey = "id")
public interface OwnedItemMapper {

	@Select(condition = "player_id = $1")
	Collection<OwnedItem> findAllByPlayerId(int playerId);
}
