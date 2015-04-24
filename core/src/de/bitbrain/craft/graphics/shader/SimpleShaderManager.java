package de.bitbrain.craft.graphics.shader;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import de.bitbrain.craft.Sizes;

/**
 * Simple implementation of {@link ShaderManager}
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class SimpleShaderManager implements ShaderManager {

  private List<ShaderData> data;

  private FrameBuffer initialBuffer, bufferA, bufferB;

  public SimpleShaderManager() {
    data = new ArrayList<ShaderData>();
    initBuffers(Sizes.worldWidth(), Sizes.worldHeight());
  }

  @Override
  public void add(ShadeArea shaderTarget, Shader<?>... shaders) {
    ShaderData shaderData = new ShaderData(shaderTarget, shaders);
    data.add(shaderData);
  }

  @Override
  public void updateAndRender(Batch batch, float delta) {

    FrameBuffer previousBuffer = initialBuffer;
    FrameBuffer currentBuffer = bufferA;

    // Iterate through each shader data and apply a buffer to it
    for (int dataIndex = 0; dataIndex < data.size(); ++dataIndex) {
      ShaderData shaderData = data.get(dataIndex);
      ShadeArea area = shaderData.getTarget();
      Shader<?>[] shaders = shaderData.getShaders();

      // Draw the area onto the previous buffer
      if (shaders.length > 0) {
        previousBuffer.begin();
      }
      drawTo(delta, batch, area);
      if (shaders.length > 0) {
        previousBuffer.end();
      }

      for (int index = 0; index < shaders.length; ++index) {

        Shader<?> shader = shaders[index];
        currentBuffer = flipBuffer(currentBuffer);

        // If it's not the last element, draw to the buffer. Otherwise
        // draw to screen
        if (index < shaders.length - 1 || dataIndex < data.size() - 1) {
          currentBuffer.begin();
          drawTo(previousBuffer, delta, batch, shader);
          currentBuffer.end();
        } else {
          drawTo(previousBuffer, delta, batch, shader);
        }

        previousBuffer = currentBuffer;
      }
    }
  }

  @Override
  public void clear() {
    data.clear();
  }

  @Override
  public int size() {
    return data.size();
  }

  @Override
  public boolean isEmpty() {
    return data.isEmpty();
  }

  @Override
  public void dispose() {
    initialBuffer.dispose();
    bufferA.dispose();
    bufferB.dispose();
  }

  @Override
  public void resize(int width, int height) {
    initialBuffer.dispose();
    bufferA.dispose();
    bufferB.dispose();
    initBuffers(width, height);
  }

  private void drawTo(FrameBuffer buffer, float delta, Batch batch, Shader<?> shader, ShadeArea area) {

    // Apply the current shader
    if (shader != null) {
      batch.setShader(shader.getProgram());
    } else {
      batch.setShader(null);
    }

    batch.begin();
    if (shader != null) // Update shader
      shader.update(delta);
    if (buffer != null) // Draw buffer
      batch.draw(buffer.getColorBufferTexture(), 0f, 0f);
    if (area != null) // Draw area
      area.draw(batch, delta);
    batch.end();

    // Send the data directly through the graphics pipeline
    batch.flush();

    // Reset the current shader
    batch.setShader(null);
  }

  private void drawTo(float delta, Batch batch, ShadeArea area) {
    drawTo(null, delta, batch, null, area);
  }

  private void drawTo(FrameBuffer buffer, float delta, Batch batch, Shader<?> shader) {
    drawTo(buffer, delta, batch, shader, null);
  }

  private FrameBuffer flipBuffer(FrameBuffer current) {
    return current.equals(bufferA) ? bufferB : bufferA;
  }

  private void initBuffers(int width, int height) {
    initialBuffer = new FrameBuffer(Format.RGBA4444, width, height, false);
    bufferA = new FrameBuffer(Format.RGBA4444, width, height, false);
    bufferB = new FrameBuffer(Format.RGBA4444, width, height, false);
  }

  private class ShaderData {

    private Shader<?>[] shaders;

    private ShadeArea target;

    /**
     * @param shaders
     * @param target
     */
    public ShaderData(ShadeArea target, Shader<?>[] shaders) {
      super();
      this.shaders = shaders;
      this.target = target;
    }

    /**
     * @return the shaders
     */
    public Shader<?>[] getShaders() {
      return shaders;
    }

    /**
     * @return the target
     */
    public ShadeArea getTarget() {
      return target;
    }

  }

}