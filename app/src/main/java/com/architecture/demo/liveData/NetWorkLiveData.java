package com.architecture.demo.liveData;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.architecture.demo.util.CONSTANT;
import com.architecture.demo.util.Utils;

import java.lang.ref.WeakReference;

public class NetWorkLiveData extends LiveData<Integer> {
    private WeakReference<Context> mContextWeakReference;
    private static NetWorkLiveData mData;

    private NetWorkLiveData(Context context) {
        mContextWeakReference = new WeakReference<>(context);
    }

    public static NetWorkLiveData getInstance(Context context) {
        if (mData == null) {
            mData = new NetWorkLiveData(context);
        }
        return mData;
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<Integer> observer) {
        super.observe(owner, observer);
        Log.i(CONSTANT.TAG_LIVE_DATA, "observe");
    }

    @Override
    protected void onActive() {//此方法是当处于激活状态的observer个数从0到1时，该方法会被调用。
        super.onActive();
        Log.i(CONSTANT.TAG_LIVE_DATA, "onActive");
        registerReceiver();
    }

    @Override
    protected void onInactive() {//此方法是当处于激活状态的observer个数从1变为0时，该方法会被调用。
        super.onInactive();
        Log.i(CONSTANT.TAG_LIVE_DATA, "onInactive");
        unregisterReceiver();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mContextWeakReference.get().registerReceiver(mReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        mContextWeakReference.get().unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                int type = Utils.getConnectedType(context);
                Log.i(CONSTANT.TAG_LIVE_DATA, "CONNECTIVITY_ACTION：" + type);
                mData.setValue(type);
            }
        }
    };
}
