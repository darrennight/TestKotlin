package com.test.kotlin.other

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kotlin.R
import com.test.kotlin.permission.PermissionListener
import com.test.kotlin.permission.PermissionManager
import kotlinx.android.synthetic.main.fragment_kotlin.*

/**
 * Created by zenghao on 2017/5/25.
 */
class KotlinFragment : Fragment(){


    /*companion object Instance{
        fun getInstance(): KotlinFragment{
            return KotlinFragment()
        }
    }*/

//    companion 只能声明一个这样的东西

    companion object{
        fun getInstanc(): KotlinFragment{
            return KotlinFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_kotlin,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_fagmetn_permission.setOnClickListener {
            PermissionManager.getInstance().with(this@KotlinFragment)
                    .requestPermissions()
                    .setShowDialog(false)
                    .setPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA))
                    .setPermissionListener(object : PermissionListener {
                        override fun onPermission(request: Int, result: Int) {
                            if (result == PermissionManager.PERMISSION_SUCCESS){
                                //权限申请成功，可以继续往下走
                                Log.e("===XPermissions","PERMISSION_SUCCESS")
                            }else{
                                //权限申请失败，此时应该关闭界面或者退出程序
                                Log.e("===XPermissions","PERMISSION_FAIL")
                            }
                        }
                    }).build()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.handlerPermissionResult(requestCode,permissions,grantResults.toTypedArray())
    }
}