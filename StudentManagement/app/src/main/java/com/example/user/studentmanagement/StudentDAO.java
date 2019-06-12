package com.example.user.studentmanagement;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StudentDAO {

    @Query("SELECT * FROM student ORDER BY stuMark DESC")
    List<Student> getAll();

    @Query("SELECT * FROM student WHERE stuName =:name")
    Student getStudent(String name);

    @Insert
    void insert(Student student);

    @Delete
    void delete(Student student);

    @Update
    void update(Student student);
}
