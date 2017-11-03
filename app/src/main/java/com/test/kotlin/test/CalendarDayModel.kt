package com.test.kotlin.test

import java.util.*

/**
 * Created by zenghao on 2017/7/12.
 */
class CalendarDayModel {

    var day:Int = 0
    /**过去*/
    var isInThePast:Boolean = false
    /**今天*/
    var isToday:Boolean = false
    /**将来*/
    var isInTheFuture:Boolean = false


    /**开始结束之间*/
    var isBetweenStartAndEndSelected:Boolean = false
    /**结束*/
    var isSelectedEndDay:Boolean = false
    /**开始*/
    var isSelectedStartDay:Boolean = false
    /**是否可以点击选择*/
    var isUnavailable:Boolean = false

    var dayCalendar: Calendar? = null
    /**单天*/
    var singleDay:Boolean = true

    constructor(day:Int,calendar:Calendar){
        this.day = day
        this.dayCalendar = calendar
    }

    fun unSelected(){
        isBetweenStartAndEndSelected = false
        isSelectedEndDay = false
        isSelectedStartDay = false
    }

    fun isSelected():Boolean{
        return (this.isSelectedStartDay || this.isSelectedEndDay || this.isBetweenStartAndEndSelected)
    }


}