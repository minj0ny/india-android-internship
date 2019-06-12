package com.example.user.newsapi.network.dto

import java.io.Serializable

data class GetResponse(
        var articles: List<Articles>,
        var status: String,
        var totalResult: Int
) : Serializable