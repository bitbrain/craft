package de.bitbrain.craft.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item;

public class AvailabilityIcon {
	
	@Inject
	private API api;
	
	private Sprite sprite;

	public AvailabilityIcon(Item item) {
		SharedInjector.get().injectMembers(this);
		setItem(item);
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
	
	public void draw(Batch batch, float x, float y, float size, float parentAlpha) {
		if (sprite != null) {
			sprite.setBounds(x, y, size, size);
			sprite.draw(batch, parentAlpha);
		}
	}
}
