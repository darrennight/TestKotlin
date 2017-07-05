package com.test.kotlin.patterns

import android.util.Log


/**
 * Created by zenghao on 2017/6/5.
 */
object PrinterDriver {
    init {
//        println("Initializing with object: $this")
    }

    fun print() = Log.e("","Printing with object: $this")
}

fun main(args: Array<String>) {
    Log.e("","Start")
    PrinterDriver.print()
    PrinterDriver.print()
}