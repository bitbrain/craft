package de.bitbrain.craft.models;

import de.bitbrain.craft.core.GoalProcessor;
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
	
	private int recipeId;
	
	private Class<? extends GoalProcessor> processor;
	
	private int modulator = 1;

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	
	public int getModulator() {
		return modulator;
	}
	
	public void setModulator(int modulator) {
		this.modulator = modulator;
	}

	public Class<? extends GoalProcessor> getProcessor() {
		return processor;
	}

	public void setProcessor(Class<? extends GoalProcessor> processor) {
		this.processor = processor;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + modulator;
		result = prime * result
				+ ((processor == null) ? 0 : processor.hashCode());
		result = prime * result + recipeId;
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
		if (modulator != other.modulator)
			return false;
		if (processor == null) {
			if (other.processor != null)
				return false;
		} else if (!processor.equals(other.processor))
			return false;
		if (recipeId != other.recipeId)
			return false;
		return true;
	}
	
	
}
