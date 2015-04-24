package de.bitbrain.craft.android;

import android.os.Build;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.android.util.HeapAllocator;
import de.bitbrain.craft.inject.SharedInjector;

public class AndroidLauncher extends AndroidApplication {

  HeapAllocator heapAllocator = new HeapAllocator();

  public AndroidLauncher() {
    // Extend the existing heap to avoid GC overflow
    // This only works in Android 3.x or higher (see issue #36)
    if (Build.VERSION.SDK_INT > 11) {
      final int MB = 10;
      heapAllocator.allocate(1024 * MB);

    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
    initialize(SharedInjector.get().getInstance(CraftGame.class), config);
    heapAllocator.free();
  }
}
