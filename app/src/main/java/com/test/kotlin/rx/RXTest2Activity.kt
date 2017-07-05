package com.test.kotlin.rx

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.test.kotlin.R
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_test_rxbus2.*

/**
 * Created by zenghao on 2017/6/27.
 */
class RXTest2Activity :RxAppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_rxbus2)

        btn_post2.setOnClickListener {
            if (RxBus.getRxBus().hasObservers()){
                RxBus.getRxBus().post(TestEvent("test2"))
                Log.e("=====","1111111hahhaha")
            }
            Log.e("=====","hahhaha")
        }

        RxBus.getRxBus().toObservable(TestEvent2::class.java).subscribe(object: Consumer<TestEvent2>{
            override fun accept(t: TestEvent2?) {
                Log.e("====","stack"+t?.name)
            }
        })
    }
}