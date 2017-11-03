package com.test.kotlin.BaseList.sample

import android.os.Bundle
import com.test.kotlin.BaseList.base.BaseActivity
import com.test.kotlin.R

/**
 * Created by zenghao on 2017/8/24.
 */
class SampleListFragmentActivity :BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_listfragment)

        val fragmgnet = SampleListFragment()
        supportFragmentManager.beginTransaction().replace(R.id.mSampleListFragmentLayout,fragmgnet).commit()

    }

}