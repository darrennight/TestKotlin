package com.test.kotlin.BaseList.base

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kotlin.BaseList.widget.BaseListAdapter
import com.test.kotlin.BaseList.widget.BaseViewHolder
import com.test.kotlin.R
import kotlinx.android.synthetic.main.fragment_base_list.*

/**
 * Created by zenghao on 2017/8/23.
 */
abstract class BaseListFragment<T> :BaseFragment(){

    protected lateinit var adapter:BaseListAdapter

    protected var mDataList:ArrayList<T> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_base_list,container,false)
        return view

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        requestData()
    }

    private fun initUI(){
        fragment_rv_base_list.layoutManager = getLayoutManager()
        fragment_rv_base_list.itemAnimator = getItemAnimator()
        adapter = ListAdapter()
        fragment_rv_base_list.adapter = adapter
    }
    abstract fun requestData()

    protected fun getLayoutManager():RecyclerView.LayoutManager{
        return LinearLayoutManager(context)
    }

    protected fun getItemAnimator(): SimpleItemAnimator {
        return DefaultItemAnimator()
    }


    inner class ListAdapter : BaseListAdapter(){
        override fun getDataCount(): Int {
            return mDataList.size
        }

        override fun onCreateNormalViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
            return OnBaseCreateViewHolder(parent,viewType)
        }
    }

    protected abstract fun OnBaseCreateViewHolder(parent: ViewGroup?,viewType: Int): BaseViewHolder
}