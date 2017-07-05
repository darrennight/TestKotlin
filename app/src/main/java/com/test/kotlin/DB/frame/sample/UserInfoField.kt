package com.test.kotlin.DB.frame.sample

import android.database.Cursor
import android.provider.BaseColumns._ID
import com.test.kotlin.DB.frame.BaseTableFields

/**
 * Created by zenghao on 2017/7/3.
 * 规范表字段
 */
class UserInfoField :BaseTableFields{

    constructor():super()

    constructor(cursor: Cursor):super(cursor)

    constructor(values:HashMap<String,Any>):super(values)

    companion object{
        const val FIELD_NAME = "name"
        const val FIELD_SEX = "sex"
        const val FIELD_AGE = "age"
    }

    fun setName(name:String){
        put(FIELD_NAME,name)
    }
    fun getName():String{
        return get(FIELD_NAME) as String
    }

    fun setSex(sex:String){
        put(FIELD_SEX,sex)
    }

    fun getSex():String{
        return get(FIELD_SEX) as String
    }
    fun setAge(age:String){
        put(FIELD_AGE,age)
    }
    fun getAge():String{
        return get(FIELD_AGE) as String
    }
    override fun put(cursor: Cursor) {
        if(cursor == null || cursor.isClosed || cursor.isAfterLast){
            return
        }

        set_Id(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)))
        setName(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_NAME)))
        setSex(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SEX)))
        setAge(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_AGE)))
    }
}