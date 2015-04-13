package com.suwonsmartapp.tourlist.database;

/**
 * Created by HYUN.KI.UNG on 2015-04-08.
 */
public class Info_CODEMT {
    public int _id;
    public String key;
    public String value;
    public String wdt;

    public Info_CODEMT() {
    }

    public Info_CODEMT(int _id, String key, String value, String wdt){
        this._id = _id;
        this.key = key;
        this.value = value;
        this.wdt = wdt;
    }
}
