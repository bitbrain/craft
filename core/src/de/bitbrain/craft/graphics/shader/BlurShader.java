package de.bitbrain.craft.graphics.shader;


import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Blur shader which supports vertical and horizontal shading. It is also possible to set
 * the strength of the blur effect.
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.1
 * @version 0.1
 */
public class BlurShader extends AbstractShader<BlurShader> {
	
	public static final String VERTEX = "shaders/blur.vert";
	
	public static final String FRAGMENT = "shaders/blur.frag";
	
	private boolean horizontal;
	
	private float blurSize;

	
	public BlurShader(boolean horizontal, float blurSize) {
		super(VERTEX, FRAGMENT);
		this.blurSize = blurSize;
		this.horizontal = horizontal;
	}
	
	public BlurShader(boolean horizontal) {
		this(horizontal, 0.4f);
	}
	
	public BlurShader(float blurSize) {
		this(false, blurSize);
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	public void setBlurSize(float blurSize) {
		this.blurSize = blurSize;
	}

	/* (non-Javadoc)
	 * @see de.myreality.galacticum.graphics.shader.Shader#update(float)
	 */
	@Override
	public void update(float delta) {
		super.update(delta);
		
		ShaderProgram p = getProgram();
		
		p.setUniformf("blurSize", blurSize);
		p.setUniformi("horizontal", (horizontal) ? 1 : 0);
	}


}