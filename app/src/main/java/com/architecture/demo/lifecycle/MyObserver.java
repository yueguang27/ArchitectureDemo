package com.architecture.demo.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.architecture.demo.CONSTANT;

public class MyObserver implements LifecycleObserver {
    private final Lifecycle lifecycle;

    MyObserver(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
        Log.i(CONSTANT.TAG_LIFECYCLE, "onCreate");

        Lifecycle.State currentState = getCurrentState();
        Log.i(CONSTANT.TAG_LIFECYCLE, "state_" + currentState);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onStart() {
        Log.i(CONSTANT.TAG_LIFECYCLE, "onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void onResume() {
        Log.i(CONSTANT.TAG_LIFECYCLE, "onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void onPause() {
        Log.i(CONSTANT.TAG_LIFECYCLE, "onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void onStop() {
        Log.i(CONSTANT.TAG_LIFECYCLE, "onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        Log.i(CONSTANT.TAG_LIFECYCLE, "onDestroy");
    }

    public Lifecycle.State getCurrentState() {
        return lifecycle.getCurrentState();
    }
}
