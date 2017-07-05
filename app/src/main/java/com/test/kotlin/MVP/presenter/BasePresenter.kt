package com.test.kotlin.MVP.presenter

import android.content.Context

/**
 * Created by zenghao on 2017/6/6.
 */
interface BasePresenter<V,M> {

    val view:V
    val model:M




}