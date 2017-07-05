package com.test.kotlin.permission

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

/**
 * Created by zenghao on 2017/6/21.
 */
class PermissionHelper private constructor(){


    companion object{

        val PACKAGE_URL_SCHEME by lazy { "package:" }

        fun onHandlerCallback(builder: PermissionBuilder,request:Int,result:Int){
            if(null != builder.permissionListener){
                builder!!.permissionListener!!.onPermission(request,result)
            }
        }

        /***
         * 添加一个需要申请的权限
         */
        private fun addPermission(target: Any, list:ArrayList<String>,permission:String):Boolean{

            if (target is  Activity){
                if(ContextCompat.checkSelfPermission(target,permission) != PackageManager.PERMISSION_GRANTED){
                    list.add(permission)
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(target,permission)){
                        return true
                    }
                }
            }else if(target is Fragment){
                if(ContextCompat.checkSelfPermission(target.activity,permission) != PackageManager.PERMISSION_GRANTED){
                    list.add(permission)
                    if (!target.shouldShowRequestPermissionRationale(permission)){
                        return true
                    }
                }
            }


            return false
        }
        fun requestPermissions(builder:PermissionBuilder){
            if(null == builder.pmermissions || builder.pmermissions!!.size <= 0){
                return
            }
            //低版本不检测
            if(Build.VERSION.SDK_INT < 23){
                onHandlerCallback(builder,builder.requestCode,PermissionManager.PERMISSION_SUCCESS)
                return
            }
            //需要请求的权限列表
            var neededList = ArrayList<String>()
            //需要提示用户的权限列表
            var shouldShowList = ArrayList<String>()

            for (i in builder.pmermissions!!.indices){
                    var isNeed = addPermission(builder.target!!,neededList,builder.pmermissions!![i])
                if (isNeed){
                    shouldShowList.add(builder.pmermissions!![i])
                }

            }

            if (shouldShowList.size > 0 && builder.showDialog){
                //弹出权限申请提示框用于解释
                return
            }


            if(neededList.size > 0){
                if (builder.target is Activity){
                    ActivityCompat.requestPermissions(builder.target,neededList.toTypedArray(),builder.requestCode)
                }else if(builder.target is Fragment){

                    builder.target.requestPermissions(neededList.toTypedArray(),builder.requestCode)
                }

            }else{
                onHandlerCallback(builder,builder.requestCode,PermissionManager.PERMISSION_SUCCESS)
            }


        }

        fun startAppSettings(target:Any){
            if(target is Activity){
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse(PACKAGE_URL_SCHEME + target.packageName)
                target.startActivity(intent)

            }else if (target is Fragment){
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse(PACKAGE_URL_SCHEME + target.activity.packageName)
                target.startActivity(intent)
            }

        }

    }
}