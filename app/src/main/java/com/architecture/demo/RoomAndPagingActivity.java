package com.architecture.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.architecture.demo.paging.PagingFragment;
import com.architecture.demo.room.ClassDao;
import com.architecture.demo.room.ClassEntity;
import com.architecture.demo.room.DemoRoomDatabase;
import com.architecture.demo.room.StudentDao;
import com.architecture.demo.room.StudentEntity;

public class RoomAndPagingActivity extends AppCompatActivity implements View.OnClickListener {
    DemoRoomDatabase mRoomDatabase;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    public static int NUMBER;
    PagingFragment mPagingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        btn1 = findViewById(R.id.text1);
        btn2 = findViewById(R.id.text2);
        btn3 = findViewById(R.id.text3);
        btn4 = findViewById(R.id.text4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        mPagingFragment = PagingFragment.getInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_data, mPagingFragment);
        fragmentTransaction.commit();
        mRoomDatabase = DemoRoomDatabase.getInstance(getApplicationContext());
        NUMBER = mRoomDatabase.studentDao().getMaxNumber();
    }

    @Override
    public void onClick(View v) {
        if (v == btn1) {
            ClassDao classDao = mRoomDatabase.classDao();
            ClassEntity classEntity = new ClassEntity();
            classEntity.setName("三年二班");
            classDao.insert(classEntity);
        } else if (v == btn2) {
            add10();
        } else if (v == btn3) {
            StudentDao studentDao = mRoomDatabase.studentDao();
            StudentEntity student = getStudentEntity();
            studentDao.insert(student);
        } else if (v == btn4) {
            StudentDao studentDao = mRoomDatabase.studentDao();
            studentDao.deleteAll();
            NUMBER = 0;
        }
        if (mPagingFragment != null) {
            mPagingFragment.refreshData();
        }
    }

    private void add10() {
        for (int i = 0; i < 10; i++) {
            StudentDao studentDao = mRoomDatabase.studentDao();
            StudentEntity student = getStudentEntity();
            studentDao.insert(student);
        }
    }

    @NonNull
    private StudentEntity getStudentEntity() {
        NUMBER++;
        StudentEntity student = new StudentEntity();
        student.setName("小闹" + NUMBER);
        student.setNumber(NUMBER);
        student.setClass_id("1");
        return student;
    }
}
