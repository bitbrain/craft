package de.bitbrain.craft.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item;

public class AvailabilityIcon extends Actor {
	
	private static final float SIZE = 16;
	
	@Inject
	private API api;
	
	private Sprite sprite;

	public AvailabilityIcon(Item item) {
		SharedInjector.get().injectMembers(this);
		setItem(item);
		setWidth(SIZE);
		setHeight(SIZE);
	}
	
	public void setItem(Item item) {
		if (api.canCraft(item.getId())) {
			sprite = new Sprite(SharedAssetManager.get(Assets.TEX_CHECK, Texture.class));
			sprite.setColor(Color.GREEN);
		} else if (api.canCraftIndirect(item.getId())){
			sprite = new Sprite(SharedAssetManager.get(Assets.TEX_CHECK, Texture.class));
			sprite.setColor(Color.CYAN);
		} else {
			sprite = new Sprite(SharedAssetManager.get(Assets.TEX_NOT, Texture.class));
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		sprite.setBounds(getX(), getY(), getWidth(), getHeight());
		sprite.draw(batch, parentAlpha);
	}
}
