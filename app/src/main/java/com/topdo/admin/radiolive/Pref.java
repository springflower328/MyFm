package com.topdo.admin.radiolive;

import android.content.Context;
import android.content.SharedPreferences;


public class Pref {

    int PRIVATE_MODE = 0;// shared pref mode
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;
    public static final String PREF_NAME = "lvradio";

    public Pref(Context context) {
        mContext = context;
    }

    public void set(String key, boolean value) {
        editor = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void set(String key, String value) {
        editor = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void set(String key, int value) {
        editor = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return pref.getBoolean(key, false);
    }

    public String getString(String key) {
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return pref.getString(key, "");
    }

    public int getInt(String key) {
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return pref.getInt(key, 0);
    }


}
