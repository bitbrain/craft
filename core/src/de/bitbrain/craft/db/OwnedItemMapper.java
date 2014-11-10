package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.OwnedItem;
import de.bitbrain.jpersis.annotations.Delete;
import de.bitbrain.jpersis.annotations.Insert;
import de.bitbrain.jpersis.annotations.Mapper;
import de.bitbrain.jpersis.annotations.Select;
import de.bitbrain.jpersis.annotations.Update;

@Mapper("de.bitbrain.craft.models.OwnedItem")
public interface OwnedItemMapper {

	@Select(condition = "player_id = $1")
	Collection<OwnedItem> findAllByPlayerId(int playerId);
	
	@Select(condition = "item_id = $1 AND player_id = $2")
	OwnedItem findById(String itemId, int playerId);
	
	@Update
	void update(OwnedItem ownedItem);
	
	@Insert
	void insert(OwnedItem ownedItem);
	
	@Delete
	void delete(OwnedItem ownedItem);
}
