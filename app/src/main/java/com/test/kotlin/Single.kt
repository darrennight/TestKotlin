package com.test.kotlin

/**
 * kotlin单例 静态内部类方式
 * static静态的东西写在companion里面
 * 一个类只能声明一个companion
 * Created by zenghao on 2017/5/26.
 */
class Single private constructor() {
    companion object {
        fun get():Single{
            return Holder.instance
        }
    }

    private object Holder {
        val instance = Single()
    }
}