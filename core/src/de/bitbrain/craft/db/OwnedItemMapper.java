package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.OwnedItem;
import de.myreality.jpersis.annotations.DataMapper;
import de.myreality.jpersis.annotations.Insert;
import de.myreality.jpersis.annotations.Select;
import de.myreality.jpersis.annotations.Update;

@DataMapper(
		model = "de.bitbrain.craft.models.OwnedItem", 
		table="owned_items", 
		primaryKey = "id")
public interface OwnedItemMapper {

	@Select(condition = "player_id = $1")
	Collection<OwnedItem> findAllByPlayerId(int playerId);
	
	@Select(condition = "item_id = $1 AND player_id = $2")
	OwnedItem findById(String itemId, int playerId);
	
	@Update
	void update(OwnedItem ownedItem);
	
	@Insert
	void insert(OwnedItem ownedItem);
}
