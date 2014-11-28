package de.bitbrain.craft.models;

import de.bitbrain.jpersis.annotations.PrimaryKey;

/**
 * Goal which belongs to a recipe
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Goal {

	@PrimaryKey(true)
	private int id;
	
	private String recipeId;
	
	private String processor;

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((processor == null) ? 0 : processor.hashCode());
		result = prime * result
				+ ((recipeId == null) ? 0 : recipeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Goal other = (Goal) obj;
		if (processor == null) {
			if (other.processor != null)
				return false;
		} else if (!processor.equals(other.processor))
			return false;
		if (recipeId == null) {
			if (other.recipeId != null)
				return false;
		} else if (!recipeId.equals(other.recipeId))
			return false;
		return true;
	}
	
	
}
