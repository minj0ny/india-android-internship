package com.example.user.newsapi.callback_listener

interface BusinessSetUpCaller {
    fun setUpView()
    fun setMultiSelect(value: Boolean)
    fun initalize()
    fun setSelectedIdsList(seletedId_business: MutableList<Int>)
}