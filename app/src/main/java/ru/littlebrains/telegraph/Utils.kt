package ru.littlebrains.telegraph

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager

import java.util.concurrent.atomic.AtomicInteger

import trikita.log.Log

object Utils {
    private val STORAGE_NAME = "telegraphLB"


    fun getSharedPreferencesEditor(
            mContext: Context): SharedPreferences.Editor {
        return mContext
                .getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
                .edit()
    }

    fun getSharedPreferences(mContext: Context): SharedPreferences {
        return mContext
                .getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }

    fun isInternetOn(mContext: Context): Boolean {
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        if (netInfo != null && netInfo.isConnectedOrConnecting) {
            Log.d("", "Internet enable")
            return true
        }
        Log.d("", "Internet desable")
        return false
    }



    fun showKeyboard(mContext: Context) {
        (mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .toggleSoftInput(InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun dptopx(context: Context, dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        val px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        return px
    }

    fun pxtodp(context: Context, px: Int): Int {
        val r = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px.toFloat(), r.displayMetrics).toInt()
    }
}
