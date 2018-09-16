package com.architecture.demo.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.text.TextUtils;

//指示数据表实体类
@Entity(tableName = "tb_student",//定义表名
        indices = @Index(value = {"class_id", "number"}),//定义索引
        foreignKeys = {@ForeignKey(entity = ClassEntity.class,
                parentColumns = "id",
                childColumns = "class_id")})//定义外键
public class StudentEntity {
    @PrimaryKey(autoGenerate = true) //定义主键
    public long id;
    @ColumnInfo(name = "name")//定义数据表中的字段名
    public String name;
    @ColumnInfo(name = "number")
    public int number;
    @ColumnInfo(name = "class_id")
    public String class_id;
    @Ignore//指示Room需要忽略的字段或方法
    public String ignoreText;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getIgnoreText() {
        return ignoreText;
    }

    public void setIgnoreText(String ignoreText) {
        this.ignoreText = ignoreText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity that = (StudentEntity) o;
        return id == that.id &&
                number == that.number &&
                TextUtils.equals(name, that.name) &&
                TextUtils.equals(class_id, that.class_id) &&
                TextUtils.equals(ignoreText, that.ignoreText);
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}