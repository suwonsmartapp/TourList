
package com.suwonsmartapp.tourlist.image.util;

import android.util.Log;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * Created by sol on 2015-04-09.
 */
public class GetMaxTextureSize {

    /*
     * // 0 나오는데? public int getMaxTextureSize(){ int[] maxTextureSize = new
     * int[1]; GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxTextureSize,
     * 0); Log.i("glinfo", "Max texture size = " + maxTextureSize[0]); return
     * maxTextureSize[0]; }
     */

    /*    */
    public int getMaxTextureSize()
    {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

        // Initialise
        int[] version = new int[2];
        egl.eglInitialize(display, version);

        // Query total number of configurations
        int[] totalConfigurations = new int[1];
        egl.eglGetConfigs(display, null, 0, totalConfigurations);

        // Query actual list configurations
        EGLConfig[] configurationsList = new EGLConfig[totalConfigurations[0]];
        egl.eglGetConfigs(display, configurationsList, totalConfigurations[0], totalConfigurations);

        int[] textureSize = new int[1];
        int maximumTextureSize = 0;

        // Iterate through all the configurations to located the maximum texture
        // size
        for (int i = 0; i < totalConfigurations[0]; i++)
        {
            // Only need to check for width since opengl textures are always
            // squared
            egl.eglGetConfigAttrib(display, configurationsList[i], EGL10.EGL_MAX_PBUFFER_WIDTH,
                    textureSize);

            // Keep track of the maximum texture size
            if (maximumTextureSize < textureSize[0])
            {
                maximumTextureSize = textureSize[0];
            }

            // Log.i("GLHelper", Integer.toString(textureSize[0]));
        }

        // Release
        egl.eglTerminate(display);
        Log.i("GLHelper", "Maximum GL texture size: " + Integer.toString(maximumTextureSize));

        return maximumTextureSize;
    }

}
