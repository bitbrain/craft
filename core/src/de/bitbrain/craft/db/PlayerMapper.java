package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.Player;
import de.bitbrain.jpersis.annotations.Count;
import de.bitbrain.jpersis.annotations.Delete;
import de.bitbrain.jpersis.annotations.Insert;
import de.bitbrain.jpersis.annotations.Mapper;
import de.bitbrain.jpersis.annotations.Select;
import de.bitbrain.jpersis.annotations.Update;

@Mapper("de.bitbrain.craft.models.Player")
public interface PlayerMapper {

	@Select
	Collection<Player> findAll();
	
	@Select(condition = "id = $1")
	Player findById(int id);
	
	@Select(condition = "name = $1")
	Player findByName(String id);
	
	@Insert
	void insert(Player item);
	
	@Insert
	void insert(Collection<Player> items);
	
	@Update
	void update(Player customer);

	@Update
	void update(Collection<Player> customer);
	
	@Delete
	void delete(Player item);
	
	@Delete
	void delete(Collection<Player> items);
	
	@Count
    int count();
	
}
