package de.bitbrain.craft.events;

import de.bitbrain.craft.models.Progress;

public class ProgressEvent extends Event<Progress> {

	public ProgressEvent(Progress model) {
		super(EventType.UPDATE, model);
	}

}
