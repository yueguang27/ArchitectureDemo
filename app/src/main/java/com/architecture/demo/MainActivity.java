package com.architecture.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.architecture.demo.lifecycle.LifecycleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void lifecycle(View view) {
        startActivity(LifecycleActivity.class);
    }

    public void startActivity(Class c) {
        Intent intent = new Intent(getBaseContext(), c);
        startActivity(intent);
    }
}
