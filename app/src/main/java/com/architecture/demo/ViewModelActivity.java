package com.architecture.demo;

import android.arch.lifecycle.ViewModelProviders;
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
        setContentView(R.layout.activity_view_model);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame1, Fragment.getInstance());
        fragmentTransaction.add(R.id.frame2, Fragment2.getInstance());
        fragmentTransaction.commit();
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
}
