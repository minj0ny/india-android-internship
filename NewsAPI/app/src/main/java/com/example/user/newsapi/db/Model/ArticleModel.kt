package com.example.user.newsapi.db.Model

import android.arch.persistence.room.*
import java.io.Serializable

@Entity(tableName = "article")
data class ArticleModel(@ColumnInfo(name = "author") var author: String? = "", @ColumnInfo(name = "title") var title: String? = "", @ColumnInfo(name = "url") var url: String? = "", @ColumnInfo(name = "description") var description: String? = "", @ColumnInfo(name = "urlToImage") var urlToImage: String? = "", @ColumnInfo(name = "publishedAt") var publishedAt: String? = "", @ColumnInfo(name = "id") var id: String? = "", @ColumnInfo(name = "name") var name: String? = "") : Serializable {
    @ColumnInfo(name = "articleId")
    @PrimaryKey(autoGenerate = true)
    var articleId: Long = 0
}
