package com.test.kotlin.permission

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

/**
 * Created by zenghao on 2017/6/22.
 */
class PermissionTips (cotext: Context) {

    private var alertBuilder: AlertDialog.Builder? = null

    init {
        alertBuilder = AlertDialog.Builder(cotext)
        alertBuilder!!.setCancelable(false)
        alertBuilder!!.create()
    }

    fun setTitle(title:String):PermissionTips{
        alertBuilder!!.setTitle(title)
        return this
    }

    fun setIcon(iconId:Int):PermissionTips{
        alertBuilder!!.setIcon(iconId)
        return this
    }

    fun setMessage(msg:String):PermissionTips{
        alertBuilder!!.setMessage(msg)
        return this
    }

    fun setOnSingleListener(listener:SingleListener):PermissionTips{
        alertBuilder!!.setNegativeButton("确定",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                listener.onClick()
            }
        })
        return this
    }

    fun setOnDoubleListener(listener:DoubleListener):PermissionTips{
        alertBuilder!!.setNegativeButton("取消",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                listener.onCancle()
            }
        }).setPositiveButton("确定",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                listener.onOk()
            }
        })
        return this
    }

    fun show(){
        alertBuilder!!.show()
    }

    interface SingleListener{
       fun  onClick()
    }
    interface DoubleListener{
        fun onCancle()
        fun onOk()
    }
}