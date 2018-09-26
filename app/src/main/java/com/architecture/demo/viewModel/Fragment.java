package com.architecture.demo.viewModel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.architecture.demo.R;
import com.architecture.demo.util.CONSTANT;

import java.util.Map;
import java.util.Set;

public class Fragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private MViewModel mMViewModel;
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    private String des;

    public static Fragment getInstance() {
        return new Fragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(CONSTANT.TAG_VIEW_MODEL, "Fragment_onCreate:");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        textView = view.findViewById(R.id.text);
        textView1 = view.findViewById(R.id.text1);
        textView2 = view.findViewById(R.id.text2);
        textView3 = view.findViewById(R.id.text3);
        textView.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setText(des);
        Log.i(CONSTANT.TAG_VIEW_MODEL, "Fragment_onCreateView_des:" + des);
        initViewModel();
        return view;
    }

    private void initViewModel() {
        if (getActivity() == null) return;
        mMViewModel = ViewModelProviders.of(getActivity()).get(MViewModel.class);
        Log.i(CONSTANT.TAG_VIEW_MODEL, "Fragment_mMViewModel:" + mMViewModel.toString());
        mMViewModel.getLanguage().observe(this, new Observer<Map<String, String>>() {
            @Override
            public void onChanged(@Nullable Map<String, String> map) {
                if (map == null) return;
                Set<Map.Entry<String, String>> entries = map.entrySet();
                int i = 0;
                for (Map.Entry<String, String> entry : entries) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (i == 0) {
                        textView.setText(key);
                        textView.setTag(value);
                    } else if (i == 1) {
                        textView1.setText(key);
                        textView1.setTag(value);
                    } else if (i == 2) {
                        textView2.setText(key);
                        textView2.setTag(value);
                    }
                    i++;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String tag = "";
        textView.setSelected(false);
        textView1.setSelected(false);
        textView2.setSelected(false);
        if (v == textView) {
            tag = (String) textView.getTag();
            textView.setSelected(true);
        } else if (v == textView1) {
            tag = (String) textView1.getTag();
            textView1.setSelected(true);
        } else if (v == textView2) {
            tag = (String) textView2.getTag();
            textView2.setSelected(true);
        }
        mMViewModel.getLanguageDes().setValue(tag);
        des = tag;
        textView3.setText(des);
        Log.i(CONSTANT.TAG_VIEW_MODEL, "Fragment_onClick:des:" + des);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(CONSTANT.TAG_VIEW_MODEL, "Fragment_onConfigurationChanged:");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(CONSTANT.TAG_VIEW_MODEL, "Fragment_onDestroy");
    }
}
