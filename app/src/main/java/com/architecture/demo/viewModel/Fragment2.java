package com.architecture.demo.viewModel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.architecture.demo.util.CONSTANT;
import com.architecture.demo.R;

public class Fragment2 extends android.support.v4.app.Fragment {
    TextView textView;

    public static Fragment2 getInstance() {
        return new Fragment2();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        textView = view.findViewById(R.id.text);
        initViewModel();
        return view;
    }

    private void initViewModel() {
        if (getActivity() == null) return;
        MViewModel mMViewModel = ViewModelProviders.of(getActivity()).get(MViewModel.class);
        Log.i(CONSTANT.TAG_VIEW_MODEL, "Fragment2_mMViewModel:" + mMViewModel.toString());
        mMViewModel.getLanguageDes().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String des) {
                textView.setText(des);
            }
        });
    }
}
