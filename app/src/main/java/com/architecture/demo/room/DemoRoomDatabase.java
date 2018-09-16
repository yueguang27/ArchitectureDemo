package com.architecture.demo.room;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.architecture.demo.util.CONSTANT;

@Database(entities = {ClassEntity.class, StudentEntity.class}, version = 1)
public abstract class DemoRoomDatabase extends RoomDatabase {
    private static DemoRoomDatabase instance = null;

    public static DemoRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DemoRoomDatabase.class, "room_database").addCallback(new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Log.i(CONSTANT.TAG_ROOM, "onCreate:" + db.getPath());
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    Log.i(CONSTANT.TAG_ROOM, "onOpen:" + db.getPath());
                }
            }).allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract StudentDao studentDao();

    public abstract ClassDao classDao();
}