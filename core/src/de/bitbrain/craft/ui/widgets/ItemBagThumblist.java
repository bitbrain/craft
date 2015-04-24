package de.bitbrain.craft.ui.widgets;

import java.util.HashMap;
import java.util.Map;

import net.engio.mbassy.listener.Handler;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.inject.Inject;

import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.ItemEvent;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.models.Item;

@StateScoped
public class ItemBagThumblist extends Table {

  private Map<ItemId, IconWidget> icons;

  @Inject
  private EventBus eventBus;

  @PostConstruct
  public void initView() {
    eventBus.subscribe(this);
    icons = new HashMap<ItemId, IconWidget>();
  }

  @Handler
  private void onCraftEvent(ItemEvent event) {
    Item item = event.getModel();
    if (event.getType().equals(EventType.CRAFT_SUBMIT)) {
      if (icons.containsKey(item.getId())) {
        IconWidget icon = new IconWidget(item, event.getAmount());
        icon.setWidth(75f);
        icon.setHeight(75f);
        add(icon).row();
      } else {
        icons.get(item.getId()).addAmount(event.getAmount());
      }
    } else if (event.getType().equals(EventType.CRAFT_REMOVE)) {

    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);

  }
}
