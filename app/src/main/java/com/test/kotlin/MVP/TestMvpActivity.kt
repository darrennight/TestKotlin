package com.test.kotlin.MVP

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.test.kotlin.MVP.Model.TestModel
import com.test.kotlin.MVP.presenter.TestPresenter
import com.test.kotlin.MVP.view.TestView
import com.test.kotlin.R
import com.test.kotlin.getCalendar
import com.test.kotlin.net.IndexData
import com.test.kotlin.test.CalendarMonthModel
import com.test.kotlin.toast
import kotlinx.android.synthetic.main.activity_test_mvp.*
import java.util.*

/**
 * Created by zenghao on 2017/6/6.
 */
class TestMvpActivity : AppCompatActivity(),TestView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_mvp)
        var model = TestModel()
        var present = TestPresenter(this@TestMvpActivity,model)
        present.getIndexData()

        mv_thum_monthview.sethModel(getdata())


        var mDatas = ArrayList<Float>()
        mDatas.add(1f)
        mDatas.add(2f)
        mDatas.add(4f)
        mDatas.add(2f)
        var mColors = ArrayList<Int>()
        mColors.add(0xff83ccd2.toInt())
        mColors.add(0xffc0e1ce.toInt())
        mColors.add(0xfffac55e.toInt())
        mColors.add(0xffef805f.toInt())
        ppCircle.setData(mDatas,mColors)

//        mv_thum_monthview.setData(mDatas,mColors)
    }

    override fun showList(index: IndexData?) {
        if(index!=null){
            toast("hahaha")
            tv_index.setText(index.date_time)
        }
    }


    private fun getdata(): CalendarMonthModel {

        var years = ArrayList<Calendar>()
        var cal = Calendar.getInstance()
        years.add(getCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1))

        var monthModel = CalendarMonthModel(years.get(0))

        monthModel.getDayModel(1)?.isSelectedStartDay = true
        monthModel.getDayModel(1)?.singleDay = true

        monthModel.getDayModel(2)?.isSelectedStartDay = true
        monthModel.getDayModel(3)?.isSelectedEndDay = true

        monthModel.getDayModel(4)?.isSelectedStartDay = true

        monthModel.hasSelectedStartAndEnd = (true)

        monthModel.getDayModel(6)?.isSelectedStartDay = true
        monthModel.getDayModel(7)?.isBetweenStartAndEndSelected = true
        monthModel.getDayModel(8)?.isBetweenStartAndEndSelected = true
        monthModel.getDayModel(9)?.isBetweenStartAndEndSelected = true
        monthModel.getDayModel(10)?.isSelectedEndDay = true

        return monthModel
    }
}