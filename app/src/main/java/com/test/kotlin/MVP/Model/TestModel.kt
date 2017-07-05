package com.test.kotlin.MVP.Model

import android.util.Log
import com.test.kotlin.MVP.presenter.TestPresenter
import com.test.kotlin.net.ApiService
import com.test.kotlin.net.IndexData
import com.test.kotlin.net.JsonResult
import com.test.kotlin.net.TestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by zenghao on 2017/6/7.
 */
class TestModel :BaseModel{

    //异步请求时不能直接返回数据要通过回调
    fun getIndexData(listener:TestPresenter.CallBackListener){
        var   data:IndexData? = null
        var api = ApiService.createService(TestApi::class.java)
        var call = api.getIndexDataBean()
        call.enqueue(object: Callback<JsonResult<IndexData>> {
            override fun onFailure(call: Call<JsonResult<IndexData>>?, t: Throwable?) {
                Log.e("====","onFailure")

            }

            override fun onResponse(call: Call<JsonResult<IndexData>>, response: Response<JsonResult<IndexData>>) {
                if(response.isSuccessful){
                     data = response.body()?.data
                    Log.e("====","okokok")
                    listener.onCall(data)
                }

            }
        })
    }



}