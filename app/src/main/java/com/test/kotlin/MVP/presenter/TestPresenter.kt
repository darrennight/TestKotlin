package com.test.kotlin.MVP.presenter

import com.test.kotlin.MVP.Model.BaseModel
import com.test.kotlin.MVP.Model.TestModel
import com.test.kotlin.MVP.view.BaseView
import com.test.kotlin.MVP.view.TestView
import com.test.kotlin.net.IndexData

/**
 * Created by zenghao on 2017/6/7.
 */
class TestPresenter(override val view:TestView,override val model: TestModel) :BasePresenter<BaseView,BaseModel>{


    fun getIndexData(){
       model.getIndexData(object :CallBackListener{
           override fun onCall(data: IndexData?) {
               view.showList(data)
           }
       })

    }

    interface CallBackListener{
        fun onCall(data:IndexData?)
    }
}