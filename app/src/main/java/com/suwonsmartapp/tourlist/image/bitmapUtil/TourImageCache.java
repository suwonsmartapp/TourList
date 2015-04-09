package com.suwonsmartapp.tourlist.image.bitmapUtil;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by sol on 2015-04-09.
 */
public class TourImageCache {

    /*
        public class Singleton{
          private volatile static SingleTon uniqueInstance;
          private Singleton() {}

          public static Singleton getInstance(){
            if(uniqueInstance == null){
              synchronized(Singleton.class){
                if(uniqueInstance == null){
                  uniqueInstance = new Singleton();
                }
              }
            }
            return uniqueInstance;
          }
        }
    * */

    private static TourImageCache tourImageCache;


    // Cache
    // Get max available VM memory, exceeding this amount will throw an
    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
    // int in its constructor.
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    // Use 1/8th of the available memory for this memory cache.
    final int cacheSize = maxMemory / 8;

    private LruCache<String, Bitmap> mMemoryCache;

    private TourImageCache() {
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public static TourImageCache getInstance(){
        if(tourImageCache == null){
            synchronized(TourImageCache.class){
                if(tourImageCache == null){
                    tourImageCache = new TourImageCache();
                }
            }
        }
        return tourImageCache;
    }

    public LruCache<String, Bitmap> getmMemoryCache() {
        return mMemoryCache;
    }
}
