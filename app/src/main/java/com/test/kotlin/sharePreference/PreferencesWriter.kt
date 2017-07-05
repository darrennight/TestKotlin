package com.test.kotlin.sharePreference

import android.content.Context
import android.content.SharedPreferences
import com.test.kotlin.App
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by zenghao on 2017/7/3.
 */
abstract class PreferencesWriter<T>() :ReadWriteProperty<Any?,T>{

    private var keName:String? = null
    private var defaultValue:T? = null
    private var preference:SharedPreferences? = null

    init {
        this.keName = initFileName()
        this.preference = App.instance.getSharedPreferences(keName,Context.MODE_PRIVATE)
        initPreferenceChanges(getVersion())
    }

    companion object{
        const val KEY_PREFERENCES_VERSION = "preferences_version"
        const val DEFAULT_PREFERENCES_VERSION = 0

    }

    protected abstract fun initFileName():String
    protected abstract fun initPreferenceChanges(version:Int)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(keName!!,defaultValue!!)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(keName!!,value)
    }

    private fun <U> findPreference(name: String?, default: U): U = with(preference!!) {
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

    private fun <U> putPreference(name: String, value: U) = with(preference!!.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("The data can not be saved")
        }.apply()
    }

    protected fun getVersion():Int{
        return preference!!.getInt(KEY_PREFERENCES_VERSION,DEFAULT_PREFERENCES_VERSION)
    }
}