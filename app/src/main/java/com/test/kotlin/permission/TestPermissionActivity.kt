package com.test.kotlin.permission

import android.Manifest
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.test.kotlin.R
import com.test.kotlin.other.KotlinFragment
import com.test.kotlin.other.MainOtherActivity
import kotlinx.android.synthetic.main.activity_permission.*
import java.util.logging.Logger

/**
 * Created by zenghao on 2017/6/21.
 */
class TestPermissionActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        btn_test_permission.setOnClickListener {
            //PermissionManager.getInstance().init(FragmentTarget(KotlinFragment.getInstanc()))
            PermissionManager.getInstance().with(this@TestPermissionActivity)
                    .requestPermissions()
                    .setShowDialog(false)
                    .setPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA))
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

        btn_go2.setOnClickListener {
            startActivity(Intent(this@TestPermissionActivity,TestPermissionActivity2::class.java))
        }
        btn_gofagment.setOnClickListener {

            startActivity(Intent(this@TestPermissionActivity,MainOtherActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.handlerPermissionResult(requestCode,permissions,grantResults.toTypedArray())
    }
}