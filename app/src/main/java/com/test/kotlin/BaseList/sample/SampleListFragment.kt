package com.test.kotlin.BaseList.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kotlin.BaseList.api.Api
import com.test.kotlin.BaseList.base.BaseListFragment
import com.test.kotlin.BaseList.model.BaseModel
import com.test.kotlin.BaseList.model.Benefit
import com.test.kotlin.BaseList.widget.BaseViewHolder
import com.test.kotlin.R
import kotlinx.android.synthetic.main.fragment_samplelist_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by zenghao on 2017/8/23.
 */
class SampleListFragment :BaseListFragment<Benefit>(){

    override fun requestData() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(Api::class.java)
        val call = api.defaultBenefits(20,1)
        call.enqueue(object : Callback<BaseModel<ArrayList<Benefit>>> {
            override fun onResponse(call: Call<BaseModel<ArrayList<Benefit>>>?, response: Response<BaseModel<ArrayList<Benefit>>>?) {

                mDataList.addAll(response?.body()?.results!!)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<BaseModel<ArrayList<Benefit>>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun OnBaseCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.fragment_samplelist_item,parent,false)
        return SampleFragmentViewHolder(view)
    }


    inner class SampleFragmentViewHolder(itemView: View):BaseViewHolder(itemView){
        override fun onBindViewHolder(position: Int) {
            itemView.fragment_tv_sample_list.text = "yesyes$position"
        }
    }
}