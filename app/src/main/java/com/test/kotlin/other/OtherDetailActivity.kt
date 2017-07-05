package com.test.kotlin.other

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity

/**
 * Created by zenghao on 2017/5/25.
 */
class OtherDetailActivity :AppCompatActivity(){

    companion object{
        fun launch(context: Context){
            context.startActivity(Intent(context,OtherDetailActivity::class.java))
        }
    }
}