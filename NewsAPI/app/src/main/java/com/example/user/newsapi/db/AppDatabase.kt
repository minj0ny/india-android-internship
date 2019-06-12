package com.example.user.newsapi.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.user.newsapi.db.Model.ArticleModel
import com.example.user.newsapi.db.Model.BookMarkModel

@Database(entities = arrayOf(ArticleModel::class, BookMarkModel::class), version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

}