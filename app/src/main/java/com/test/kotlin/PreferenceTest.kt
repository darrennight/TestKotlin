package com.test.kotlin

import android.content.Context
import android.util.Log
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by zenghao on 2017/7/4.
 */
class PreferenceTest <T>() : ReadWriteProperty<Any?, T> {

    var key: String? = null
    var value: T? = null
    var fileName:String = "bnbPreference"

    constructor(fileName: String):this(){
        this.fileName = fileName
    }

    constructor(name: String, default: T):this(){
        key = name
        value = default
    }

    constructor(name: String,default: T,fileName:String):this(){
        key = name
        value = default
        this.fileName = fileName
    }

    val prefs by lazy { App.instance.getSharedPreferences(fileName, Context.MODE_PRIVATE) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(key!!, value!!)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(key!!, value)
    }

    fun delete(vararg key: String): Unit {
        if (key.size == 0) {
            prefs.edit().clear().commit()
            return
        }
        /*for (i in 0..key.size-1) {
            prefs.edit().remove(key[i]).commit()
        }*/

        for (item in key){
            prefs.edit().remove(item).commit()
        }
    }

    private fun <U> findPreference(name: String, default: U): U = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("The data can not be saved")
        }
        res as U
    }

    private fun <U> putPreference(name: String, value: U) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("The data can not be saved")
        }.apply()
    }
}
