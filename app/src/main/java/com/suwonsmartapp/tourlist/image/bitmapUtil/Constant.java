package com.suwonsmartapp.tourlist.image.bitmapUtil;

/**
 * Created by sol on 2015-04-02.
 */
public class Constant {

    // 폰에서 로딩할 수 있는 최대 픽셀 수(ex. 갤S4 : 4096)
    private static int maxTextureSize;

    public static void setMaxTextureSize(int pMaxTextureSize) {
        maxTextureSize = pMaxTextureSize;
    }
    public static int getMaxTextureSize(){
        return maxTextureSize;
    }

}
