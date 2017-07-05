package com.test.kotlin.DB.frame.sample

import android.database.sqlite.SQLiteDatabase
import com.test.kotlin.DB.frame.CustomTableHelper

/**
 * Created by zenghao on 2017/7/3.
 */
class UserInfoHelper private constructor():CustomTableHelper(){


    companion object{
        const val TABLE_NAME = "user_info"
        fun getImpl():UserInfoHelper{
            return Holder.instance
        }
    }

    override fun getTableName(): String {
        return TABLE_NAME
    }

    override fun onDataBaseCreate(db: SQLiteDatabase) {
       var create = getCustomCreatePre() + UserInfoField.FIELD_NAME + " TEXT," + UserInfoField.FIELD_SEX+ " TEXT," + UserInfoField.FIELD_AGE + " TEXT);";
        db.execSQL(create);
    }

    override fun onDataBaseUpgrade(oldVersion: Int, newVersion: Int, db: SQLiteDatabase) {
        super.onDataBaseUpgrade(oldVersion, newVersion, db)
    }

    private object Holder{
        val instance = UserInfoHelper()
    }
}