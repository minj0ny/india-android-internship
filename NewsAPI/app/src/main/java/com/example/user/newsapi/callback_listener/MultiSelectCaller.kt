package com.example.user.newsapi.callback_listener

import com.example.user.newsapi.network.dto.Articles

interface MultiSelectCaller {
    fun onNewsSeletedItem(seletedId_news: MutableList<Int>, localArticles_news: List<Articles>, newsSetUpCaller: NewsSetUpCaller)
    fun onBusinessSeletedItem(seletedId_Business: MutableList<Int>, localArticles_Business: List<Articles>, businessSetUpCaller: BusinessSetUpCaller)
    fun onTechSeletedItem(seletedId_Tech: MutableList<Int>, localArticles_Tech: List<Articles>, techSetUpCaller: TechSetUpCaller)
    fun setNewsCaller(newsSetUpCaller: NewsSetUpCaller)
    fun setBusinessCaller(businessSetUpCaller: BusinessSetUpCaller)
    fun setTechCaller(techSetUpCaller: TechSetUpCaller)
    fun setActionBarState()
}