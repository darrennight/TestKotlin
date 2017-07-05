package com.test.kotlin.DB.frame.sample

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.test.kotlin.App
import com.test.kotlin.DB.frame.BaseTableOperator

/**
 * Created by zenghao on 2017/7/3.
 */
class UserInfoOperator :BaseTableOperator<UserInfoField,UserInfoHelper>{

    constructor(context: Context,helper:UserInfoHelper):super(context,helper)

    companion object{
        fun getImpl():UserInfoOperator{
            return Holder.instance
        }
    }

    override fun getUri(): Uri {
        return getTableHelper().getContentUri(SampleDB.AUTHORITY)
    }

    override fun createColumns(cursor: Cursor): ArrayList<UserInfoField> {
        var list = ArrayList<UserInfoField>()

        if(cursor != null){
            while (cursor.moveToNext()){
                list.add(UserInfoField(cursor))
            }
        }
        return list
    }

    private object Holder{
        val instance = UserInfoOperator(App.instance, UserInfoHelper.getImpl())
    }
}