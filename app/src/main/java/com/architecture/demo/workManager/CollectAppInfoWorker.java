package com.architecture.demo.workManager;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.architecture.demo.util.CONSTANT;
import com.architecture.demo.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.work.Worker;

/**
 * Created by cym on 18-9-14.
 */
public class CollectAppInfoWorker extends Worker {
    @NonNull
    @Override
    public Result doWork() {
        Log.i(CONSTANT.TAG_WORKER, "CollectAppInfoWorker_doWork_start----");

        String name = getApplicationContext().getClass().getName();
        Log.i(CONSTANT.TAG_WORKER, "application:" + name + Utils.getThreadInfo());

        PackageManager packageManager = getApplicationContext().getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo info = installedPackages.get(i);
            String label = info.applicationInfo.loadLabel(packageManager).toString();
            String packageName = info.packageName;
            String versionName = info.versionName;
            int versionCode = info.versionCode;
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("label", label);
                jsonObject.put("packageName", packageName);
                jsonObject.put("versionName", versionName);
                jsonObject.put("versionCode", String.valueOf(versionCode));
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Utils.writePathContent(CONSTANT.PATH, jsonArray.toString());
        Log.i(CONSTANT.TAG_WORKER, "jsonArray:" + jsonArray.length() + "," + jsonArray.toString());
        Log.i(CONSTANT.TAG_WORKER, "CollectAppInfoWorker_doWork_end----");
        return Result.SUCCESS;
    }
}
