package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.Item;
import de.bitbrain.jpersis.annotations.Count;
import de.bitbrain.jpersis.annotations.DataMapper;
import de.bitbrain.jpersis.annotations.Delete;
import de.bitbrain.jpersis.annotations.Insert;
import de.bitbrain.jpersis.annotations.Select;
import de.bitbrain.jpersis.annotations.Update;

@DataMapper(
		model = "de.bitbrain.craft.models.Item", 
		table="item", 
		primaryKey = "id")
public interface ItemMapper {

	@Select
	Collection<Item> findAll();
	
	@Select(condition = "id = $1")
	Item findById(String id);
	
	@Insert
	void insert(Item item);
	
	@Insert
	void insert(Collection<Item> items);
	
	@Update
	void update(Item customer);

	@Update
	void update(Collection<Item> customer);
	
	@Delete
	void delete(Item item);
	
	@Delete
	void delete(Collection<Item> items);
	
	@Count
    int count();
	
}
