package com.architecture.demo.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.architecture.demo.util.CONSTANT;

import java.util.Map;

public class MViewModel extends AndroidViewModel {
    private MutableLiveData<Map<String, String>> mLanguage;
    private MutableLiveData<String> mLanguageDes;

    public MViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Map<String, String>> getLanguage() {
        if (mLanguage == null) {
            mLanguage = new MutableLiveData<>();
        }
        return mLanguage;
    }

    public MutableLiveData<String> getLanguageDes() {
        if (mLanguageDes == null) {
            mLanguageDes = new MutableLiveData<>();
        }
        return mLanguageDes;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(CONSTANT.TAG_VIEW_MODEL, "PagingViewModel.onCleared");
    }
}
