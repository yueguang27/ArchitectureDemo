package com.architecture.demo.workManager;

import android.support.annotation.NonNull;
import android.util.Log;

import com.architecture.demo.util.CONSTANT;
import com.architecture.demo.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.work.Worker;

/**
 * Created by cym on 18-9-14.
 */
public class SimpleWorker4 extends Worker {
    @NonNull
    @Override
    public Result doWork() {
        Log.i(CONSTANT.TAG_WORKER, "SimpleWorker4_doWork_start----");

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("时间", Utils.getTimeFormat(System.currentTimeMillis(), "yyMMdd HH:mm:ss SSS") + "--SimpleWorker4");
            Utils.writePathContent(CONSTANT.PATH, "\r" + jsonObject.toString());
            Log.i(CONSTANT.TAG_WORKER, "jsonObject:" + "," + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(CONSTANT.TAG_WORKER, "SimpleWorker4_doWork_end----");
        return Result.SUCCESS;
    }
}
