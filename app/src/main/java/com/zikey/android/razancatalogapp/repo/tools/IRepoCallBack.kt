package com.zikey.android.razancatalogapp.repo.tools

import com.zikey.android.razancatalogapp.model.wrapper.ServerAnswer


interface IRepoCallBack<T : ServerAnswer> {

    fun onAnswer(answer: T)
    fun onError(error: Throwable?)

}