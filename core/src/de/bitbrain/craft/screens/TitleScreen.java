package de.bitbrain.craft.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.CraftGame;
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
		stage.addActor(btnPlay);
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onDraw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	protected void onDraw(Batch batch, float delta) {		
		logo.draw(batch);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		float scale = Gdx.graphics.getWidth() / 550.0f;
		
		logo.setX(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 2);
		logo.setScale(scale);
		logo.setY(Gdx.graphics.getHeight() / 4.1f);
		
		btnPlay.setBounds(0, 0, logo.getWidth(), logo.getHeight());
		btnPlay.setScale(scale * 3);
		btnPlay.setX(Gdx.graphics.getWidth() / 2.0f - btnPlay.getWidth() / 2.0f);
		btnPlay.setY(Gdx.graphics.getHeight() / 4.1f);
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#createStage()
	 */
	@Override
	protected Stage createStage(int width, int height, Batch batch) {
		return new TitleControls(new FitViewport(width, height), batch);
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onShow()
	 */
	@Override
	protected void onShow() {
		logo = new Sprite(SharedAssetManager.get(Assets.TEXTURE_LOGO, Texture.class));
		logo.flip(false, true);
	}

	
}	
