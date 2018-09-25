package com.architecture.demo;

import android.arch.lifecycle.Observer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.architecture.demo.liveData.DataFetch;
import com.architecture.demo.liveData.NetWorkLiveData;
import com.architecture.demo.util.CONSTANT;

import java.util.ArrayList;
import java.util.List;

public class LiveDataActivity extends AppCompatActivity {
    public static int NUM = 0;
    private TextView text;
    private TextView text1;
    private DataFetch mDataFetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        text = findViewById(R.id.text);
        text1 = findViewById(R.id.text1);
        NUM = 0;
        initData();
    }

    //Demo1
    private void initData() {
        mDataFetch = new DataFetch();
        mDataFetch.getCurrentName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                text.setText(s);
            }
        }); // 注册观察者，观察LiveData持有的数据变化并做出响应
        mDataFetch.getNameList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                StringBuilder tmp = new StringBuilder();
                if (strings != null) {
                    for (String item : strings) {
                        tmp.append(item).append(",");
                    }
                }
                Log.i(CONSTANT.TAG_LIVE_DATA, tmp.toString());
            }
        }); // 注册观察者，观察LiveData持有的数据变化并做出响应
    }

    public void changeName(View view) {
        mDataFetch.getCurrentName().setValue("name" + NUM);
        NUM++;
    }

    public void changeList(View view) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < NUM; i++) {
            list.add("list" + i);
        }
        mDataFetch.getNameList().setValue(list);
        NUM++;
    }

    //Demo2
    public void registerNetStatus(View view) {
        NetWorkLiveData.getInstance(getApplicationContext()).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (null == integer) {
                    Log.i(CONSTANT.TAG_LIVE_DATA, "onChanged:null");
                    return;
                }
                Log.i(CONSTANT.TAG_LIVE_DATA, "onChanged:" + integer);
                String s = "无网络";
                if (integer == ConnectivityManager.TYPE_MOBILE) {
                    s = "手机网络";
                } else if (integer == ConnectivityManager.TYPE_WIFI) {
                    s = "WIFI网络";
                } else if (integer == -1) {
                    s = "无网络";
                }
                text1.setText(String.format("网络状态：%s", s));
            }
        });
    }

    public void unregisterNetStatus(View view) {
        NetWorkLiveData.getInstance(getApplicationContext()).removeObservers(this);
    }
}
