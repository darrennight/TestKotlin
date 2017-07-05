package com.test.kotlin.bean

/**
 *
 * 单例
 * Created by zenghao on 2017/5/25.
 */
class User {


    companion object {
        @Volatile var instance: User? = null
            get() {
                if (field == null) {
                    synchronized(User::class.java) {
                        if (field == null)
                            field = User()
                    }
                }
                return field
            }
    }

    var name: String? = null
    var age: Int? = null
}