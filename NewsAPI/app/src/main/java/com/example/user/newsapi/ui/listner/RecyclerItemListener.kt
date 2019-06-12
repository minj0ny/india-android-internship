package com.example.user.newsapi.ui.listner

interface RecyclerItemListener {
    fun onItemSelected(position: Int)
    fun onLongItemSelected(position: Int): Boolean
}