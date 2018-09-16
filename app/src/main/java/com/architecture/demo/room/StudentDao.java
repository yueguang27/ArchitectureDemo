package com.architecture.demo.room;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM tb_student ORDER BY number ASC")
    List<StudentEntity> getAll();

    @Query("SELECT * FROM tb_student LIMIT :current,:count ")
    List<StudentEntity> getByLimit(int current, int count);

    @Query("SELECT COUNT(*) FROM tb_student")
    int getCount();

    @Query("SELECT Max(number) FROM tb_student")
    int getMaxNumber();

    @Insert
    void insert(StudentEntity... entities);

    @Delete
    void delete(StudentEntity entity);

    @Query("DELETE FROM tb_student")
    void deleteAll();

    @Update
    void update(StudentEntity entity);

    @Query("SELECT * FROM tb_student ORDER BY number DESC")
    DataSource.Factory<Integer, StudentEntity> loadStudent();
}