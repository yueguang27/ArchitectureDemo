package com.architecture.demo;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.architecture.demo.dataBinding.User;

public class DataBindingActivity extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_data_binding, null);
        setContentView(rootView);
        ViewDataBinding binding = DataBindingUtil.bind(rootView);
        User user = new User("Test", "User");
        binding.setVariable(BR.user, user);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
