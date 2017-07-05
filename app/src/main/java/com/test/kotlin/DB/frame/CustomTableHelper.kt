package com.test.kotlin.DB.frame

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

/**
 * Created by zenghao on 2017/6/30.
 */
abstract class CustomTableHelper:BaseTableHelper() {

    override fun getPrimaryKey(): String {
        return BaseColumns._ID
    }

    override fun getDefaultSortOrder(): String {
        return "${getPrimaryKey()} DESC "
    }

    override fun onDataBaseUpgrade(oldVersion: Int, newVersion: Int, db: SQLiteDatabase) {

    }
}