package com.architecture.demo.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cym on 18-9-11.
 */
public class Utils {
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static String getThreadInfo() {
        long id = Thread.currentThread().getId();
        String name = Thread.currentThread().getName();
        return "---ThreadInfo---id:" + id + ",name:" + name;
    }

    @VisibleForTesting
    public static final List<String> Permissions = new ArrayList<String>() {{
        add(Manifest.permission.INTERNET);
        add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }};

    public static void requestPermissionsIfNecessary(Activity activity, List<String> permissions) {
        boolean hasPermissions = Utils.checkAllPermissions(activity, permissions);
        if (!hasPermissions) {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[0]), 101);
        }
    }

    private static boolean checkAllPermissions(Context context, List<String> permissions) {
        boolean hasPermissions = true;
        for (String permission : permissions) {
            hasPermissions &= ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return hasPermissions;
    }

    public static void writePathContent(String path, String content) {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                boolean mkdirs = true;
                if (!parentFile.exists()) {
                    mkdirs = parentFile.mkdirs();
                }
                if (mkdirs) {
                    boolean newFile = file.createNewFile();
                    if (!newFile) return;
                }
            }
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(content);
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean delete(String filePathName) {
        if (TextUtils.isEmpty(filePathName)) return false;
        File file = new File(filePathName);
        return file.isFile() && file.exists() && file.delete();
    }

    public static String getTimeFormat(long time, String format) {
        java.util.Date date = new java.util.Date(time);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static int getConnectedType(Context context) {
        int type = -1;
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    type = activeNetworkInfo.getType();
                }
            }
        }
        return type;
    }
}
