package com.test.kotlin.bean

/**
 * Created by zenghao on 2017/5/26.
 */
class Person(private var name: String) {
    private var description: String? = null

    init {
        name = "Zhang Tao"
    }

    constructor(name: String, description: String) : this(name) {
        this.description = description
    }

    internal fun sayHello() {
        println("hello $name")
    }
}