package de.bitbrain.craft.ui.cli;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.google.inject.Inject;

import de.bitbrain.craft.core.API;

public class CommandLineInterface extends Actor {

	private TextField textField;
	
	@Inject
	private API api;
}
