package com.test.kotlin.MVP.view

import com.test.kotlin.net.IndexData

/**
 * Created by zenghao on 2017/6/7.
 */
 interface TestView : BaseView{

    fun showList(index: IndexData?)
}