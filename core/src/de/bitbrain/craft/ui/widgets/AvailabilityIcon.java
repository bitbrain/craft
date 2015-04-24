package de.bitbrain.craft.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Bundles;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.ui.Tooltip;

public class AvailabilityIcon extends Actor {

  private static final float SIZE = 16;

  @Inject
  private API api;

  private Sprite sprite;

  private Tooltip tooltip;

  public AvailabilityIcon(Item item) {
    SharedInjector.get().injectMembers(this);
    tooltip = Tooltip.create(this);
    setItem(item);
    setWidth(SIZE);
    setHeight(SIZE);
  }

  public void setItem(Item item) {
    if (api.canCraft(item.getId())) {
      sprite = new Sprite(SharedAssetManager.get(Assets.TEX_CHECK, Texture.class));
      sprite.setColor(Color.GREEN);
      tooltip.text(Bundles.general.get("craftable"));
    } else if (api.canCraftIndirect(item.getId())) {
      sprite = new Sprite(SharedAssetManager.get(Assets.TEX_CHECK, Texture.class));
      sprite.setColor(Color.CYAN);
      tooltip.text(Bundles.general.get("craftable_external"));
    } else {
      sprite = null;
    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    if (sprite != null) {
      sprite.setBounds(getX(), getY(), getWidth(), getHeight());
      sprite.draw(batch, parentAlpha);
    }
  }
}
