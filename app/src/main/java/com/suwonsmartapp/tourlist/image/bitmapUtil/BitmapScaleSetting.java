package com.suwonsmartapp.tourlist.image.bitmapUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by sol on 2015-04-08.
 */
public class BitmapScaleSetting {

    private static final String TAG = BitmapScaleSetting.class.getSimpleName();
    private String mPackageName;
    private int mImageSizeBoundary;
    private Context mContext;

    private File mTargetFile;



    /**
     * 생성자
     * @param packageName
     * @param imageSizeBoundary
     * @param context
     */
    public BitmapScaleSetting(String packageName, int imageSizeBoundary, Context context) {
        this.mPackageName = packageName;
        this.mImageSizeBoundary = imageSizeBoundary;
        this.mContext = context;
    }

    public String getmPackageName() {return mPackageName;}
    public void setmPackageName(String mPackageName) {this.mPackageName = mPackageName;}
    public int getmImageSizeBoundary() {return mImageSizeBoundary;}
    public void setmImageSizeBoundary(int mImageSizeBoundary) {this.mImageSizeBoundary = mImageSizeBoundary;}


    /**
     * 임시 파일 생성
     */
    public void setTempImageFile() {
        File path = new File(Environment.getExternalStorageDirectory() + "/Android/data/"
                + getmPackageName() + "/temp/");
        if (!path.exists()) {
            path.mkdirs();
        }
        mTargetFile = new File(path, "tempImage.png");
    }

    /**
     * Uri를 File로 만들기
     * @param srcUri
     */
    public void copyUriToFile(Uri srcUri) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            // 스트림 생성
            inputStream = (FileInputStream) mContext.getContentResolver().openInputStream(srcUri);
            outputStream = new FileOutputStream(mTargetFile);

            // 채널 생성
            fcin = inputStream.getChannel();
            fcout = outputStream.getChannel();

            // 채널을 통한 스트림 전송
            long size = fcin.size();
            fcin.transferTo(0, size, fcout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fcout.close();
            } catch (IOException ioe) {
            }
            try {
                fcin.close();
            } catch (IOException ioe) {
            }
            try {
                outputStream.close();
            } catch (IOException ioe) {
            }
            try {
                inputStream.close();
            } catch (IOException ioe) {
            }
        }
    }


    /**
     * 이미지 스케일링
     * @return
     */
    public Bitmap loadImageWithSampleSize() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mTargetFile.getAbsolutePath(), options);

        int width = options.outWidth;
        int height = options.outHeight;
        int longSide = Math.max(width, height);
        int sampleSize = 1;
        Log.d(TAG, "longSide : " + String.valueOf(longSide));

        if (longSide >= mImageSizeBoundary) {
            // sampleSize = longSide / mImageSizeBoundary;
            sampleSize *= 4;
        } else if (longSide >= mImageSizeBoundary / 2) {
            sampleSize *= 4;
        } else if (longSide >= mImageSizeBoundary / 4) {
            sampleSize *= 2;
        }

        Log.d(TAG, "sampleSize : " + String.valueOf(sampleSize));
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inDither = false;

        Bitmap bitmap = BitmapFactory.decodeFile(mTargetFile.getAbsolutePath(), options);
        return bitmap;
    }

    /**
     * mImageSizeBoundary 크기로 이미지 크기 조정. mImageSizeBoundary 보다 작은 경우 resize하지
     * 않음.
     */
    public Bitmap resizeImageWithinBoundary(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        if (width > height) {
            if (width > mImageSizeBoundary) {
                bitmap = resizeBitmapWithWidth(bitmap, mImageSizeBoundary);
            }
        } else {
            if (height > mImageSizeBoundary) {
                bitmap = resizeBitmapWithHeight(bitmap, mImageSizeBoundary);
            }
        }
        return bitmap;
    }

    private Bitmap resizeBitmapWithHeight(Bitmap source, int wantedHeight) {
        if (source == null)
            return null;

        int width = source.getWidth();
        int height = source.getHeight();

        float resizeFactor = wantedHeight * 1f / height;

        int targetWidth, targetHeight;
        targetWidth = (int) (width * resizeFactor);
        targetHeight = (int) (height * resizeFactor);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, true);

        return resizedBitmap;
    }

    private Bitmap resizeBitmapWithWidth(Bitmap source, int wantedWidth) {
        if (source == null)
            return null;

        int width = source.getWidth();
        int height = source.getHeight();

        float resizeFactor = wantedWidth * 1f / width;

        int targetWidth, targetHeight;
        targetWidth = (int) (width * resizeFactor);
        targetHeight = (int) (height * resizeFactor);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, true);

        return resizedBitmap;
    }



    /**
     * 비트맵 이미지를 파일로 저장하는 메서드
     *
     * @param bitmap
     */
    private void saveBitmapToFile(Bitmap bitmap) {
        try {
            FileOutputStream fos = new FileOutputStream(mTargetFile, false);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // 100 : max
            // quality
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
