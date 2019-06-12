package com.example.user.newsapi.db

import android.app.Application
import android.arch.persistence.room.Room

class NewsApp : Application() {

    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        NewsApp.database =  Room.databaseBuilder(this, AppDatabase::class.java, "news-db").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}