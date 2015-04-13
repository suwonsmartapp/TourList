package com.suwonsmartapp.tourlist.database;

/**
 * Created by Administrator on 2015-04-09.
 */
public class Info_PHOTODT {

    public int _id;
    public int mid;
    public String fullUrl;
    public String wdt;

    public Info_PHOTODT() {
    }

    public Info_PHOTODT(int mid, String fullUrl){
        this.mid = mid;
        this.fullUrl = fullUrl;
    }

    public Info_PHOTODT(int _id, int mid, String fullUrl, String wdt){
        this._id = _id;
        this.mid = mid;
        this.fullUrl = fullUrl;
        this.wdt = wdt;
    }
}
