package com.test.kotlin.other

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by zenghao on 2017/5/25.
 */
class MyFragmentAdapter(val list: ArrayList<Fragment>,val fm: FragmentManager) :FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }
}