package com.test.kotlin.BaseList.widget

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by zenghao on 2017/8/21.
 */
abstract class BaseViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    abstract fun onBindViewHolder(position:Int)
}