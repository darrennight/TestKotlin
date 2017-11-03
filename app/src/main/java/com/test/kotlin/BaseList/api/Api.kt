package com.test.kotlin.BaseList.api

import com.test.kotlin.BaseList.model.BaseModel
import com.test.kotlin.BaseList.model.Benefit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by zenghao on 2017/8/22.
 */
interface Api {

    @GET("api/data/福利/{pageCount}/{pageIndex}")
    fun defaultBenefits(@Path("pageCount") pageCount:Int,
                        @Path("pageIndex") pageIndex:Int ):Call<BaseModel<ArrayList<Benefit>>>
}