package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.Migration;
import de.bitbrain.jpersis.annotations.Delete;
import de.bitbrain.jpersis.annotations.Insert;
import de.bitbrain.jpersis.annotations.Mapper;
import de.bitbrain.jpersis.annotations.Select;

@Mapper("de.bitbrain.craft.models.Migration")
public interface MigrationMapper {
	
	@Select(condition = "player_id = $1 AND version = $2")
	Migration findByPlayerId(int playerId, String version);
	
	@Select(condition = "player_id = $1")
	Collection<Migration> findAllByPlayerId(int playerId);
	
	@Insert
	boolean insert(Migration migration);
	
	@Delete
	boolean delete(Migration migration);
}
