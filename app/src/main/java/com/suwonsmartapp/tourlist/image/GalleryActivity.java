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
import com.suwonsmartapp.tourlist.image.bitmapUtil.Constant;
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
        Constant.setMaxTextureSize(getMaxTextureSize.getMaxTextureSize());
        mBitmapScaleSetting = new BitmapScaleSetting(getPackageName(), Constant.getMaxTextureSize(), getApplicationContext());


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
                Uri uri = data.getData(); // Received Data from the intent

                mUriList.add(uri);
                mBitmapScaleSetting.setTempImageFile();

                mGridAdapter.setmShowBtns(false);
            }
        }
    }



    /*
        private void addBitmapToImgList(String path) {
            // sample size 를 적용하여 bitmap load.
            Bitmap bitmap = mBitmapScaleSetting.loadImageWithSampleSize();
            // Bitmap bitmap =BitmapSetting.decodeSampledBitmapFromFile(getTempImageFile(), 100, 100);
            // Bitmap bitmap =BitmapSetting.decodeSampledBitmapFromFile(getTempImageFile(), mImageSizeBoundary);

            // image boundary size 에 맞도록 이미지 축소.
            bitmap = mBitmapScaleSetting.resizeImageWithinBoundary(bitmap);

            // 결과 file 을 얻어갈 수 있는 메서드 제공.
            // saveBitmapToFile(bitmap);

            // show image on ImageView (저장한 파일 읽어와서 출력하는 듯)
            // Bitmap bm = BitmapFactory.decodeFile(getTempImageFile().getAbsolutePath());
            // mImageView.setImageBitmap(bitmap);

            TourImage tourImage = new TourImage(path, bitmap);
            mImgList.add(tourImage);
        }

    public void addBitmapToImgListUsingCache(String path) {

        // 이미지 캐시 사용 부분
        Bitmap bitmapInCache = mImageCache.getBitmap(path);

        if (bitmapInCache == null) {
            Bitmap loadedBitmap = mBitmapScaleSetting.loadImageWithSampleSize();
            loadedBitmap = mBitmapScaleSetting.resizeImageWithinBoundary(loadedBitmap);
            bitmapInCache = addBitmapToCache(path, loadedBitmap);
        }

        TourImage tourImage = new TourImage(path, bitmapInCache);
        mImgList.add(tourImage);
    }

    private Bitmap addBitmapToCache(String path, Bitmap bitmap) {
        mImageCache.addBitmap(path, bitmap);
        return mImageCache.getBitmap(path);
    }

    */

}
