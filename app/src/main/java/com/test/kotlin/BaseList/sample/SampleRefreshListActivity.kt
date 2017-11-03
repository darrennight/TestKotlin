package com.test.kotlin.BaseList.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kotlin.BaseList.api.Api
import com.test.kotlin.BaseList.base.BaseRefreshListActivity
import com.test.kotlin.BaseList.model.BaseModel
import com.test.kotlin.BaseList.model.Benefit
import com.test.kotlin.BaseList.widget.BaseViewHolder
import com.test.kotlin.R
import com.test.kotlin.toast
import kotlinx.android.synthetic.main.activity_base_refresh_list.*
import kotlinx.android.synthetic.main.activity_samplelist_item.view.*
import kotlinx.android.synthetic.main.state_test_empty.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by zenghao on 2017/8/24.
 */
class SampleRefreshListActivity :BaseRefreshListActivity<Benefit,ArrayList<Benefit>>(){

    override fun initStateLayout() {
        //TODO 设置stateLayout
        setEmpty(R.layout.state_test_empty)
        setReTryListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                toast("tattatatatatatt")
            }
        })
    }

    override fun requestData() {
       /*val retrofit = Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(Api::class.java)
        val call = api.defaultBenefits(30,1)
        call.enqueue(object : Callback<BaseModel<ArrayList<Benefit>>> {
            override fun onResponse(call: Call<BaseModel<ArrayList<Benefit>>>?, response: Response<BaseModel<ArrayList<Benefit>>>?) {

                mDataList.addAll(response?.body()?.results!!)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<BaseModel<ArrayList<Benefit>>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })*/
    }

    override fun getRequestCall(): Call<BaseModel<ArrayList<Benefit>>>? {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(Api::class.java)
        val call = api.defaultBenefits(30,1)
        return call
    }

    override fun getRefreshCall(): Call<BaseModel<ArrayList<Benefit>>>? {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(Api::class.java)
        val call = api.defaultBenefits(30,1)
        return call
    }

    override fun getLoadCall(): Call<BaseModel<ArrayList<Benefit>>>? {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(Api::class.java)
        val call = api.defaultBenefits(30,2)
        return call
    }



    /*override fun onDataResponse(action: Int, response: BaseModel<ArrayList<Benefit>>?) {
        base_sv_refresh.onFinishFreshAndLoad()
        when(action){
            request ->{
                mDataList.addAll(response?.results!!)
            }
            refresh ->{
                mDataList.clear()
                mDataList.addAll(response?.results!!)
            }
            load ->{
                mDataList.addAll(response?.results!!)
            }
            else ->{

            }

        }

        adapter.notifyDataSetChanged()
    }*/

    override fun onDataResponse(action: Int, response: BaseModel<ArrayList<Benefit>>?) {
        //TODO 可以对数据进行处理 再传递
        onDataResult(action,response?.results)
    }

    override fun onDataFailure(call: Call<BaseModel<ArrayList<Benefit>>>?, t: Throwable?) {
    }

    override fun onBaseCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(this).inflate(R.layout.activity_samplelist_item,parent,false)
        return SampleViewHolder(view)
    }

    inner class SampleViewHolder(itemView: View) :BaseViewHolder(itemView){
        override fun onBindViewHolder(position: Int) {
            itemView.tv_sample_list.text = "hahahah$position"

        }
    }
}