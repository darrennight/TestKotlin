package com.test.kotlin.rx

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

/**
 * Created by zenghao on 2017/6/27.
 */
class RxBus private constructor(){

    private val mBus: PublishRelay<Any> = PublishRelay.create()

    companion object{
        private var instance:RxBus = RxBus()

        @Synchronized
        fun getRxBus(): RxBus {
            if (instance == null) {
                instance = RxBus()
            }
            return instance
        }
    }

    fun post(obj:Any){
        mBus.accept(obj)
    }

    fun toObservable(): Observable<Any> = mBus

    fun <T> toObservable(clzz: Class<T>): Observable<T> = mBus.ofType(clzz)

    fun hasObservers():Boolean{
        return mBus.hasObservers()
    }

}