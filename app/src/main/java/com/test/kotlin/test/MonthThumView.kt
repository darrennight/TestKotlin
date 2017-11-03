package com.test.kotlin.test

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.test.kotlin.R
import com.test.kotlin.getCalendar
import java.util.*


/**
 * Created by zenghao on 2017/7/12.
 */
class MonthThumView: View{






    private var selectedCircleColor: Int = 0
    private var unavailableDayTextColor: Int = 0
    private var dayTextColor: Int = 0
    private var selectedDayTextColor: Int = 0
    private var monthTextColor: Int = 0
    private var dayTextSize: Float = 0f
    private var monthTextSize: Float = 0f

    /**能选择点击day */
    private var selectedCirclePaint: Paint? = null
    /**不能选择点击day */
    private var unavailableDayPaint: Paint? = null
    private var dayPaint: Paint? = null
    private var selectedDayPaint: Paint? = null
    private var monthPaint: Paint? = null
    private var todayCirclePaint: Paint? = null//今日下面的小圆点
    private var todayCircleRadius: Int = 0
     var mCalendarMonthModel: CalendarMonthModel? = getdata()
    /**itemDay的高度*/
    private var itemHeight: Float = 0f
    /**itemDay的宽度*/
    private var itemWidth: Float = 0f
    private var monthHeight: Float = 0f
    private var offsetRowHeight = 15f

///////////////////////

    var mDatas = ArrayList<Float>()
    var mColors = ArrayList<Int>(4)
    var mPaint: Paint = Paint()

    fun initPaint() {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.color = 0xff44b391.toInt()
    }

    constructor(context: Context?) : super(context){
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initAtt(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initAtt(attrs)
    }


    fun initAtt(attrs: AttributeSet?){
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.MonthTHumView)

        selectedCircleColor = typedArray.getColor(R.styleable.MonthTHumView_selectedCircleColor, Color.BLACK)
        selectedDayTextColor = typedArray.getColor(R.styleable.MonthTHumView_selectedDayTextColor, Color.WHITE)
        dayTextColor = typedArray.getColor(R.styleable.MonthTHumView_dayTextColor, Color.BLACK)
        monthTextColor = typedArray.getColor(R.styleable.MonthTHumView_monthTextColor, Color.BLACK)
        unavailableDayTextColor = typedArray.getColor(R.styleable.MonthTHumView_unavailableDayTextColor, Color.GRAY)
        dayTextSize = typedArray.getDimension(R.styleable.MonthTHumView_dayTextSize, 40f)
        monthTextSize = typedArray.getDimension(R.styleable.MonthTHumView_monthTextSize, 40f)
        typedArray.recycle()

        selectedCirclePaint = Paint()
        selectedCirclePaint?.isAntiAlias = true
        selectedCirclePaint?.color = (selectedCircleColor)
        selectedCirclePaint?.textAlign = (Paint.Align.CENTER)
        selectedCirclePaint?.style = (Paint.Style.FILL)


        dayPaint = Paint(selectedCirclePaint)
        dayPaint?.color = (dayTextColor)
        dayPaint?.textSize = (dayTextSize)

        unavailableDayPaint = Paint(dayPaint)
        unavailableDayPaint?.color = (unavailableDayTextColor)

        selectedDayPaint = Paint(dayPaint)
        selectedDayPaint?.color = (selectedDayTextColor)

        monthPaint = Paint(dayPaint)
        monthPaint?.color = (monthTextColor)
        monthPaint?.textSize = (monthTextSize)
        monthPaint?.textAlign = (Paint.Align.LEFT)

        todayCirclePaint = Paint(dayPaint)
        todayCircleRadius = 4//今日小圆点
    }

    //长宽一致
    /*override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val mLayoutSize = Math.min(widthSpecSize, heightSpecSize)
        setMeasuredDimension(mLayoutSize, mLayoutSize)
    }*/

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        if(itemHeight == 0f){
            //itemDay的宽度 直接多少dp
            itemHeight = (width - paddingLeft - paddingRight )/7f
            itemWidth = itemHeight
            monthHeight = itemHeight*3/4
        }
        var heigh = (monthHeight + 5 * itemHeight + offsetRowHeight * 4)
        if(mCalendarMonthModel != null){
            var itemCount = mCalendarMonthModel?.days?.size?.plus(mCalendarMonthModel?.dayOffset!!)
            var temp = itemCount?.div(7f)
            if(temp?.compareTo(5)!! > 0){
                heigh += itemHeight + offsetRowHeight

            }
        }else{
            heigh = 0f
        }
        heigh+=paddingTop + paddingBottom
        //height
        setMeasuredDimension(width,heigh.toInt())
    }


    /**
     * 设置数据
     */
    fun setData(data: ArrayList<Float>, colors: ArrayList<Int>) {
        mDatas = data
        mColors = colors
        invalidate()
    }

    /*override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mDatas.size == 0) {
            return
        }

        //切掉圆心
        var mPath  = Path()
        mPath.addCircle(width / 2f, height / 2f, width / 2f * 0.4f, Path.Direction.CW)
        mPath.close()
        canvas?.clipPath(mPath, Region.Op.XOR)

        var total = 0f
        //此处亮点
        mDatas.forEach { total += it }
        var rf = RectF(0f, 0f, width.toFloat(), height.toFloat())
        var startAngle = -90f//起点
        var i = 0
        mDatas.forEach {
            mPaint.color = mColors[i]
            var sweepAngle = it * 360 / total
            canvas?.drawArc(rf, startAngle, sweepAngle, true, mPaint)
            startAngle += sweepAngle
            i++
        }

    }*/


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawMonthTitle(canvas!!)
        drawMonthDay(canvas)
    }

    fun drawMonthTitle(canvas: Canvas){
        if (mCalendarMonthModel == null) {
            return
        }
        val monthText = mCalendarMonthModel?.getMonthText()
        val textBounds = Rect()
        monthPaint?.getTextBounds("22", 0, 2, textBounds)//22 test width
        val font = monthPaint?.getFontMetrics()
        val baseLine = (0.5 * monthHeight - 0.5 * (font!!.ascent + font.descent)).toFloat()
        canvas.drawText(monthText, itemWidth / 2 - textBounds.exactCenterX(),
                paddingTop + baseLine, monthPaint)
    }

    private fun drawMonthDay(canvas: Canvas){
        if(mCalendarMonthModel == null){
            return
        }

        var days = mCalendarMonthModel?.days
        var h = monthHeight + itemHeight.div(2) + paddingTop
        var dayOffset = mCalendarMonthModel?.dayOffset

        for (i in 0..(days?.size!!-1)){
            var day = days.get(i)
            var w = itemWidth.div(2)*(dayOffset?.times(2)!!+1)

            var dayText = day.day.toString()
            var textBounds = Rect()
            var dPaint = dayPaint

            /**位置坐标*/
            var left = w - itemWidth.div(2) + paddingLeft
            var top = h - itemHeight.div(2)
            var bottom = h + itemHeight.div(2)
            var right = w + itemWidth.div(2) + paddingLeft

            if(day.isUnavailable){
                /**不可点击*/
                dPaint = unavailableDayPaint
            }else if(day.isSelected()){
                /**可以点击*/
                dPaint = selectedDayPaint
                if(mCalendarMonthModel!!.hasSelectedStartAndEnd){
                    var isDrawLeft = false
                    var isDrawRight = false
                    if(dayOffset == 0 && !day.isSelectedStartDay){
                        isDrawLeft = true
                    }else if(dayOffset == 6 && !day.isSelectedEndDay){
                        isDrawRight = true
                    }
                    if(i == 0 && !day.isSelectedStartDay){
                        isDrawLeft = true
                    }else if(i == (days.size -1) && !day.isSelectedEndDay){
                        isDrawRight = true
                    }

                    if(isDrawLeft){
                        canvas.drawRect(0f, top, right - itemWidth, bottom, selectedCirclePaint)
                    }
                    if(isDrawRight){
                        canvas.drawRect(left + itemWidth, top,measuredWidth.toFloat(), bottom, selectedCirclePaint);
                    }
                }


                if(day.isBetweenStartAndEndSelected){
                    /**之间*/
                    canvas.drawRect(left, top, right, bottom, selectedCirclePaint)
                }else if (day.isSelectedStartDay) {
                    /**开始*/
                    canvas.drawCircle((left + right) / 2,
                            (top + bottom) / 2, itemHeight / 2, selectedCirclePaint);

                    if(!day.singleDay){
                        if (mCalendarMonthModel!!.hasSelectedStartAndEnd) {
                            canvas.drawRect((left + right) / 2, top, right, bottom, selectedCirclePaint);
                        }
                    }
                }else{
                    /**结束*/
                    canvas.drawCircle((left + right) / 2,
                            (top + bottom) / 2, itemHeight / 2, selectedCirclePaint);
                    if (mCalendarMonthModel!!.hasSelectedStartAndEnd) {
                        canvas.drawRect(left, top, (left + right) / 2, bottom, selectedCirclePaint);
                    }
                }

            }

            /**绘制txt*/
            dPaint?.color = Color.BLACK
            dPaint?.getTextBounds(dayText, 0, dayText.length, textBounds)
            canvas.drawText(dayText, w + paddingLeft, h - textBounds.exactCenterY(), dPaint)

            if(day.isToday){
                if(day.isSelected()){
                        todayCirclePaint?.color = this.selectedDayPaint!!.color
                }else{
                        todayCirclePaint?.color = dayPaint!!.color
                }
                canvas.drawCircle(w + paddingLeft, h + itemWidth / 4 + this.todayCircleRadius * 2,
                        this.todayCircleRadius.toFloat(), this.todayCirclePaint)

            }

            dayOffset++
            if (dayOffset == 7) {
                dayOffset = 0
                h += itemHeight + offsetRowHeight
            }
        }
    }


    fun sethModel(calendarMonthModel: CalendarMonthModel){
        this.mCalendarMonthModel = calendarMonthModel
        invalidate()
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