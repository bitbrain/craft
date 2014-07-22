package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Progress;
import de.myreality.jpersis.annotations.DataMapper;
import de.myreality.jpersis.annotations.Delete;
import de.myreality.jpersis.annotations.Insert;
import de.myreality.jpersis.annotations.Select;
import de.myreality.jpersis.annotations.Update;

@DataMapper(model = "de.bitbrain.craft.models.Progress", table="progress", primaryKey = "id")
public interface ProgressMapper {
	
	@Select(condition = "player_id = $1")
	Collection<Progress> progressOfPlayer(int id);
	
	@Insert
	void insert(Progress progress);
	
	@Insert
	void insert(Collection<Item> items);

	@Update
	void update(Progress progress);
	
	@Delete
	void delete(Progress progress);
	
}
