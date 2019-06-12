package com.example.user.newsapi.network.dto

import java.io.Serializable

data class Articles(
        var author: String?,
        var title: String?,
        var description: String?,
        var url: String?,
        var urlToImage: String?,
        var publishedAt: String?,
        var source: Source
) : Serializable

