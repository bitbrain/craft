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

package de.bitbrain.craft.inject;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.db.DriverProvider;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MBassadorEventBus;
import de.bitbrain.craft.graphics.IconManager;
import de.bitbrain.craft.graphics.ParticleRenderer;
import de.bitbrain.craft.graphics.shader.ShaderManager;
import de.bitbrain.craft.graphics.shader.SimpleShaderManager;
import de.bitbrain.craft.migration.DataMigrator;
import de.bitbrain.craft.screens.IngameScreen;
import de.bitbrain.craft.screens.LoadingScreen;
import de.bitbrain.craft.screens.ProfessionScreen;
import de.bitbrain.craft.screens.TitleScreen;
import de.bitbrain.craft.ui.DragDropHandler;
import de.bitbrain.craft.ui.TooltipManager;
import de.bitbrain.craft.ui.cli.CommandLineInterface;
import de.bitbrain.jpersis.JPersis;

/**
 * Module to provide screen injection
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class StateModule extends AbstractModule {

  private JPersis jpersis;

  /*
   * (non-Javadoc)
   * 
   * @see com.google.inject.AbstractModule#configure()
   */
  @Override
  protected void configure() {
    install(PostConstructModule.INSTANCE);
    StateScope scope = new StateScope();
    bindScope(StateScoped.class, scope);
    bind(StateScope.class).annotatedWith(Names.named("stateScope")).toInstance(scope);
    bind(OrthographicCamera.class).in(StateScoped.class);
    bind(Camera.class).to(OrthographicCamera.class).in(StateScoped.class);
    bind(ShaderManager.class).to(SimpleShaderManager.class).in(StateScoped.class);
    bind(ParticleRenderer.class);
    bind(EventBus.class).to(MBassadorEventBus.class);
    bind(DragDropHandler.class);
    bind(IconManager.class);
    bind(TweenManager.class).in(StateScoped.class);
    bind(CraftGame.class).asEagerSingleton();
    bind(CommandLineInterface.class).asEagerSingleton();
    bind(TooltipManager.class).asEagerSingleton();
    bind(LoadingScreen.class);
    bind(TitleScreen.class);
    bind(IngameScreen.class);
    bind(ProfessionScreen.class);
    bind(DataMigrator.class);
  }

  @Provides
  public JPersis provideJPersis() {
    if (jpersis == null) {
      jpersis = new JPersis(DriverProvider.getDriver());
    }
    return jpersis;
  }
}