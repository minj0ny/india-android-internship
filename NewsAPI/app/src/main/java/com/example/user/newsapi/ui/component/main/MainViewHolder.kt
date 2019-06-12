package com.example.user.newsapi.ui.component.main

import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.user.newsapi.network.dto.Articles
import kotlinx.android.synthetic.main.news_item.view.*
import com.bumptech.glide.request.RequestOptions
import com.example.user.newsapi.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.user.newsapi.callback_listener.MethodCaller
import com.example.user.newsapi.db.Model.BookMarkModel
import com.example.user.newsapi.db.NewsApp


class MainViewHolder(itemView: View?, private val updateSelectedListener: MethodCaller) : RecyclerView.ViewHolder(itemView) {
    fun bind(position: Int, articles: Articles, check: Boolean) {
        itemView?.apply {
            if (check) {
                checkBox.visibility = View.VISIBLE

            }
            tv_title.text = articles.title
            if (!"Chosun.com".equals(articles.source.name)) {
                tv_description.text = articles.description
            } else {
                tv_description.text = ""
            }
            tv_caption.text = articles.source.name

            val options = RequestOptions().centerCrop()
                    .placeholder(R.drawable.place)
                    .error(R.drawable.place)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)

            Glide.with(this).load(articles.urlToImage).apply(options).into(news_image)

            val articleModel = NewsApp.database?.articleDao()?.findArticleById(articles.title!!)
            if (articleModel != null) {
                val bookMark = NewsApp.database?.articleDao()?.findBookMarkById(articleModel?.articleId!!)
                if (bookMark != null) {
                    btnBookmark.isChecked = true
                }
            }

            btnShare.setOnClickListener(View.OnClickListener {
                updateSelectedListener.shareArticle(articles.description)
            })

            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    updateSelectedListener.updateSelectedCount(position)
                } else {
                    updateSelectedListener.updateUnSelectedCount(position)
                }
            }

            btnBookmark?.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    //  Toast.makeText(context, "Add to bookmark.", Toast.LENGTH_SHORT).show()
                    val articleModel = NewsApp.database?.articleDao()?.findArticleById(articles.title!!)
                    val bookMark = BookMarkModel(articleModel?.articleId!!)

                    AsyncTask.execute {
                        NewsApp.database?.articleDao()?.insertBookMark(bookMark)
                    }

                } else {
                    val articleModel = NewsApp.database?.articleDao()?.findArticleById(articles.title!!)
                    val bookMark = NewsApp.database?.articleDao()?.findBookMarkById(articleModel?.articleId!!)

                    if (bookMark != null) {
                        //     Toast.makeText(context, "Removed from bookmark.", Toast.LENGTH_SHORT).show()
                        NewsApp.database?.articleDao()?.deleteBookMark(bookMark)
                        updateSelectedListener.updateView()
                    }
                }
            }
        }
    }
}