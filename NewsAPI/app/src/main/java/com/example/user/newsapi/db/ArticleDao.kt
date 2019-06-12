package com.example.user.newsapi.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.example.user.newsapi.db.Model.ArticleModel
import com.example.user.newsapi.db.Model.BookMarkModel

@Dao
interface ArticleDao {

    @Query("select * from article")
    fun getAllArticles(): List<ArticleModel>

    @Query("select * from article where title = :articleTitle")
    fun findArticleById(articleTitle: String): ArticleModel

    @Query("select * from article where articleId = :articleId")
    fun findArticleByarticleID(articleId: Long): ArticleModel

    @Query("select * from book order by id desc")
    fun getAllBookMarks(): List<BookMarkModel>

    @Query("select * from book where articleId = :articleId")
    fun findBookMarkById(articleId: Long): BookMarkModel

    @Insert(onConflict = REPLACE)
    fun insertArticle(article: List<ArticleModel>)

    @Insert(onConflict = REPLACE)
    fun insertBookMark(bookmark: BookMarkModel)

    @Update(onConflict = REPLACE)
    fun updateArticle(article: ArticleModel)

    @Delete
    fun deleteBookMark(bookmark: BookMarkModel)

    @Query("delete from article")
    fun deleteAllArticles()

}