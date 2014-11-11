package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Progress;
import de.bitbrain.jpersis.annotations.Delete;
import de.bitbrain.jpersis.annotations.Insert;
import de.bitbrain.jpersis.annotations.Mapper;
import de.bitbrain.jpersis.annotations.Select;
import de.bitbrain.jpersis.annotations.Update;

@Mapper("de.bitbrain.craft.models.Progress")
public interface ProgressMapper {
	
	@Select(condition = "player_id = $1")
	Collection<Progress> progressOfPlayer(int id);
	
	@Insert
	boolean insert(Progress progress);
	
	@Insert
	boolean insert(Collection<Item> items);

	@Update
	boolean update(Progress progress);
	
	@Delete
	boolean delete(Progress progress);	
}