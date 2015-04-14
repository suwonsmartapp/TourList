
package com.suwonsmartapp.tourlist.image;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.suwonsmartapp.tourlist.R;
import com.suwonsmartapp.tourlist.ResultsActivity;
import com.suwonsmartapp.tourlist.database.Info_PHOTODT;
import com.suwonsmartapp.tourlist.database.TourListFacade;
import com.suwonsmartapp.tourlist.image.adapter.GridAdapter;
import com.suwonsmartapp.tourlist.image.bitmapUtil.BitmapScaleSetting;
import com.suwonsmartapp.tourlist.image.util.GetMaxTextureSize;

import java.math.BigDecimal;
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
    private ArrayList<Uri> mUriList;
    private ArrayList<String> mPathList;

    // Adapter
    private GridAdapter mGridAdapter;

    private long mSavedTourId;
    private int mIntMId;

    private void init() {
        mBtnGetImg = (Button) findViewById(R.id.btn_get_img);
        mGridView = (GridView) findViewById(R.id.gv_each_image);

        // Data
        mUriList = new ArrayList<>();
        mPathList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Data init
        init();

        // get a phone's max texture size
        GetMaxTextureSize getMaxTextureSize = new GetMaxTextureSize();
        mBitmapScaleSetting = new BitmapScaleSetting(getPackageName(),
                getMaxTextureSize.getMaxTextureSize(), getApplicationContext());

        /*
         * String cacheName = "gallery"; FileCacheFactory.initialize(this); if
         * (!FileCacheFactory.getInstance().has(cacheName)) {
         * FileCacheFactory.getInstance().create(cacheName, cacheSize); }
         * mFileCache = FileCacheFactory.getInstance().get(cacheName); // 이미지 캐시
         * 초기화 int memoryImageMaxCounts = 20;
         * ImageCacheFactory.getInstance().createTwoLevelCache(cacheName,
         * memoryImageMaxCounts); mImageCache =
         * ImageCacheFactory.getInstance().get(cacheName);
         */

        // Adapter
        // View
        mGridAdapter = new GridAdapter(getApplicationContext(), mUriList, mBitmapScaleSetting);
        mGridView.setAdapter(mGridAdapter);

        // click listener 바인딩
        mBtnGetImg.setOnClickListener(this);

        Intent intent = getIntent();

        if (intent != null) {
            mSavedTourId = Long.valueOf(getIntent().getLongExtra("id", -1)); // 연결된
                                                                             // 글의
                                                                             // 아이디
            mIntMId = new BigDecimal(mSavedTourId).intValueExact();

            Log.d("GalleryActivity TAG", String.valueOf(mSavedTourId));
            Toast.makeText(getApplicationContext(), "저장된 아이디 : " + String.valueOf(mSavedTourId),
                    Toast.LENGTH_SHORT).show();
        }

    } // onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_img:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

                // Log.d(TAG, "image data : " + data.toString());
                // Log.d(TAG, "getPath() : " + uri.getPath());
                // Log.d(TAG, "uri.getEncodedPath() : " + uri.getEncodedPath());

                mUriList.add(uri);
                mBitmapScaleSetting.setTempImageFile();

                mGridAdapter.setmShowBtns(false);
                mGridAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_gallery, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.item_picture_load_complete: // 사진 선택 완료
                Log.d(TAG, "사진 선택 완료");

                final int mainImgPosition = mGridAdapter.getmMainImgPosition();

                if (mainImgPosition == -1) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(GalleryActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // 닫기
                        }
                    });
                    alert.setMessage("대표 이미지를 선택해 주세요!");
                    alert.show();

                    return false;
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(GalleryActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // 닫기

                            /*
                             * 사진 DB에 저장
                             */
                            for (int i = 0; i < mPathList.size(); i++) {
                                long savedPictureId = savePictureToDB(mPathList.get(i));

                                if (mainImgPosition != -1 && mainImgPosition == i) { // 대표이미지
                                    saveMainPictureToDB(savedPictureId);
                                }
                            }

                            // mPathList 에 절대경로 리스트 저장
                            makePathList(mUriList);

                            Intent intent = new Intent(getApplicationContext(),
                                    ResultsActivity.class);
                            intent.putExtra("picturePathList", mPathList);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                    alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // 닫기
                        }
                    });
                    alert.setMessage("저장 하시겠습니까?");
                    alert.show();

                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void makePathList(List<Uri> uriList) {
        int size = uriList.size();
        for (int i = 0; i < size; i++) {
            mPathList.add(getAbsolutePathFromUri(uriList.get(i)));
        }
    }


    private String getAbsolutePathFromUri(Uri uri) {
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();

        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        Log.d("path", path);

        return path;
    }


    /*
     * 메인이미지 저장
     */
    private long saveMainPictureToDB(long pId) {
        TourListFacade tourListFacade = new TourListFacade(getApplicationContext());
        int intPid = new BigDecimal(pId).intValueExact();
        long savedId = tourListFacade.saveMainPicture(mIntMId, intPid);
        Log.d("Gallery TAG(main save)", String.valueOf(savedId)); // up count
        return savedId;
    }

    /*
     * 이미지 한장씩 DB에 저장
     */
    private long savePictureToDB(String path) {
        TourListFacade tourListFacade = new TourListFacade(getApplicationContext());
        Info_PHOTODT info_photodt = new Info_PHOTODT(mIntMId, path);
        long savedId = tourListFacade.savePicture(info_photodt);
        return savedId;
    }

    private Info_PHOTODT makeInfoPhoto(String path) {
        Info_PHOTODT info_photodt = new Info_PHOTODT(mIntMId, path);
        return info_photodt;
    }

}
