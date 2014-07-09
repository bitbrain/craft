package de.bitbrain.craft.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.Resources;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.controls.TitleControls;


public class TitleScreen extends MenuScreen {
	
	private Sprite logo;	
	
	private TextButton btnPlay;

	public TitleScreen(CraftGame game) {
		super(game);
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onCreateStage(com.badlogic.gdx.scenes.scene2d.Stage)
	 */
	@Override
	protected void onCreateStage(Stage stage) {
		btnPlay = new TextButton("PLAY", Styles.TEXT_BUTTON);
		btnPlay.setWidth(100);
		btnPlay.setHeight(100);
		stage.addActor(btnPlay);
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onDraw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	protected void onDraw(Batch batch, float delta) {
		logo.setX(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 2);
		logo.setScale(Gdx.graphics.getWidth() / 550.0f);
		logo.setY(Gdx.graphics.getHeight() / 5.0f);
		logo.draw(batch);
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#createStage()
	 */
	@Override
	protected Stage createStage(int width, int height) {
		return new TitleControls(new FitViewport(width, height));
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onShow()
	 */
	@Override
	protected void onShow() {
		logo = new Sprite(SharedAssetManager.get(Resources.TEXTURE_LOGO, Texture.class));
		logo.flip(false, true);
	}

	
}	
