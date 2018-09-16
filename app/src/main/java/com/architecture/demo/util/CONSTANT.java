package com.architecture.demo.util;

import android.os.Environment;

/**
 * Created by cym on 18-9-9.
 */
public class CONSTANT {
    public static String TAG = "arch_";
    public static String TAG_LIFECYCLE = TAG + "lifecycle";
    public static String TAG_LIVE_DATA = TAG + "liveData";
    public static String TAG_VIEW_MODEL = TAG + "viewModel";
    public static String TAG_ROOM = TAG + "room";
    public static String TAG_PAGING = TAG + "paging";
    public static String TAG_WORKER = TAG + "worker";
    public static String PATH = Environment.getExternalStorageDirectory().getPath() + "/appInfo.txt";
}
