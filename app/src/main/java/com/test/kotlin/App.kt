package com.test.kotlin

import android.app.Application

/**
 * Created by zenghao on 2017/6/5.
 */
class   App : Application() {
    private val TAG = App::class.java.simpleName
    init {
        instance = this
    }
    companion object {
        lateinit var instance: App

    }
    override fun onCreate() {
        super.onCreate()
    }
}