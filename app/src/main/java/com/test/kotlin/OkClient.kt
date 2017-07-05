package com.test.kotlin

import okhttp3.OkHttpClient

/**
 * 此种也是单例模式
 * 单例OkHttpClient
 * Created by zenghao on 2017/6/5.
 */

object OkClient{
    private val client = OkHttpClient()
    fun instance() = client
}