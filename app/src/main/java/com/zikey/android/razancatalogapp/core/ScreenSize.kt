package com.zikey.android.razancatalogapp.core

object ScreenSize {
    @JvmStatic
    var width = 0
        private set

    var height = 0
        private set

    fun setScreenSize(w: Int, h: Int) {

        width = w
        height = h
    }

}