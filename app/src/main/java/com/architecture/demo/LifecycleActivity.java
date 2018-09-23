package com.architecture.demo;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.architecture.demo.lifecycle.MyObserver;
import com.architecture.demo.util.CONSTANT;
import com.architecture.demo.util.Utils;

public class LifecycleActivity extends AppCompatActivity {
    MyObserver myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(CONSTANT.TAG_LIFECYCLE, "LifecycleActivity_onCreate");
        setContentView(R.layout.activity_lifecycle);
        myObserver = new MyObserver(getLifecycle());
        getLifecycle().addObserver(myObserver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(CONSTANT.TAG_LIFECYCLE, "LifecycleActivity_onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(CONSTANT.TAG_LIFECYCLE, "LifecycleActivity_onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(CONSTANT.TAG_LIFECYCLE, "LifecycleActivity_onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(CONSTANT.TAG_LIFECYCLE, "LifecycleActivity_onDestroy");
    }

    public void getState(View view) {
        Lifecycle.State currentState = myObserver.getCurrentState();
        Utils.showToast(getApplicationContext(), "state_" + currentState);
        Log.i(CONSTANT.TAG_LIFECYCLE, "state_" + currentState);
    }
}
