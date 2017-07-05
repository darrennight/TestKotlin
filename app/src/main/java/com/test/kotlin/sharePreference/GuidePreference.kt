package com.test.kotlin.sharePreference

/**
 * Created by zenghao on 2017/7/3.
 */
class GuidePreference :PreferencesWriter<Any>(){

    companion object{
        private const val FILE_NAME = "guide_video"
        fun getImpl():GuidePreference{
            return Holder.instance
        }
    }


    override fun initFileName(): String {
        return FILE_NAME
    }

    override fun initPreferenceChanges(version: Int) {
    }

    private object Holder{
        val instance = GuidePreference()
    }
}