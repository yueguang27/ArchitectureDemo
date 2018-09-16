package com.architecture.demo.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ClassDao {

    @Query("SELECT * FROM tb_class")
    List<ClassEntity> getAll();

    @Insert
    void insert(ClassEntity... entities);

}