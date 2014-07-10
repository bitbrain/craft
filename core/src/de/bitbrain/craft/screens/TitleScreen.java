package de.bitbrain.craft.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.controls.TitleControls;


public class TitleScreen extends MenuScreen {
	
	private Sprite logo;	
	
	private TextButton btnPlay;
	
	private Label lblCredits;

	public TitleScreen(CraftGame game) {
		super(game);
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onCreateStage(com.badlogic.gdx.scenes.scene2d.Stage)
	 */
	@Override
	protected void onCreateStage(Stage stage) {
		btnPlay = new TextButton("PLAY", Styles.TEXT_BUTTON);	
		
		final CraftGame tempGame = game;
		
		btnPlay.addCaptureListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				tempGame.setScreen(new ProfessionScreen(tempGame));
			}
			
		});
		stage.addActor(btnPlay);
		
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.fontColor = Assets.COLOR_WOOD_TEAK;
		lblStyle.font =  SharedAssetManager.get(Assets.FONT_SMALL, BitmapFont.class);
		lblCredits = new Label("a game by Miguel Gonzalez", lblStyle);
		stage.addActor(lblCredits);
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
		logo.setY(Gdx.graphics.getHeight() / 4.1f);
		logo.setScale(scale);
		
		btnPlay.setWidth(logo.getWidth() * scale / 1.5f);
		btnPlay.setHeight(logo.getHeight() * scale);
		btnPlay.setX(Gdx.graphics.getWidth() / 2.0f - btnPlay.getWidth() / 2.0f);
		btnPlay.setY(Gdx.graphics.getHeight() / 4.1f);
		btnPlay.getLabel().setFontScale(scale / 1.5f);
		
		lblCredits.setFontScale(scale / 1.5f);
		lblCredits.setX(Gdx.graphics.getWidth() / 2 - (lblCredits.getWidth() * lblCredits.getFontScaleX()) / 2);
		lblCredits.setY(Gdx.graphics.getHeight() / 15f);
		
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#createStage()
	 */
	@Override
	protected Stage createStage(int width, int height, Batch batch) {
		return new TitleControls(new ScreenViewport(), batch);
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
