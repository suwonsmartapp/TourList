package com.suwonsmartapp.tourlist.list;

/**
 * Created by sangmun on 2015-04-01.
 */
public class TourList {
    private int mListMainImg;
    private String mLIstMainTitle;
    private String mListMainDate;
    private String mListMainContent;
    private int mListSubImg1;
    private int mListSubImg2;
    private int mListSubImg3;
    private int mListSubImg4;
    private int mListSubImg5;

    public TourList(int mListMainImg, String mLIstMainTitle, String mListMainDate, String mListMainContent, int mListSubImg1, int mListSubImg2, int mListSubImg3, int mListSubImg4, int mListSubImg5) {
        this.mListMainImg = mListMainImg;
        this.mLIstMainTitle = mLIstMainTitle;
        this.mListMainDate = mListMainDate;
        this.mListMainContent = mListMainContent;
        this.mListSubImg1 = mListSubImg1;
        this.mListSubImg2 = mListSubImg2;
        this.mListSubImg3 = mListSubImg3;
        this.mListSubImg4 = mListSubImg4;
        this.mListSubImg5 = mListSubImg5;
    }

    public int getmListMainImg() {
        return mListMainImg;
    }

    public String getmLIstMainTitle() {
        return mLIstMainTitle;
    }

    public String getmListMainDate() {
        return mListMainDate;
    }

    public String getmListMainContent() {
        return mListMainContent;
    }

    public int getmListSubImg1() {
        return mListSubImg1;
    }

    public int getmListSubImg2() {
        return mListSubImg2;
    }

    public int getmListSubImg3() {
        return mListSubImg3;
    }

    public int getmListSubImg4() {
        return mListSubImg4;
    }

    public int getmListSubImg5() {
        return mListSubImg5;
    }
}
