package com.test.kotlin.BaseList.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by zenghao on 2017/8/21.
 */
abstract class BaseListAdapter :RecyclerView.Adapter<BaseViewHolder>(){

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        holder?.let {
            holder.onBindViewHolder(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return onCreateNormalViewHolder(parent,viewType)
    }

    override fun getItemCount(): Int {
        return getDataCount()
    }

    override fun getItemViewType(position: Int): Int {
        return getDataViewType(position)
    }

    abstract fun getDataCount():Int
    open protected fun getDataViewType(position: Int):Int{
        return 0
    }
    /**创建正常holder*/
    abstract fun onCreateNormalViewHolder(parent:ViewGroup?,viewType: Int):BaseViewHolder
}