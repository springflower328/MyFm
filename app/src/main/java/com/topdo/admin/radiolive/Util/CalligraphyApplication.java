package com.topdo.admin.radiolive.Util;

import android.app.Application;

import com.topdo.admin.radiolive.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by admin on 12-08-2017.
 */

public class CalligraphyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/lato_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
