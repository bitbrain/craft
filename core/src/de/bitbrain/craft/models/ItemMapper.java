package de.bitbrain.craft.models;

import java.util.Collection;

import de.myreality.jpersis.annotations.DataMapper;
import de.myreality.jpersis.annotations.Select;

@DataMapper(model = "de.bitbrain.craft.models.Item", table="item", primaryKey = "id")
public interface ItemMapper {

	@Select
	Collection<Item> findAll();
	
	@Select(condition = "id = $1")
	Item findById(int id);
	
}
