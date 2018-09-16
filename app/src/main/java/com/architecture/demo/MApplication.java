package com.architecture.demo;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.architecture.demo.util.CONSTANT;

/**
 * Created by cym on 18-9-14.
 */
public class MApplication extends Application {
    public static Context mContext;

    @Override
    public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
        Log.i(CONSTANT.TAG, "MApplication_createPackageContext");
        return super.createPackageContext(packageName, flags);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Log.i(CONSTANT.TAG, "MApplication_onCreate");
    }
}
