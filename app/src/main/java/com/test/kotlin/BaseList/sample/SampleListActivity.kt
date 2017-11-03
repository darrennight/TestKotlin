package com.test.kotlin.BaseList.sample

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kotlin.BaseList.api.Api
import com.test.kotlin.BaseList.base.BaseListActivity
import com.test.kotlin.BaseList.model.BaseModel
import com.test.kotlin.BaseList.model.Benefit
import com.test.kotlin.BaseList.widget.BaseViewHolder
import com.test.kotlin.R
import kotlinx.android.synthetic.main.activity_samplelist_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Created by zenghao on 2017/8/21.
 */
class SampleListActivity :BaseListActivity<Benefit>(){

    override fun getLayoutManager(): RecyclerView.LayoutManager {

        val random = Random().nextInt(2)
        if(random == 0){
            return GridLayoutManager(this@SampleListActivity,2)
        }else{
            return super.getLayoutManager()
        }


    }

    override fun getItemType(position: Int): Int {
        return super.getItemType(position)
    }

    override fun onBaseCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(this).inflate(R.layout.activity_samplelist_item,parent,false)
        return SampleViewHolder(view)
    }



    override fun requestData() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(Api::class.java)
        val call = api.defaultBenefits(30,1)
        call.enqueue(object :Callback<BaseModel<ArrayList<Benefit>>>{
            override fun onResponse(call: Call<BaseModel<ArrayList<Benefit>>>?, response: Response<BaseModel<ArrayList<Benefit>>>?) {

                mDataList.addAll(response?.body()?.results!!)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<BaseModel<ArrayList<Benefit>>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }



    inner class SampleViewHolder(itemView: View) :BaseViewHolder(itemView){
        override fun onBindViewHolder(position: Int) {
            itemView.tv_sample_list.text = "hahahah$position"

        }
    }
}