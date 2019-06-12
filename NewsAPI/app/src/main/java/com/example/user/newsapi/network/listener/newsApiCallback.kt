package com.example.user.newsapi.network.listener

interface newsApiCallback<T> {
    fun onSuccess(receiveData: T)
    fun onError(t: Throwable)
    fun onFailure(code: Int)
}