package com.test.kotlin.bean

/**
 * Created by zenghao on 2017/7/6.
 */
class Tester (var name:String,var age:Int) {

    fun testSetName(testName:String){
        this.name = testName
    }
    fun testSetAge(age: Int){
        this.age = age
    }
}