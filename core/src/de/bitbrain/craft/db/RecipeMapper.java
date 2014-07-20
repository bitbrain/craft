package de.bitbrain.craft.db;

import java.util.Collection;

import de.bitbrain.craft.models.Recipe;
import de.myreality.jpersis.annotations.Count;
import de.myreality.jpersis.annotations.DataMapper;
import de.myreality.jpersis.annotations.Delete;
import de.myreality.jpersis.annotations.Insert;
import de.myreality.jpersis.annotations.Select;
import de.myreality.jpersis.annotations.Update;

@DataMapper(model = "de.bitbrain.craft.models.Recipe", table="recipe", primaryKey = "id")
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