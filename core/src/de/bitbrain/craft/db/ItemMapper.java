package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.Item;
import de.myreality.jpersis.annotations.Count;
import de.myreality.jpersis.annotations.DataMapper;
import de.myreality.jpersis.annotations.Delete;
import de.myreality.jpersis.annotations.Insert;
import de.myreality.jpersis.annotations.Select;
import de.myreality.jpersis.annotations.Update;

@DataMapper(
		model = "de.bitbrain.craft.models.Item", 
		table="item", 
		primaryKey = "id")
public interface ItemMapper {

	@Select
	Collection<Item> findAll();
	
	@Select(condition = "id = $1")
	Item findById(int id);
	
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
