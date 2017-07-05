package com.test.kotlin.permission

/**
 * Created by zenghao on 2017/6/21.
 */
interface PermissionListener {

    fun onPermission(request:Int,result:Int)

}