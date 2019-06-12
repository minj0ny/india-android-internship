package com.example.user.newsapi.callback_listener

interface NewsSetUpCaller {
    fun setUpView()
    fun setMultiSelect(value: Boolean)
    fun initalize()
    fun setSelectedIdsList(seletedId_news: MutableList<Int>)
}