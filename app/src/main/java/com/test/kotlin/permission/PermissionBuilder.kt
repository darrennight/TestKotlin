package com.test.kotlin.permission

import android.app.Activity
import android.support.v4.app.Fragment

/**
 * Created by zenghao on 2017/6/21.
 */
class PermissionBuilder constructor(val target:Any){

     //var target:Any? = null
    //权限申请Code
     var requestCode:Int = 0
    //权限申请数组
     var pmermissions:Array<String>? = null

    //是否弹出申请权限的弹窗提示？
     var showDialog:Boolean = false

    //权限申请回调
     var permissionListener:PermissionListener? = null

    init {

    }

//    constructor(target: Any){
//        this.target = target
//    }

//    fun setActivity(activity: Activity):PermissionBuilder{
//        this.activity = activity
//        return this
//    }
    fun setRequestCode(requestCode:Int):PermissionBuilder{
        this.requestCode = requestCode
        return this
    }
    fun setPermissions(list:Array<String>):PermissionBuilder{
        this.pmermissions = list
        return this
    }
    fun setShowDialog(show:Boolean):PermissionBuilder{
        this.showDialog = show
        return this
    }

    fun setPermissionListener(listener: PermissionListener):PermissionBuilder{
        this.permissionListener = listener
        return this
    }

    fun build(){
            PermissionHelper.requestPermissions(this)
    }

}