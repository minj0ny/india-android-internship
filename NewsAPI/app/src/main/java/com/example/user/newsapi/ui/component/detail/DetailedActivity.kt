package com.example.user.newsapi.ui.component.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.user.newsapi.R
import com.example.user.newsapi.network.dto.Articles
import kotlinx.android.synthetic.main.activity_detailed.*


class DetailedActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        val intent = intent
        val article = intent.getSerializableExtra("article") as Articles

        source_Title.text = article.title
        if (!"Chosun.com".equals(article.source.name)) {
            source_description.text = article.description
        } else {
            source_description.text = ""
        }

        source_date.text = article.publishedAt

        val options = RequestOptions()
                .placeholder(R.drawable.place)
                .error(R.drawable.place)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)

        Glide.with(this).load(article.urlToImage).apply(options).into(source_image)

    }
}
