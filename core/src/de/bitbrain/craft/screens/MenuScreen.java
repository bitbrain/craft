package de.bitbrain.craft.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.Resources;
import de.bitbrain.craft.SharedAssetManager;

public abstract class MenuScreen implements Screen {
	
	protected CraftGame game;
	
	private Sprite background;
	
	private Batch batch;
	
	private OrthographicCamera camera;		
	
	protected Stage stage;
	
	public MenuScreen(CraftGame game) {
		this.game = game;		
	}

	@Override
	public final void render(float delta) {
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		camera.update();		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
			background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			background.draw(batch);
			
			onDraw(batch, delta);
		batch.end();
		
		stage.draw();
	}

	@Override
	public final void resize(int width, int height) {
		
		if (stage == null) {
			stage = createStage(width, height);
			onCreateStage(stage);
		} else {		
			stage.setViewport(new FitViewport(width, height));
		}
		
		camera.setToOrtho(true, width, height);
	}

	@Override
	public final void show() {
		camera = new OrthographicCamera();	
		batch = new SpriteBatch();
		background = new Sprite(SharedAssetManager.getInstance().get(Resources.TEXTURE_BACKGROUND, Texture.class));
		background.flip(false, true);
		
		onShow();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void hide() { }

	@Override
	public void pause() { }

	@Override
	public void resume() { }
	
	protected abstract void onCreateStage(Stage stage);
	
	protected abstract Stage createStage(int width, int height);
	
	protected abstract void onDraw(Batch batch, float delta);
	
	protected abstract void onShow();

}
