package ru.littlebrains.telegraph.api

interface ICallBackTApi<T> {

    fun onComplete(result: T)
    fun onException(e: RequestException)
}


