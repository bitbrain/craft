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

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.SharedAssetManager;

public abstract class MenuScreen implements Screen {
	
	protected CraftGame game;
	
	private Sprite background;
	
	protected Batch batch;
	
	protected OrthographicCamera camera;		
	
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
			background.setBounds(
					camera.position.x - camera.viewportWidth / 2, 
					camera.position.y - camera.viewportHeight / 2, 
					camera.viewportWidth, 
					camera.viewportHeight);
			background.draw(batch);
			
			onDraw(batch, delta);
		batch.end();
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
		if (stage == null) {
			stage = createStage(width, height, batch);
			Gdx.input.setInputProcessor(stage);
			onCreateStage(stage);
		} else {		
			stage.getViewport().update(width, height, true);
		}
		
		camera.setToOrtho(true, width, height);
	}

	@Override
	public final void show() {
		camera = new OrthographicCamera();	
		batch = new SpriteBatch();
		background = new Sprite(SharedAssetManager.get(Assets.TEXTURE_BACKGROUND, Texture.class));
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
	
	protected abstract Stage createStage(int width, int height, Batch batch);
	
	protected abstract void onDraw(Batch batch, float delta);
	
	protected abstract void onShow();

}
