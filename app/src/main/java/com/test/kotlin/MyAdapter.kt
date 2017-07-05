package com.test.kotlin

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kotlin.bean.FanFouBean
import kotlinx.android.synthetic.main.fanfou_item_layout.view.*


/**
 * Created by zenghao on 2017/5/24.
 */
 class MyAdapter(val context: Context, val list: List<FanFouBean>) : RecyclerView.Adapter<MyAdapter.FanFouHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FanFouHolder {
            val view:View = LayoutInflater.from(context).inflate(R.layout.fanfou_item_layout,parent,false)

        return FanFouHolder(view)
    }

    override fun onBindViewHolder(holder: FanFouHolder, position: Int) {

        val fan = list[position]
        holder.itemView.tv_author.text = fan.author
        holder.itemView.tv_content.text = fan.content
        holder.itemView.tv_time.text = fan.time
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context,DetailActivity::class.java))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class FanFouHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}