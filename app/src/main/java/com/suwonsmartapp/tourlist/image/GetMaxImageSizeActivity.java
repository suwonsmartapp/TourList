
package com.suwonsmartapp.tourlist.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import com.suwonsmartapp.tourlist.R;
import com.suwonsmartapp.tourlist.image.bitmapUtil.Constant;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 */
public class GetMaxImageSizeActivity extends ActionBarActivity {

    private static final String TAG = GetMaxImageSizeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_max_image_size);

        // 1px이라도 레이아웃이 들어가야 surfaceCreated 같은 콜백메서드가 실행됨
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_image_test);
        layout.addView(new GetMaxTextureSizeSurfaceView(this));
    }

    private class GetMaxTextureSizeSurfaceView extends SurfaceView implements
            SurfaceHolder.Callback {

        public GetMaxTextureSizeSurfaceView(Context context) {
            super(context);
            SurfaceHolder holder = getHolder();
            holder.addCallback(this);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            setMaxTextureSize();
            goHome();
        }

        private void setMaxTextureSize() {
            EGL10 egl10 = (EGL10) EGLContext.getEGL();
            EGLContext ctx = egl10.eglGetCurrentContext();
            GL10 gl10 = (GL10) ctx.getGL();
            IntBuffer val = IntBuffer.allocate(1);
            gl10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, val);
            int size = val.get(); // 최대 크기 구함
            Log.d(TAG, "imageSizeBoundary : " + String.valueOf(size));
            Constant.setMaxTextureSize(size);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    }

    private void goHome() {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
        finish();
    }
}
