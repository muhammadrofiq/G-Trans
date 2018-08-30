package com.example.asus.skripsi;

import android.app.Application;
import android.content.Context;

/**
 * Created by root on 05/03/18.
 */

public class Contextku extends Application{
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
}
