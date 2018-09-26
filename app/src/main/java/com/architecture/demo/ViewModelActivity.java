package com.architecture.demo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.architecture.demo.util.CONSTANT;
import com.architecture.demo.viewModel.Fragment;
import com.architecture.demo.viewModel.Fragment2;
import com.architecture.demo.viewModel.MViewModel;

import java.util.HashMap;
import java.util.Map;

public class ViewModelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(CONSTANT.TAG_VIEW_MODEL, "ViewModelActivity_onCreate:");
        setContentView(R.layout.activity_view_model);

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame1, Fragment.getInstance(), "frame1");
            fragmentTransaction.replace(R.id.frame2, Fragment2.getInstance(), "frame2");
            fragmentTransaction.commit();
        } else {
            android.support.v4.app.Fragment frame1 = getSupportFragmentManager().findFragmentByTag("frame1");
            android.support.v4.app.Fragment frame2 = getSupportFragmentManager().findFragmentByTag("frame2");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame1, frame1, "frame1");
            fragmentTransaction.replace(R.id.frame2, frame2, "frame2");
            fragmentTransaction.commit();
        }

        initViewModel();
    }

    private void initViewModel() {
        MViewModel mMViewModel = ViewModelProviders.of(this).get(MViewModel.class);
        Log.i(CONSTANT.TAG_VIEW_MODEL, "ViewModelActivity_mMViewModel:" + mMViewModel.toString());
        Map<String, String> map = new HashMap<>();
        map.put("Java", "Java good");
        map.put("Dart", "Dart good");
        map.put("Kotlin", "Kotlin good");
        mMViewModel.getLanguage().setValue(map);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(CONSTANT.TAG_VIEW_MODEL, "ViewModelActivity_onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(CONSTANT.TAG_VIEW_MODEL, "ViewModelActivity_onConfigurationChanged:");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(CONSTANT.TAG_VIEW_MODEL, "ViewModelActivity_onDestroy");
    }
}
