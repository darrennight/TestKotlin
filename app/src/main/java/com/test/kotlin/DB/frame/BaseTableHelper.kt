package com.test.kotlin.DB.frame

import android.database.sqlite.SQLiteDatabase
import android.net.Uri

/**
 * Created by zenghao on 2017/6/30.
 * 数据表规范
 * 规范(表名、主键、默认查询结果排序、表创建、表升级)
 */
abstract class BaseTableHelper {

    companion object{
        const val CREATE_TABLE_SQL_PRE = "CREATE TABLE IF NOT EXISTS "
    }

    abstract fun getPrimaryKey():String
    abstract fun getTableName():String
    abstract fun getDefaultSortOrder():String
    abstract fun onDataBaseCreate(db:SQLiteDatabase)
    abstract fun onDataBaseUpgrade(oldVersion:Int,newVersion:Int,db: SQLiteDatabase)

    /**
     * 创建表的sql语句
     * 默认语句
     */
    protected fun getCustomCreatePre():String{
        return "$CREATE_TABLE_SQL_PRE${getTableName()} (${getPrimaryKey()} INTEGER PRIMARY KEY AUTOINCREMENT,${BaseTableFields.CREATE_AT} DATETIME DEFAULT(DATETIME('now', 'localtime')),"
    }

    fun getContentUri(authority: String): Uri {
        return Uri.parse(BaseContentProvider.getContentAuthoritySlash(authority) + getTableName())
    }

    fun getContentType(authority: String):String{
        return "vnd.android.cursor.dir/$authority.${getTableName()}"
    }

    fun getEntryContentType(authority: String):String{
        return "vnd.android.cursor.item/$authority.${getTableName()}"
    }

}