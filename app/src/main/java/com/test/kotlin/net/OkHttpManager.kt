package com.test.kotlin.net

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Created by zenghao on 2017/6/6.
 */
class OkHttpManager private constructor() {

    var okHttpClient: OkHttpClient? = null

    init {
        val builder = OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
//                    .cookieJar()
//                    .addInterceptor()
        //添加日志拦截器
        okHttpClient = builder.build()

    }

    companion object {
        fun getInstance(): OkHttpManager {
            return Holder.instance
        }
    }

    private object Holder {
        val instance = OkHttpManager()
    }

}