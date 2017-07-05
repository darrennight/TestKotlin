package com.test.kotlin.DB.frame

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import android.text.TextUtils

/**
 * Created by zenghao on 2017/6/30.
 */
abstract class BaseTableFields: BaseColumns{
    /**key-—>表字段 value->值*/
    protected var columnValues:HashMap<String,Any> = hashMapOf()

    constructor()

    constructor(cursor:Cursor){
        put(cursor)
    }

    constructor(values:HashMap<String,Any>){
        this.columnValues = values
    }

    companion object{
        //创建时间
        const val CREATE_AT = "createAt"
        //修改时间
        const val MODIFIED_AT = "modifiedAt"
    }


    fun set_Id(_id:Int){
        put(_ID,_id)
    }

    fun get_Id():Int{
        return if (get(_ID) == null) -1 else get(_ID) as Int
    }

    fun get(key:String):Any?{
        return columnValues.getValue(key)
    }



    fun put(key:String,value:Any){
        columnValues.put(key,value)
    }

    fun clear(){
        columnValues.clear()
    }

    fun remove(key: String){
       columnValues.remove(key)
    }

    abstract fun put(cursor:Cursor)

    fun getSql():SQLString{

        var selectionArgsList:ArrayList<Any> = ArrayList()
        var selection:String =""
        for (item in columnValues){
            var key:String = item.key
            var value:Any = item.value
            selection += key + "=? AND "
            selectionArgsList.add(value)
        }

        if(selection.length > 0){
            selection = selection.substring(0,selection.length - " AND ".length)
        }
        var selectionArgs:ArrayList<String> = ArrayList()
        if (selectionArgsList.size > 0){

            for (item in selectionArgsList){
                if (item is String){
                    selectionArgs.add(item)
                }

            }
        }

        return SQLString(selection,selectionArgs)
    }

    fun setValues(cv: ContentValues):ContentValues?{
        if (cv == null){
            return null
        }
       for (item in columnValues){

           var key = item.key
           var value = item.value
           if (value is String){
               cv.put(key,value)
           }else if(value is Long){
               cv.put(key,value)
           }else if(value is Int){
               cv.put(key,value)
           }else if(value is Short){
               cv.put(key,value)
           }else if(value is Byte){
               cv.put(key,value)
           }else if(value is Double){
               cv.put(key,value)
           }else if (value is Float){
               cv.put(key,value)
           }else if (value is Boolean){
               cv.put(key,value)
           }
       }

        return cv
    }


    inner class SQLString constructor(var selection:String,var selectionArgs:ArrayList<String>){

        fun isValid():Boolean{
            return !TextUtils.isEmpty(selection) && selectionArgs != null && selectionArgs.size > 0
        }

    }
}