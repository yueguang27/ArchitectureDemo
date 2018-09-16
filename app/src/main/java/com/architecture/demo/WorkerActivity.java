package com.architecture.demo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.architecture.demo.util.CONSTANT;
import com.architecture.demo.util.Utils;
import com.architecture.demo.workManager.CollectAppInfoWorker;
import com.architecture.demo.workManager.SimpleWorker;
import com.architecture.demo.workManager.SimpleWorker2;
import com.architecture.demo.workManager.SimpleWorker3;
import com.architecture.demo.workManager.SimpleWorker4;
import com.architecture.demo.workManager.SimpleWorker5;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.State;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

public class WorkerActivity extends AppCompatActivity {
    public static String WORKER_TAG = "worker_tag";
    UUID mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
    }

    public void deleteFile(View view) {
        boolean delete = Utils.delete(CONSTANT.PATH);
        if (delete) Utils.showToast(getApplicationContext(), "删除成功！");
    }

    public void startOneTimeWorkRequest(View view) {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(CollectAppInfoWorker.class).build();
        WorkManager.getInstance().enqueue(workRequest);
    }

    public void startPeriodicWorkRequest(View view) {
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest
                .Builder(SimpleWorker.class, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                .addTag(WORKER_TAG)
                .build();
        mId = workRequest.getId();
        WorkManager.getInstance().enqueue(workRequest);

        LiveData<List<WorkStatus>> statusesByTag = WorkManager.getInstance().getStatusesByTag(WORKER_TAG);
        statusesByTag.observe(this, new Observer<List<WorkStatus>>() {
            @Override
            public void onChanged(@Nullable List<WorkStatus> workStatuses) {
                Log.i(CONSTANT.TAG_WORKER, "onChanged");
                if (workStatuses == null) return;
                for (WorkStatus workStatus : workStatuses) {
                    UUID id = workStatus.getId();
                    Log.i(CONSTANT.TAG_WORKER, "onChanged_id:" + id);
                    if (mId != id) break;
                    Data outputData = workStatus.getOutputData();
                    String data = outputData.getString("data");
                    Log.i(CONSTANT.TAG_WORKER, "data：" + data);
                    State state = workStatus.getState();
                    boolean finished = state.isFinished();
                    Log.i(CONSTANT.TAG_WORKER, "finished：" + finished);
                }
            }
        });
    }

    public void cancelPeriodicWorkRequest(View view) {
        WorkManager.getInstance().cancelAllWorkByTag(WORKER_TAG);
        /*//可以通过id或者tag删除worker
        if (mId != null) {
            WorkManager.getInstance().cancelWorkById(mId);
            mId = null;
        }*/
    }

    public void startContinuation(View view) {
        OneTimeWorkRequest workRequestA = new OneTimeWorkRequest.Builder(SimpleWorker.class).build();
        OneTimeWorkRequest workRequestB = new OneTimeWorkRequest.Builder(SimpleWorker2.class).build();
        OneTimeWorkRequest workRequestC = new OneTimeWorkRequest.Builder(SimpleWorker3.class).build();
        WorkManager.getInstance()
                .beginWith(workRequestA)
                .then(workRequestB)
                .then(workRequestC)
                .enqueue();
    }

    public void startMultiContinuation(View view) {
        OneTimeWorkRequest workRequestA = new OneTimeWorkRequest.Builder(SimpleWorker.class).build();
        OneTimeWorkRequest workRequestB = new OneTimeWorkRequest.Builder(SimpleWorker2.class).build();
        OneTimeWorkRequest workRequestC = new OneTimeWorkRequest.Builder(SimpleWorker3.class).build();
        OneTimeWorkRequest workRequestD = new OneTimeWorkRequest.Builder(SimpleWorker4.class).build();
        OneTimeWorkRequest workRequestE = new OneTimeWorkRequest.Builder(SimpleWorker5.class).build();

        WorkContinuation chain1 = WorkManager.getInstance()
                .beginWith(workRequestA)
                .then(workRequestB);

        WorkContinuation chain2 = WorkManager.getInstance()
                .beginWith(workRequestC)
                .then(workRequestD);

        WorkContinuation chain3 = WorkContinuation
                .combine(chain1, chain2)
                .then(workRequestE);

        chain3.enqueue();
    }

    public void startConstraints(View view) {
        int type = Utils.getConnectedType(getApplicationContext());
        Log.i(CONSTANT.TAG_WORKER, "NetworkInfo：" + type);

        Constraints.Builder constraintsBuilder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)//指定任务执行时的网络状态,网络链接时候运行,默认NOT_REQUIRED
                .setRequiresBatteryNotLow(false)//指定设备电池电量低于阀值时是否启动任务,默认false 执行任务时电池电量不能偏低
                .setRequiresStorageNotLow(false)//指定设备储存空间低于阀值时是否启动任务,默认false  设备储存空间足够时才能执行
                .setRequiresCharging(false);//指定设备在充电时是否启动任务,默认false  在设备充电时才能执行任务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            constraintsBuilder.setRequiresDeviceIdle(false);//指明设备为空闲时是否启动任务。 设备空闲时才能执行
        }

        Constraints constraints = constraintsBuilder.build();
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(SimpleWorker.class).setConstraints(constraints).build();
        WorkManager.getInstance().enqueue(workRequest);
    }
}
