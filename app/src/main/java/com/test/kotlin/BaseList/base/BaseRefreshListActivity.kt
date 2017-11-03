package com.test.kotlin.BaseList.base

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.View
import android.view.ViewGroup
import com.liaoinstan.springview.container.DefaultFooter
import com.liaoinstan.springview.container.DefaultHeader
import com.liaoinstan.springview.widget.SpringView
import com.test.kotlin.BaseList.model.BaseModel
import com.test.kotlin.BaseList.widget.BaseListAdapter
import com.test.kotlin.BaseList.widget.BaseViewHolder
import com.test.kotlin.R
import kotlinx.android.synthetic.main.activity_base_refresh_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by zenghao on 2017/8/24.
 */
abstract class BaseRefreshListActivity<T, K> : BaseActivity() {

    protected lateinit var adapter: BaseListAdapter
    protected var mDataList: ArrayList<T> = ArrayList()

    protected val request: Int = 0
    protected val refresh: Int = 1
    protected val load: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_refresh_list)
        initUI()
        initStateLayout()
        val call = getRequestCall()
        call?.let {
            call.enqueue(object : Callback<BaseModel<K>> {
                override fun onFailure(call: Call<BaseModel<K>>?, t: Throwable?) {
                    onDataFailure(call, t)
                    layout_state.showError()
                }

                override fun onResponse(call: Call<BaseModel<K>>?, response: Response<BaseModel<K>>?) {
                    handleResponse(request, response)
                }
            })
        }
    }

    private fun initUI() {
        rv_base_list_refresh.layoutManager = getLayoutManager()
        rv_base_list_refresh.itemAnimator = getItemAnimator()
        adapter = ListAdapter()
        rv_base_list_refresh.adapter = adapter
        base_sv_refresh.setListener(object : SpringView.OnFreshListener {
            override fun onLoadmore() {
                val call = getLoadCall()
                call?.let {
                    call.enqueue(object : Callback<BaseModel<K>> {
                        override fun onFailure(call: Call<BaseModel<K>>?, t: Throwable?) {
                            onDataFailure(call, t)
                        }

                        override fun onResponse(call: Call<BaseModel<K>>?, response: Response<BaseModel<K>>?) {

                            handleResponse(load, response)
                        }
                    })
                }
            }

            override fun onRefresh() {
                val call = getRequestCall()
                call?.let {
                    call.enqueue(object : Callback<BaseModel<K>> {
                        override fun onFailure(call: Call<BaseModel<K>>?, t: Throwable?) {
                            onDataFailure(call, t)
                        }

                        override fun onResponse(call: Call<BaseModel<K>>?, response: Response<BaseModel<K>>?) {

                            handleResponse(refresh, response)
                        }
                    })
                }

            }
        })

//        base_sv_refresh.header = DefaultHeader(this)
        base_sv_refresh.footer = DefaultFooter(this)
        //base_sv_refresh.isEnable = false
        //base_sv_refresh.setGive(SpringView.Give.TOP)
        //base_sv_refresh.type = SpringView.Type.FOLLOW
    }

    private fun handleResponse(action: Int, response: Response<BaseModel<K>>?) {
        if (response != null && response.isSuccessful) {
            //TODO errorCode == 200 判断

            if (response.body()?.results != null) {
                onDataResponse(action, response?.body())
            } else {
                if(action == request){
                    layout_state.showEmpty()
                }

            }


        } else {
            if(action == request){
                layout_state.showError()
            }

        }
    }

    abstract protected fun getRequestCall(): Call<BaseModel<K>>?
    //刷新和加载不一定要抽象继承
    abstract protected fun getRefreshCall(): Call<BaseModel<K>>?
    abstract protected fun getLoadCall(): Call<BaseModel<K>>?

    abstract fun onDataResponse(action: Int, response: BaseModel<K>?)
    abstract fun onDataFailure(call: Call<BaseModel<K>>?, t: Throwable?)

    abstract fun requestData()
    abstract fun initStateLayout()
    protected fun setLoading(layout:Int){
        layout_state.setLoading(layout)
    }
    protected fun setEmpty(layout:Int){
        layout_state.setEmpty(layout)
    }

    protected fun setError(layout:Int){
        layout_state.setError(layout)
    }
    protected fun setReTryListener(clickListener: View.OnClickListener){
        layout_state.setRetryListener(clickListener)
    }
    protected fun onDataResult(action: Int,list:ArrayList<T>?){

        base_sv_refresh.onFinishFreshAndLoad()
        when(action){
            request ->{
                if(list == null || list.isEmpty()){
                    layout_state.showEmpty()
                }else{
                    layout_state.showContent()
                    mDataList.addAll(list)
                    adapter.notifyDataSetChanged()
                }

            }
            refresh ->{
                if(list != null && !list.isEmpty()){
                    mDataList.clear()
                    layout_state.showContent()
                    mDataList.addAll(list)
                    adapter.notifyDataSetChanged()
                }

            }
            load ->{
                if(list != null && !list.isEmpty()){
                    mDataList.addAll(list)
                    adapter.notifyDataSetChanged()
                }

            }
            else ->{

            }

        }



    }

    open protected fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this)
    }

    open protected fun getItemAnimator(): SimpleItemAnimator {
        return DefaultItemAnimator()
    }

    open protected fun getItemType(position: Int): Int {
        return 0
    }

    protected abstract fun onBaseCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder

    inner class ListAdapter : BaseListAdapter() {
        override fun getDataCount(): Int {
            return mDataList.size
        }

        override fun onCreateNormalViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
            return onBaseCreateViewHolder(parent, viewType)
        }

        override fun getDataViewType(position: Int): Int {
            return getItemType(position)
        }
    }


}