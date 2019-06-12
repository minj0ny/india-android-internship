package com.example.user.newsapi.db.Model

import android.arch.persistence.room.*
import java.io.Serializable

@Entity(tableName = "book",
        foreignKeys = arrayOf(ForeignKey(entity = ArticleModel::class,
                parentColumns = arrayOf("articleId"),
                childColumns = arrayOf("articleId"),
                onDelete = ForeignKey.CASCADE)),
        indices = arrayOf(Index(value = "articleId", unique = true)))
data class BookMarkModel(@ColumnInfo(name = "articleId") var articleId: Long?) : Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
