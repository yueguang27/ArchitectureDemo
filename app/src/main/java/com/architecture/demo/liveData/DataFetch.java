package com.architecture.demo.liveData;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

/**
 * Created by cym on 18-9-9.
 */
public class DataFetch {
    private MutableLiveData<String> mCurrentName;
    private MutableLiveData<List<String>> mNameListData;

    public MutableLiveData<String> getCurrentName() {
        if (mCurrentName == null) {
            mCurrentName = new MutableLiveData<>();
        }
        return mCurrentName;
    }

    public MutableLiveData<List<String>> getNameList() {
        if (mNameListData == null) {
            mNameListData = new MutableLiveData<>();
        }
        return mNameListData;
    }
}
