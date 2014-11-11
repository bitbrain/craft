package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.Item;
import de.bitbrain.jpersis.annotations.Count;
import de.bitbrain.jpersis.annotations.Delete;
import de.bitbrain.jpersis.annotations.Insert;
import de.bitbrain.jpersis.annotations.Mapper;
import de.bitbrain.jpersis.annotations.Select;
import de.bitbrain.jpersis.annotations.Update;

@Mapper("de.bitbrain.craft.models.Item")
public interface ItemMapper {

	@Select
	Collection<Item> findAll();
	
	@Select(condition = "id = $1")
	Item findById(String id);
	
	@Insert
	boolean insert(Item item);
	
	@Insert
	boolean insert(Collection<Item> items);
	
	@Update
	boolean update(Item customer);

	@Update
	boolean update(Collection<Item> customer);
	
	@Delete
	boolean delete(Item item);
	
	@Delete
	boolean delete(Collection<Item> items);
	
	@Count
    int count();
	
}
