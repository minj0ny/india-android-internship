package com.example.user.newsapi.callback_listener

interface MethodCaller {
    fun updateSelectedCount(index: Int)
    fun updateUnSelectedCount(index: Int)
    fun updateView()
    fun updateFirstCheckBox(): MutableList<Int>
    fun shareArticle(description: String? = "")
}