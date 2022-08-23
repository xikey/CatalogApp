package com.razanpardazesh.com.resturantapp.tools

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class FontChanger {

    private val mainFontAddress = "fonts/Estedad-Regular.ttf"
    private val titleFontAddress = "fonts/Estedad-SemiBold.ttf"
    private val yekanFontAddress = "fonts/IRYekan.ttf"
    private val numberFontAddress = "fonts/Dana-FaNum-Regular.ttf"

    private var mainFont: Typeface? = null
    private var titleFont: Typeface? = null
    private var yekanFont: Typeface? = null
    private var numberFont: Typeface? = null


    fun getMainFont(context: Context): Typeface? {
        if (mainFont == null) {
            mainFont = Typeface.createFromAsset(context.assets, mainFontAddress)
        }
        return mainFont
    }

    fun getTitleFont(context: Context): Typeface? {
        if (titleFont == null) {
            titleFont = Typeface.createFromAsset(context.assets, titleFontAddress)
        }
        return titleFont
    }

    fun getYekanFont(context: Context): Typeface? {
        if (yekanFont == null) {
            yekanFont = Typeface.createFromAsset(context.assets, yekanFontAddress)
        }
        return yekanFont
    }

    fun getNumberFont(context: Context): Typeface? {
        if (numberFont == null) {
            numberFont = Typeface.createFromAsset(context.assets, numberFontAddress)
        }
        return numberFont
    }

    fun applyMainFont(view: View?) {
        if (view?.context == null) return
        if (view is TextView) {
            view.typeface = getMainFont(view.getContext())
            return
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child: View = view.getChildAt(i)
                applyMainFont(child)
            }
        }
    }



    fun applyTitleFont(view: View?) {
        if (view?.context == null) return
        if (view is TextView) {
            view.typeface = getTitleFont(view.getContext())
            return
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child: View = view.getChildAt(i)
                applyTitleFont(child)
            }
        }
    }





    fun applyYekanFont(view: View?) {
        if (view?.context == null) return
        if (view is TextView) {
            view.typeface = getYekanFont(view.getContext())
            return
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child: View = view.getChildAt(i)
                applyYekanFont(child)
            }
        }
    }


 fun applyNumberFont(view: View?) {
        if (view?.context == null) return
        if (view is TextView) {
            view.typeface = getNumberFont(view.getContext())
            return
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child: View = view.getChildAt(i)
                applyNumberFont(child)
            }
        }
    }



}