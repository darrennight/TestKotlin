package com.test.kotlin.rx

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.test.kotlin.R
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_test_rxbus.*

/**
 * Created by zenghao on 2017/6/21.
 */
class RXTestActivity :RxAppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_rxbus)

        btn_go_test2.setOnClickListener {
            startActivity(Intent(this@RXTestActivity,RXTest2Activity::class.java))
        }

        RxBus.getRxBus().toObservable(TestEvent::class.java).subscribe(object: Consumer<TestEvent>{
            override fun accept(t: TestEvent?) {
                Log.e("====","accept"+t?.name)
            }
        })


        btn_postbus.setOnClickListener {
            if(RxBus.getRxBus().hasObservers()){
                RxBus.getRxBus().post(TestEvent2("TestEvent2"))
            }
        }


        RxBus.getRxBus().toObservable(TestEvent::class.java).compose(bindToLifecycle()).subscribe(object: Consumer<TestEvent> {
            override fun accept(t: TestEvent?) {
                //绑定声明周期 取消订阅
                Log.e("====","Lifecycleaccept"+t?.name)
                tv_bus_msg.text = t?.name
            }
        })

        /**接收消息主线程处理*/
        RxBus.getRxBus().toObservable(TestEvent::class.java).compose(bindToLifecycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(object: Consumer<TestEvent> {
            override fun accept(t: TestEvent?) {
                //绑定声明周期 取消订阅
                Log.e("====","Lifecycleaccept"+t?.name)
                tv_bus_msg.text = t?.name
            }
        })

        /**接收消息子线程处理*/
        RxBus.getRxBus().toObservable(TestEvent::class.java).compose(bindToLifecycle()).observeOn(Schedulers.newThread()).subscribe(object: Consumer<TestEvent> {
            override fun accept(t: TestEvent?) {
                //绑定声明周期 取消订阅
                Log.e("====","Lifecycleaccept"+t?.name)
                tv_bus_msg.text = t?.name
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("====","onDestroyonDestroyonDestroy")
    }
}