package com.test.kotlin.BaseList.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.test.kotlin.R
import kotlinx.android.synthetic.main.activity_hello_list.*

/**
 * Created by zenghao on 2017/8/22.
 */
class HelloListActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_list)

        btn_sample_list.setOnClickListener {
            _-> startActivity(Intent(this@HelloListActivity,SampleListActivity::class.java))
        }

        btn_sample_fragment_list.setOnClickListener {
            _-> startActivity(Intent(this@HelloListActivity,SampleListFragmentActivity::class.java))
        }

        btn_sample_refresh_list.setOnClickListener {
            _-> startActivity(Intent(this@HelloListActivity,SampleRefreshListActivity::class.java))
        }
    }
}