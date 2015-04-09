package com.suwonsmartapp.tourlist.database;

/**
 * Created by Administrator on 2015-04-09.
 */
public class Info_PHOTODT {

    public int _id;
    public int mid;
    public String FullUrl;
    public String wdt;

    public Info_PHOTODT() {
    }

    public Info_PHOTODT(int _id, int mid, String FullUrl, String wdt){
        this._id = _id;
        this.mid = mid;
        this.FullUrl = FullUrl;
        this.wdt = wdt;
    }
}
