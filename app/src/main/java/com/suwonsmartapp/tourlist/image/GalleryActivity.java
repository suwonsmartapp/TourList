package com.suwonsmartapp.tourlist.image;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.suwonsmartapp.tourlist.R;
import com.suwonsmartapp.tourlist.image.adapter.GridAdapter;
import com.suwonsmartapp.tourlist.image.bitmapUtil.BitmapScaleSetting;
import com.suwonsmartapp.tourlist.image.util.GetMaxTextureSize;

import java.util.ArrayList;
import java.util.List;


public class GalleryActivity extends ActionBarActivity implements View.OnClickListener {

    // 상수
    private static final String TAG = GalleryActivity.class.getSimpleName();
    private static final int SELECT_FROM_GALLERY = 1;

    // Resource
    private Button mBtnGetImg;
    private GridView mGridView;

    private BitmapScaleSetting mBitmapScaleSetting;

    // Data
    private List<Uri> mUriList;

    // Adapter
    private GridAdapter mGridAdapter;

    private void init() {
        mBtnGetImg = (Button) findViewById(R.id.btn_get_img);
        mGridView = (GridView) findViewById(R.id.gv_each_image);

        // Data
        mUriList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Data init
        init();

        // get a phone's max texture size
        GetMaxTextureSize getMaxTextureSize = new GetMaxTextureSize();
        mBitmapScaleSetting = new BitmapScaleSetting(getPackageName(), getMaxTextureSize.getMaxTextureSize(), getApplicationContext());


        /*
            String cacheName = "gallery";
            FileCacheFactory.initialize(this);
            if (!FileCacheFactory.getInstance().has(cacheName)) {
                FileCacheFactory.getInstance().create(cacheName, cacheSize);
            }
            mFileCache = FileCacheFactory.getInstance().get(cacheName);

            // 이미지 캐시 초기화
            int memoryImageMaxCounts = 20;
            ImageCacheFactory.getInstance().createTwoLevelCache(cacheName, memoryImageMaxCounts);
            mImageCache = ImageCacheFactory.getInstance().get(cacheName);
        */


        // Adapter
        // View
        mGridAdapter = new GridAdapter(getApplicationContext(), mUriList, mBitmapScaleSetting);
        mGridView.setAdapter(mGridAdapter);


        // click listener 바인딩
        mBtnGetImg.setOnClickListener(this);

    } // onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_img:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, SELECT_FROM_GALLERY);
                break;
        }
    } // onClick



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SELECT_FROM_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                //Log.d(TAG, "image data : " + data.toString());

                Uri uri = data.getData(); // Received Data from the intent
                //Log.d(TAG, "getPath() : " + uri.getPath());
                //Log.d(TAG, "uri.getEncodedPath() : " + uri.getEncodedPath());

                mUriList.add(uri);
                mBitmapScaleSetting.setTempImageFile();

                mGridAdapter.setmShowBtns(false);
                mGridAdapter.notifyDataSetChanged();
            }
        }
    }


}
