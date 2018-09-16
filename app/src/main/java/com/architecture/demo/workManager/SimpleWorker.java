package com.architecture.demo.workManager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.architecture.demo.util.CONSTANT;
import com.architecture.demo.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.impl.Extras;

/**
 * Created by cym on 18-9-14.
 */
public class SimpleWorker extends Worker {
    @Override
    protected void internalInit(@NonNull Context appContext, @NonNull UUID id, @NonNull Extras extras) {
        super.internalInit(appContext, id, extras);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i(CONSTANT.TAG_WORKER, "SimpleWorker_doWork_start----");
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("时间", Utils.getTimeFormat(System.currentTimeMillis(), "yyMMdd HH:mm:ss SSS") + "--SimpleWorker");
            Utils.writePathContent(CONSTANT.PATH, "\r" + jsonObject.toString());
            Log.i(CONSTANT.TAG_WORKER, "jsonObject:" + "," + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Data output = new Data.Builder().putString("data", "data_value").build();
        setOutputData(output);
        Log.i(CONSTANT.TAG_WORKER, "SimpleWorker_doWork_end----");
        return Result.SUCCESS;
    }
}
