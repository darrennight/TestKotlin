package com.test.kotlin.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import com.test.kotlin.App
import android.support.v4.content.ContextCompat
import android.support.v4.app.AppOpsManagerCompat
import android.text.TextUtils
import android.os.Build



/**
 * Created by zenghao on 2017/6/21.
 */
class PermissionManager private constructor(){

    private var target:Any? = null
    private var permissionBuilder:PermissionBuilder? = null

    private object Holder {
        val instance = PermissionManager()
    }
    companion object{
        //成功
        val PERMISSION_SUCCESS:Int = 0
        //失败
        val PERMISSION_FAIL:Int = -1
        //系统权限设置
        val PERMISSION_SETTING:Int = -2

        fun getInstance():PermissionManager{
            return Holder.instance
        }

        fun handlerPermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: Array<Int>){
            getInstance().permissionResult(requestCode,permissions,grantResults)
        }
    }

    fun with(target: Activity):PermissionManager{
        this.target = target
        return this
    }

    fun with(traget: Fragment):PermissionManager{
        this.target = traget
        return this
    }


    fun requestPermissions():PermissionBuilder{
        this.permissionBuilder = PermissionBuilder(target!!)
        return this.permissionBuilder!!
    }

    private fun shouldShowRequestPermissionRationale(target: Any,permission:String):Boolean{
        if(target is Activity){
            return ActivityCompat.shouldShowRequestPermissionRationale(target!!, permission)
        }else if(target is Fragment){
            return target.shouldShowRequestPermissionRationale(permission)
        }
        return false
    }

    fun permissionResult(requestCode:Int,permissions:Array<out String>,grantResults:Array<Int>){

        //保存拒绝过但是可以重试的权限
        var shouldShowList = ArrayList<String>()

        //保存所有被拒绝的权限
        var deniedList = ArrayList<String>()

        //记录权限是否是有点击 不再提示的权限
        var noLongerShow = HashMap<String,Boolean>()

        for (i in permissions.indices){
            if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                //被拒绝的权限
                var permission = permissions[i]
                //var isShould = ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)
                var isShould = shouldShowRequestPermissionRationale(target!!,permission)
                noLongerShow.put(permission,isShould)
                deniedList.add(permission)

                if(isShould){
                    shouldShowList.add(permission)
                }
            }
        }

        /**
         * 1.所有权限都同意直接回调成功结果return
         * 2.申请的权限有不能弹出系统权限提示框的提示跳转到系统权限设置界面return
         * 3.有拒绝的权限但是都能弹出系统权限提示框弹出自定义dialog继续操作
         */

        if (deniedList.size == 0){
            /***
             * 没有被拒绝的
             */
            PermissionHelper.onHandlerCallback(permissionBuilder!!,requestCode, PERMISSION_SUCCESS)
        }else{
            /**
             * 检测是否有被勾选不再提示的
             */
            val entry = noLongerShow.entries
                entry.forEach {
                 if (!it.value){
                    //有权限申请对话框拒绝不再显示
                    //弹出自定义dialog提示跳转到系统权限设置
                     PermissionTips(getDialogContext(target!!)).setTitle("系统设置权限")
                             .setMessage("去系统设置界面授予权限")
                             .setOnDoubleListener(object :PermissionTips.DoubleListener{
                                 override fun onCancle() {
                                     PermissionHelper.onHandlerCallback(permissionBuilder!!,requestCode, PERMISSION_FAIL)
                                 }

                                 override fun onOk() {
                                     PermissionHelper.startAppSettings(target!!)

                                     PermissionHelper.onHandlerCallback(permissionBuilder!!,requestCode, PERMISSION_SETTING)
                                 }
                             }).show()
                    return
                }
            }

            PermissionTips(getDialogContext(target!!)).setTitle("权限")
                    .setMessage("重试权限申请")
                    .setOnDoubleListener(object :PermissionTips.DoubleListener{
                        override fun onCancle() {
                            PermissionHelper.onHandlerCallback(permissionBuilder!!,requestCode, PERMISSION_FAIL)
                        }

                        override fun onOk() {
                            permissionBuilder!!.requestCode = requestCode
                            permissionBuilder!!.setPermissions(shouldShowList.toTypedArray())
                            permissionBuilder!!.setShowDialog(false)
                            PermissionHelper.requestPermissions(permissionBuilder!!)
                        }
                    }).show()


        }

    }




   private fun getDialogContext(target: Any):Context{
        if (target is Activity){
            return target
        }else if (target is Fragment){
            return target.activity
        }

        return App.instance
    }

    /**
     * 国产手机权限申请适配
     * 判断是否有权限
     * 在申请后结果回调中判断
     * 失败成功都需要判断
     */
    private fun hasPermission(context: Context, permissions: List<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
        for (permission in permissions) {
            val op = AppOpsManagerCompat.permissionToOp(permission)
            if (TextUtils.isEmpty(op)) continue
            var result = AppOpsManagerCompat.noteProxyOp(context, op, context.packageName)
            if (result == AppOpsManagerCompat.MODE_IGNORED) return false
            result = ContextCompat.checkSelfPermission(context, permission)
            if (result != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }


}