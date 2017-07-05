package com.test.kotlin.DB.frame.sample

import com.test.kotlin.DB.frame.BaseContentProvider
import com.test.kotlin.DB.frame.BaseTableHelper

/**
 * Created by zenghao on 2017/7/3.
 */
class SampleDB :BaseContentProvider(AUTHORITY){

    companion object{
        const val DATABASE_NAME = "sample.db"
        const val AUTHORITY = "sampledb"
        const val DATABASE_VERSION = 1
    }

    override fun getDataBaseName(): String {
        return DATABASE_NAME
    }

    override fun getDataBaseVersion(): Int {
        return DATABASE_VERSION
    }

    override fun createAllTableHelper(): HashMap<String, BaseTableHelper> {
        var hashMap = HashMap<String,BaseTableHelper>()
        var userInfoHelper = UserInfoHelper.getImpl()
        hashMap.put(userInfoHelper.getTableName(),userInfoHelper)
        return hashMap
    }
}