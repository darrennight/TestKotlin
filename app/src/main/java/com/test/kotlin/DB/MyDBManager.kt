package com.test.kotlin.DB

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.test.kotlin.App
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by zenghao on 2017/6/29.
 */
class MyDBManager private constructor(){

    private val openCounter:AtomicInteger = AtomicInteger()
    private val databaseHelper:SQLiteOpenHelper = MyDBHelper( App.instance)
    private var database:SQLiteDatabase? = null


    companion object{
        private val mInstance:MyDBManager = MyDBManager()

        fun getInstance():MyDBManager{
            return mInstance
        }
    }
    @Synchronized
    fun getWriteDatabase():SQLiteDatabase?{
        if(openCounter.incrementAndGet() == 1){
            database = databaseHelper.writableDatabase
        }

        return database
    }

    /***
     * 调用数据库操作完成后要调用此方法
     */
    fun closeDatabase(){
        if(openCounter.decrementAndGet() == 0){
            if(database != null){
                database?.close()
            }
        }
    }
}