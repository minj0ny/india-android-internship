package com.example.user.studentmanagement;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "student")
public class Student implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "stuName")
    private String stuName;

    @ColumnInfo(name = "stuClass")
    private String stuClass;

    @ColumnInfo(name = "stuMark")
    private int stuMark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public int getStuMark() {
        return stuMark;
    }

    public void setStuMark(int stuMark) {
        this.stuMark = stuMark;
    }
}
