package com.test.kotlin.permission

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.test.kotlin.R
import kotlinx.android.synthetic.main.activity_permission2.*

/**
 * Created by zenghao on 2017/6/21.
 */
class TestPermissionActivity2 :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_permission2)
        btn_test_permission2.setOnClickListener {
                PermissionManager.getInstance().with(this@TestPermissionActivity2)
                        .requestPermissions()
                        .setShowDialog(false)
                        .setPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA))
                        .setPermissionListener(object :PermissionListener{
                            override fun onPermission(request: Int, result: Int) {
                                if (result == PermissionManager.PERMISSION_SUCCESS){
                                    //权限申请成功，可以继续往下走
                                    Log.e("===XPermissions","PERMISSION_SUCCESS");
                                }else{
                                    //权限申请失败，此时应该关闭界面或者退出程序
                                    Log.e("===XPermissions","PERMISSION_FAIL");
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