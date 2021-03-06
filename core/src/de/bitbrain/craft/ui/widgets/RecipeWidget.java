/*
 * Craft - Crafting game for Android, PC and Browser.
 * Copyright (C) 2014 Miguel Gonzalez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package de.bitbrain.craft.ui.widgets;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.engio.mbassy.listener.Handler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.google.inject.Inject;

import de.bitbrain.craft.Bundles;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.ItemBag;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.ItemEvent;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.ui.Tabs;
import de.bitbrain.craft.ui.Tooltip;
import de.bitbrain.craft.ui.widgets.IconWidget.IconHandle;

/**
 * Provides the view of a single recipe. This view is blocked by default. If a recipe has been clicked, it will open
 * instantly with further information.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class RecipeWidget extends Table {

  @Inject
  private TabWidget tabPanel;

  @Inject
  private EventBus eventBus;

  @Inject
  private API api;

  private Map<Item, IconWidget> materialSet;

  @PostConstruct
  public void init() {
    materialSet = new HashMap<Item, IconWidget>();
    eventBus.subscribe(this);
  }

  @Handler
  public void onEvent(MouseEvent<?> event) {
    if (event.getModel() instanceof Item && event.getType() == EventType.CLICK) {
      Item item = (Item) event.getModel();
      if (api.canCraft(Player.getCurrent(), item.getId())) {
        tabPanel.setTab(Tabs.CRAFTING);
        if (!isModified()) {
          clear();
          add(generateTop(item)).row();
          String description = Bundles.itemDescriptions.get(item.getId().toString());
          if (!description.isEmpty()) {
            add(generateDescription(item)).fillX().row();
          }
          add(generateMaterials(item)).row();
        }
      }
    }
  }

  @Handler
  public void onItemEvent(ItemEvent event) {
    Item item = event.getModel();
    IconWidget widget = materialSet.get(item);
    if (widget != null) {
      switch (event.getType()) {
        case ADD:
          widget.addAmount(event.getAmount());
          break;
        case REMOVE:
          widget.reduceAmount(event.getAmount());
          break;
        default:
          break;
      }
    }
  }

  private boolean isModified() {
    return false;
  }

  private Actor generateDescription(Item item) {
    Container<Label> container =
        new Container<Label>(new Label(Bundles.itemDescriptions.get(item.getId().toString()), Styles.LBL_BROWN));
    container.padTop(20f).padLeft(10f).align(Align.left);
    return container;
  }

  private Actor generateTop(Item item) {
    HorizontalGroup group = new HorizontalGroup();
    group.align(Align.left);
    IconWidget icon = new IconWidget(item, 0);
    icon.setHandle(icon.new DefaultIconHandle() {
      @Override
      public boolean isDraggable(int amount) {
        return false;
      }

      @Override
      public boolean isVisible(int currentAmount) {
        return false;
      }
    });
    group.addActor(icon);
    HorizontalGroup wrapper = new HorizontalGroup();
    Label caption = new Label(Bundles.items.get(item.getId().toString()), Styles.LBL_ITEM);
    caption.setColor(item.getRarity().getColor());
    icon.setWidth(caption.getHeight() * 4);
    icon.setHeight(caption.getHeight() * 4);
    wrapper.addActor(caption);
    wrapper.padLeft(15f);
    group.addActor(wrapper);
    return group;
  }

  private Actor generateMaterials(Item item) {
    materialSet.clear();
    Table table = new Table();
    addCaption(Bundles.MATERIALS, table);
    Table materialTable = new Table();
    table.add(materialTable);
    ItemBag materials = api.findIngredients(item);
    materialTable.setWidth(500f);
    int index = 0;
    for (Entry<Item, Integer> entry : materials) {
      int amount = api.getItemAmount(entry.getKey());
      IconWidget widget = new IconWidget(entry.getKey(), amount);
      materialSet.put(entry.getKey(), widget);
      widget.setHandle(new MaterialIconHandle(entry.getValue()));
      widget.setWidth(Sizes.MATERIAL_ICON);
      widget.setHeight(Sizes.MATERIAL_ICON);
      Tooltip.create(widget).text(Bundles.items.get(entry.getKey().getId().toString()));
      Cell<IconWidget> cell = materialTable.add(widget);
      if (index > 0 && index % 2 == 0) {
        cell.row();
      }
      index++;
    }
    return table;
  }

  private void addCaption(String key, Table target) {
    Label label = new Label(Bundles.general.get(key), Styles.LBL_CAPTION);
    target.add(label).padTop(55f).padBottom(25f).row();
  }

  private class MaterialIconHandle implements IconHandle {

    private int required;

    public MaterialIconHandle(int required) {
      this.required = required;
    }

    @Override
    public Color getColor(int currentAmount) {
      return currentAmount == Item.INFINITE_AMOUNT || currentAmount >= required ? Color.GREEN : Color.RED;
    }

    @Override
    public String getContent(int currentAmount) {
      if (currentAmount != Item.INFINITE_AMOUNT) {
        return currentAmount + "/" + required;
      } else {
        return "INF/" + required;
      }
    }

    @Override
    public boolean isVisible(int currentAmount) {
      return true;
    }

    @Override
    public boolean isDraggable(int amount) {
      return amount > 0 || amount == Item.INFINITE_AMOUNT;
    }

    @Override
    public int getDragAmount() {
      return required;
    }

  }
}