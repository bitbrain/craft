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

import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.Resources;
import de.bitbrain.craft.SharedAssetManager;

public class MenuScreen implements Screen {
	
	protected CraftGame game;
	
	private Sprite background;
	
	private Batch batch;
	
	private OrthographicCamera camera;		
	
	private Stage stage;
	
	public MenuScreen(CraftGame game) {
		this.game = game;		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
			background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			background.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(true, width, height);
	}

	@Override
	public void show() {
		camera = new OrthographicCamera();	
		batch = new SpriteBatch();
		background = new Sprite(SharedAssetManager.getInstance().get(Resources.TEXTURE_BACKGROUND, Texture.class));
		background.flip(false, true);
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

}
