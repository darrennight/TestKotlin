package com.test.kotlin.test

import com.test.kotlin.getCalendar
import com.test.kotlin.getMaxMonthCount
import com.test.kotlin.getToDay
import java.util.*



/**
 * Created by zenghao on 2017/7/12.
 */
class CalendarMonthModel {

     var monthDayCount: Int = 0//这个月多少天
     var monthCalendar: Calendar? = null
     var dayOffset: Int = 0
     var days: ArrayList<CalendarDayModel>? = null
     var hasSelectedStartAndEnd: Boolean = false//是否同时选中首尾
     var month: Int = 0
     var year: Int = 0

    constructor(year:Int,month:Int):this(getMyCalendar(year,month,1))

    constructor(monthCalendar:Calendar){
        this.monthCalendar = monthCalendar
        this.year = monthCalendar.get(Calendar.YEAR)
        this.month = monthCalendar.get(Calendar.MONTH)
        dayOffset = findDayOffset()
        val toDay = getToDay()
        monthDayCount = getMaxMonthCount(monthCalendar)//这个月多少天
        days = ArrayList<CalendarDayModel>(monthDayCount)
        for (i in 1..monthDayCount) {
            val dayCalendar = getCalendar(year, month, i)
            val day = CalendarDayModel(i, dayCalendar)
            if (toDay.equals(dayCalendar)) {
                day.isToday = true
            } else if (toDay.after(dayCalendar)) {
                day.isInThePast = true
            } else {
                day.isInTheFuture = true
            }
            days?.add(day)
        }
    }


    private fun findDayOffset():Int{
        var i = this.monthCalendar?.get(Calendar.DAY_OF_WEEK)
        val firstDayOfWeek = this.monthCalendar?.firstDayOfWeek
        if ( firstDayOfWeek?.compareTo(i!!)!! > 0) {
            i = i?.plus(7)
        }
        return i?.minus(firstDayOfWeek)!!
    }

    fun getDayModel(day:Int):CalendarDayModel?{
        if(day <= 0 || day.compareTo(days?.size!!)>0){
            return null
        }else{
            return days?.get(day -1)
        }
    }

    fun getMonthText():String{
        return year.toString()+"年"+(month -1).toString()+"月"
    }


    companion object{
        fun getMyCalendar(year:Int,month:Int,day:Int):Calendar{
            val calendar = Calendar.getInstance()
            calendar.clear()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            return calendar
        }
    }


}