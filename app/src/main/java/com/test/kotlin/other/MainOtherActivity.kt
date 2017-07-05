package com.test.kotlin.other

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.test.kotlin.R
import kotlinx.android.synthetic.main.activity_main_other.*
import java.util.*

/**
 * Created by zenghao on 2017/5/25.
 */
class MainOtherActivity : AppCompatActivity(){

    //定义成员变量又想该变量不为空，又不想子定义时就初始化要怎么办呢？使用lateinit关键字
    //lateinit 不能修饰val 和基本类型
    lateinit var adapter: MyFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_other)
        initViews()
    }



   private fun initViews(){
        val fragments = ArrayList<Fragment>()
        fragments.add(KotlinFragment())
        fragments.add(KotlinFragment())
        fragments.add(KotlinFragment())
       fragments.add(KotlinFragment.getInstanc())
       fragments.add(KotlinFragment2.getInstance())
       fragments.add(KotlinFragment3.getInstance("hahahaKotlinFragment3"))

        viewPager.adapter = MyFragmentAdapter(fragments,supportFragmentManager)

    }
}