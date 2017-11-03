package com.test.kotlin.BaseList.base

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.ViewGroup
import com.test.kotlin.BaseList.widget.BaseListAdapter
import com.test.kotlin.BaseList.widget.BaseViewHolder
import com.test.kotlin.R
import kotlinx.android.synthetic.main.activity_base_list.*

/**
 * Created by zenghao on 2017/8/21.
 * recyclerView列表基类
 */
abstract class BaseListActivity<T> : BaseActivity(){

    protected lateinit var adapter:BaseListAdapter
    protected var mDataList: ArrayList<T> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_list)
        initUI()
        requestData()
    }

    private fun initUI(){
        rv_base_list.layoutManager = getLayoutManager()
        rv_base_list.itemAnimator = getItemAnimator()
        //添加间隔
        //ItemDecoration
        adapter = ListAdapter()
        rv_base_list.adapter = adapter
    }

    abstract fun requestData()

    open protected fun getLayoutManager(): RecyclerView.LayoutManager{
        return LinearLayoutManager(this)
    }
    open protected fun getItemAnimator(): SimpleItemAnimator {
        return DefaultItemAnimator()
    }


    inner class ListAdapter :BaseListAdapter(){
        override fun getDataCount(): Int {
            return mDataList.size
        }

        override fun onCreateNormalViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
            return onBaseCreateViewHolder(parent,viewType)
        }

        override fun getDataViewType(position: Int): Int {
            return getItemType(position)
        }
    }
    open protected fun getItemType(position: Int):Int{
        return 0
    }
    protected abstract fun onBaseCreateViewHolder(parent: ViewGroup?, viewType: Int):BaseViewHolder
}