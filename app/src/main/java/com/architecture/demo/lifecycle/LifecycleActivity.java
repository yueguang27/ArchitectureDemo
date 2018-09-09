package com.architecture.demo.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.architecture.demo.CONSTANT;
import com.architecture.demo.R;

public class LifecycleActivity extends AppCompatActivity {
    MyObserver myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        myObserver = new MyObserver(getLifecycle());
        getLifecycle().addObserver(myObserver);
    }

    public void getState(View view) {
        Lifecycle.State currentState = myObserver.getCurrentState();
        Log.i(CONSTANT.TAG_LIFECYCLE, "state_" + currentState);
    }
}
