package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.Player;
import de.myreality.jpersis.annotations.Count;
import de.myreality.jpersis.annotations.DataMapper;
import de.myreality.jpersis.annotations.Delete;
import de.myreality.jpersis.annotations.Insert;
import de.myreality.jpersis.annotations.Select;
import de.myreality.jpersis.annotations.Update;

@DataMapper(
		model = "de.bitbrain.craft.models.Player", 
		table="player", 
		primaryKey = "id")
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
