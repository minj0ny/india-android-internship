package com.example.user.newsapi.callback_listener

interface TechSetUpCaller {
    fun setUpView()
    fun setMultiSelect(value: Boolean)
    fun initalize()
    fun setSelectedIdsList(seletedId_tech: MutableList<Int>)
}