package de.bitbrain.craft.graphics.shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Abstract implementation of {@link Shader}
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public abstract class AbstractShader<Type extends Shader<Type>> implements
		Shader<Type> {

	private ShaderProgram program;

	private ShaderBehavior<Type> behavior;

	public AbstractShader(String vert, String frag) {
		FileHandle vertHandle = Gdx.files.internal(vert);
		FileHandle fragHandle = Gdx.files.internal(frag);
		program = new ShaderProgram(vertHandle, fragHandle);
	}

	@Override
	public ShaderProgram getProgram() {
		return program;
	}

	@Override
	public void setBehavior(ShaderBehavior<Type> behavior) {
		this.behavior = behavior;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(float delta) {
		if (behavior != null) {
			behavior.update(delta, (Type) this);
		}
	}

}