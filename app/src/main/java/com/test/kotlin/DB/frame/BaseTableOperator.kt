package com.test.kotlin.DB.frame

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.text.TextUtils

/**
 * Created by zenghao on 2017/6/30.
 */
abstract class BaseTableOperator<FIELDS: BaseTableFields, HELPER :BaseTableHelper> constructor(var context: Context,var helper:HELPER){


    fun getContentResolver():ContentResolver{
        return context.contentResolver
    }
    fun getTableHelper():HELPER{
        return helper
    }

    abstract fun getUri(): Uri
    abstract fun createColumns(cursor:Cursor):ArrayList<FIELDS>

    /**
     * 添加数据
     */
    fun insert(f:FIELDS):Long{
        var cv = ContentValues()
        f.setValues(cv)
        var uri = getContentResolver().insert(getUri(),cv)
        if(uri!=null){
            return ContentUris.parseId(uri)
        }
        return -1
    }

    fun update(cv:ContentValues,selection:String,selectionArgs:Array<String>):Int{
        return getContentResolver().update(getUri(),cv,selection,selectionArgs)
    }

    fun update(primaryValue:Any,t:FIELDS):Int{
        if (t == null){
            return -1
        }

        var cv = ContentValues()
        t.setValues(cv)
        return update(cv,"${getTableHelper().getPrimaryKey()}=?", arrayOf(primaryValue.toString()))
    }

    fun update(updateData:FIELDS):Int{
        if(updateData == null){
            return -1
        }
        var sqls = updateData.getSql()
        if (sqls == null || !sqls.isValid()) {
            return -1
        }

        var cv = ContentValues()
        updateData.setValues(cv)

        return update(cv,sqls.selection,sqls.selectionArgs.toTypedArray())
    }

    fun delete(selection: String,selectionArgs: Array<String>):Int{
        return getContentResolver().delete(getUri(),selection,selectionArgs)
    }

    fun delete(primaryValue: Any):Int{
        if (primaryValue == null){
            return -1
        }
        return delete("${getTableHelper().getPrimaryKey()}=?", arrayOf(primaryValue.toString()))
    }

    fun query(selection: String, selectionArgs: Array<String>, orderBy: String):ArrayList<FIELDS>{
        return query(selection,selectionArgs,orderBy,0)
    }

    fun query(selection: String,selectionArgs: Array<String>):FIELDS?{

        var list = query(selection,selectionArgs,null,1)
        if (list!=null && !list.isEmpty()){
            return list[0]
        }
        return null
    }

    fun query(primaryKey:String):FIELDS?{
        if(TextUtils.isEmpty(primaryKey)){
            return null
        }
        return query("${getTableHelper().getPrimaryKey()}=?", arrayOf(primaryKey))
    }

    fun query(selection: String, selectionArgs: Array<String>, orderBy:String?, limit:Int):ArrayList<FIELDS>{
        var order:String
        var list:ArrayList<FIELDS> = ArrayList()
        if (limit > 0){
            if (TextUtils.isEmpty(orderBy)){
                order = "${getTableHelper().getDefaultSortOrder()} limit $limit"
            }else{
                order = "$orderBy limit $limit"
            }
           var cursor = getContentResolver().query(getUri(),null,selection,selectionArgs,order)


            try {
                list = createColumns(cursor)
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                if(cursor != null && !cursor.isClosed ){
                    cursor.close()
                }
            }
        }
        return list
    }

    fun getCount(selection: String, selectionArgs: Array<String>, limit: Int):Int{
        var count = 0
        var projection = arrayOf(" count(*)")
        var orderBy:String? = null
        if(limit > 0){
            orderBy = "${getTableHelper().getDefaultSortOrder()} limit $limit"
        }
        var cursor = getContentResolver().query(getUri(),projection,selection,selectionArgs,orderBy)
        if (cursor!=null){
            if(cursor.moveToFirst()){
                count = cursor.getInt(0)
            }
            cursor.close()
        }

        return count
    }

    fun getCount(selection: String,selectionArgs: Array<String>):Int{
        return getCount(selection,selectionArgs,-1)
    }

    fun isExist(primaryKey: String):Boolean{
        if (TextUtils.isEmpty(primaryKey)){
            return false
        }
        return getCount("${getTableHelper().getPrimaryKey()}=?", arrayOf(primaryKey),1) > 0
    }

    fun get(condition:FIELDS):FIELDS?{
        if(condition == null){
            return null
        }
        var sqls = condition.getSql()
        if (sqls == null || !sqls.isValid()){
            return null
        }

        return query(sqls.selection,sqls.selectionArgs.toTypedArray())
    }
}