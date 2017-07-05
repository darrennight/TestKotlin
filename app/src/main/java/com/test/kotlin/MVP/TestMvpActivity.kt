package com.test.kotlin.MVP

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.test.kotlin.MVP.Model.TestModel
import com.test.kotlin.MVP.presenter.TestPresenter
import com.test.kotlin.MVP.view.TestView
import com.test.kotlin.R
import com.test.kotlin.net.IndexData
import com.test.kotlin.toast
import kotlinx.android.synthetic.main.activity_test_mvp.*

/**
 * Created by zenghao on 2017/6/6.
 */
class TestMvpActivity : AppCompatActivity(),TestView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_mvp)
        var model = TestModel()
        var present = TestPresenter(this@TestMvpActivity,model)
        present.getIndexData()

    }

    override fun showList(index: IndexData?) {
        if(index!=null){
            toast("hahaha")
            tv_index.setText(index.date_time)
        }
    }
}