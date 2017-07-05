package com.test.kotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.test.kotlin.MVP.TestMvpActivity
import com.test.kotlin.bean.FanFouBean
import com.test.kotlin.bean.Person
import com.test.kotlin.net.ApiService
import com.test.kotlin.net.IndexData
import com.test.kotlin.net.JsonResult
import com.test.kotlin.net.TestApi
import com.test.kotlin.other.MainOtherActivity
import com.test.kotlin.permission.TestPermissionActivity
import com.test.kotlin.rx.RXTestActivity
import com.test.kotlin.sharePreference.GuidePreference
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * https://github.com/TonnyL/FanfouHandpick
 * https://github.com/wuapnjie/PoiShuhui-Kotlin
 * https://github.com/githubwing/GankClient-Kotlin
 * kotlin 控件不用findviewbyId 直接使用xmlID即可
 *https://github.com/ReactiveX/RxKotlin
 * https://github.com/Yalantis/JellyToolbar
 * https://github.com/Yalantis/SearchFilter
 *https://github.com/antoniolg/Bandhook-Kotlin
 *https://github.com/dbacinski/Design-Patterns-In-Kotlin
 * https://github.com/JetradarMobile/android-snowfall
 * https://github.com/kittinunf/Fuel
 * https://github.com/antoniolg/Kotlin-for-Android-Developers
 *
 * kotlin的android开发工具包
 * https://github.com/mcxiaoke/kotlin-koi
 *
 *https://github.com/550609334/Twobbble
 *https://github.com/vslimit/kotlindemo
 * https://github.com/LeeeYou/RsKotlin
 * https://github.com/ImangazalievM/Notelin
 * https://github.com/antoniolg/Bandhook-Kotlin
 * https://github.com/Yalantis/Multi-Selection
 * https://github.com/TonicArtos/SuperSLiM
 * https://github.com/mazurio/bodyweight-fitness-android
 *
 * https://github.com/enbandari/Kotlin-Tutorials
 */

class MainActivity : AppCompatActivity() {

    var list = ArrayList<FanFouBean>()
    var adapter: MyAdapter?=null
    val nameResList: ArrayList<Int> = arrayListOf(R.string.app_name, R.string.app_name, R.string.app_name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (i in 0..10){
            var fan = FanFouBean("",i.toString(),i.toString()+"haha" ,i.toString(),"")
            list.add(fan)

        }
        initViews()

        val nameList = nameResList.map(this::getString)

        toast("ajj")

        btn_mvp.setOnClickListener {
            startActivity(Intent(this@MainActivity,TestMvpActivity::class.java))
        }

        btn_permission.setOnClickListener {
            startActivity(Intent(this@MainActivity,TestPermissionActivity::class.java))
        }
        btn_rxbus.setOnClickListener {
            startActivity(Intent(this@MainActivity,RXTestActivity::class.java))
        }


        /*var save:Int by Preference(this@MainActivity,"test",0)
        if(save == 0){
            Log.e("===","save$save")
            save = 1
        }else{
            Log.e("===","save$save")
        }*/

        //清除
        /*var test = Preference<Int>(this@MainActivity)
        test.delete()*/



        var save:Int by PreferenceTest("test",0)
        if(save == 0){
            Log.e("===","save$save")
            save = 1
        }else{
            Log.e("===","save$save")
        }

        var test = PreferenceTest<Any>()
        test.delete("test")
//        test.delete("test")

    }



    fun initViews(){
        refresh.setOnRefreshListener {
            refresh.isRefreshing = false
            this.startActivity(Intent(this, MainOtherActivity::class.java))
        }

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.itemAnimator = DefaultItemAnimator()
        adapter = MyAdapter(this,list)
        rv_main.adapter = adapter

    }

    fun testApiRequest(){
        val testApi = ApiService.createService(TestApi::class.java)
        val call = testApi.getIndexData()

        call.enqueue(object: Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.isSuccessful){
                    val data =  response.body().toString()
                    Log.e("===",data);
                }else{
                    Log.e("====","errorerror")
                }
            }

            override fun onFailure(call: Call<Any>?, t: Throwable?) {
                Log.e("====","onFailure")

            }

        } )
    }

    fun testApiRequestBean(){
        var api = ApiService.createService(TestApi::class.java)
        var call = api.getIndexDataBean()
        call.enqueue(object: Callback<JsonResult<IndexData>>{
            override fun onFailure(call: Call<JsonResult<IndexData>>?, t: Throwable?) {
                Log.e("====","onFailure")
            }

            override fun onResponse(call: Call<JsonResult<IndexData>>, response: Response<JsonResult<IndexData>>) {
                if(response.isSuccessful){
                    var data = response.body()?.data
                    Log.e("====","okokok")
                }
            }
        })
    }

}
