package com.architecture.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.architecture.demo.util.CONSTANT;
import com.architecture.demo.util.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.requestPermissionsIfNecessary(this, Utils.Permissions);
        Log.i(CONSTANT.TAG, "main:" + Utils.getThreadInfo());
        setContentView(R.layout.activity_main);

    }

    public void startActivity(Class c) {
        Intent intent = new Intent(getBaseContext(), c);
        startActivity(intent);
    }

    public void dataBinding(View view) {
        startActivity(DataBindingActivity.class);
    }

    public void lifecycle(View view) {
        startActivity(LifecycleActivity.class);
    }

    public void liveData(View view) {
        startActivity(LiveDataActivity.class);
    }

    public void viewModel(View view) {
        startActivity(ViewModelActivity.class);
    }

    public void room(View view) {
        startActivity(RoomAndPagingActivity.class);
    }

    public void paging(View view) {
        startActivity(RoomAndPagingActivity.class);
    }

    public void worker(View view) {
        startActivity(WorkerActivity.class);
    }


}
