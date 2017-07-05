package com.test.kotlin.net

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by zenghao on 2017/6/6.
 */
interface TestApi {
    @GET("/v2/index")
    fun getIndexData(): Call<Any>

    @GET("/v2/index")
    fun getIndexDataBean(): Call<JsonResult<IndexData>>


}