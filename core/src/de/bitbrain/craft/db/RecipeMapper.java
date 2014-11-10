package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.Recipe;
import de.bitbrain.jpersis.annotations.Count;
import de.bitbrain.jpersis.annotations.Delete;
import de.bitbrain.jpersis.annotations.Insert;
import de.bitbrain.jpersis.annotations.Mapper;
import de.bitbrain.jpersis.annotations.Select;
import de.bitbrain.jpersis.annotations.Update;

@Mapper("de.bitbrain.craft.models.Recipe")
public interface RecipeMapper {

	@Select
	Collection<Recipe> findAll();
	
	@Select(condition = "id = $1")
	Recipe findById(int id);
	
	@Insert
	void insert(Recipe item);
	
	@Insert
	void insert(Collection<Recipe> items);
	
	@Update
	void update(Recipe customer);

	@Update
	void update(Collection<Recipe> customer);
	
	@Delete
	void delete(Recipe item);
	
	@Delete
	void delete(Collection<Recipe> items);
	
	@Count
    int count();
}