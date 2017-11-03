package com.test.kotlin.BaseList.widget

import android.content.Context
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.test.kotlin.R
import kotlinx.android.synthetic.main.state_layout_empty.view.*
import kotlinx.android.synthetic.main.state_layout_error.view.*
import kotlinx.android.synthetic.main.state_test_empty.view.*

/**
 * Created by zenghao on 2017/7/25.
 */
class StateLayout:FrameLayout{

    /**空数据界面*/
    private var mEmptyImage:Int = -1
    private var mEmptyText:String? = null
    /**错误界面*/
    private var mErrorImage:Int = -1
    private var mErrorText:String? = null

    private var mRetryText:String?=null


    private var mEmptyResId:Int = View.NO_ID
    private var mLoadingResId:Int = View.NO_ID
    private var mErrorResId:Int = View.NO_ID
    private var mContentId:Int = View.NO_ID

    private var mLayouts = HashMap<Int,View>()

    private var mInflater:LayoutInflater? = null

    private var mRetryListener:View.OnClickListener? = null

    private val mRetryClickListener = object :OnClickListener{
        override fun onClick(v: View?) {
                mRetryListener?.onClick(v)
        }
    }

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init(attrs,defStyleAttr)
    }


    private fun init(attrs: AttributeSet?,defStyleAttr: Int){
                mInflater = LayoutInflater.from(context)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.StateLayout,defStyleAttr,R.style.StateLayout_Style)
        mEmptyImage = ta.getResourceId(R.styleable.StateLayout_slEmptyImage,NO_ID)
        mEmptyText = ta.getString(R.styleable.StateLayout_slEmptyText)

        mErrorImage = ta.getResourceId(R.styleable.StateLayout_slErrorImage,NO_ID)
        mErrorText = ta.getString(R.styleable.StateLayout_slErrorText)

        mRetryText = ta.getString(R.styleable.StateLayout_slRetryText)


        mEmptyResId = ta.getResourceId(R.styleable.StateLayout_slEmptyResId,R.layout.state_layout_empty)
        mLoadingResId = ta.getResourceId(R.styleable.StateLayout_slLoadingResId,R.layout.state_layout_loading)
        mErrorResId = ta.getResourceId(R.styleable.StateLayout_slErrorResId,R.layout.state_layout_error)

        ta.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if(childCount == 0){
            return
        }

        if(childCount > 1){
            removeViews(1,childCount -1 )
        }

        val view = getChildAt(0)
        setContentView(view)
        showLoading()

    }

    private fun setContentView(view:View){
        mContentId = view.id
        mLayouts.put(mContentId,view)
    }

    fun setLoading(layoutId:Int):StateLayout{
        if(mLoadingResId != layoutId ){
            remove(mLoadingResId)
            mLoadingResId = layoutId
        }
        return this
    }

    fun setEmpty(layoutId: Int):StateLayout{
        if(mEmptyResId != layoutId){
            remove(mEmptyResId)
            mEmptyResId = layoutId
        }
        return this
    }

    fun setError(layoutId:Int):StateLayout{
        if(mErrorResId != layoutId){
            remove(mErrorResId)
            mErrorResId = layoutId
        }
        return this
    }

    fun setEmptyImage(resid:Int):StateLayout{
        mEmptyImage = resid
        image(mEmptyResId,R.id.empty_image,mEmptyImage)
        return this
    }

    fun setEmptyText(text:String):StateLayout{
        mEmptyText = text
        text(mEmptyResId,R.id.empty_text,mEmptyText!!)
        return this
    }

    fun setErrorImage(resId: Int):StateLayout{
        mErrorImage = resId
        image(mErrorResId,R.id.error_image,mErrorImage)
        return this
    }

    fun setErrorText(value:String):StateLayout{
        mErrorText = value
        text(mErrorResId,R.id.error_text,mErrorText!!)
        return this
    }

    fun setReTryText(text: String):StateLayout{
        mRetryText = text
        text(mErrorResId,R.id.retry_button,mRetryText!!)
        return this
    }

    fun setRetryListener(listener:OnClickListener):StateLayout{
        mRetryListener = listener
        return this
    }


    fun showLoading(){
        show(mLoadingResId)
    }

    fun showEmpty(){
        show(mEmptyResId)
    }

    fun showError(){
        show(mErrorResId)
    }

    fun showContent(){
        show(mContentId)
    }

    private fun show(layoutId:Int){
        for (view in mLayouts.values){
            view.visibility = GONE
        }
        layout(layoutId)?.visibility = VISIBLE
    }


    private fun remove(layoutId:Int){
        if(mLayouts.containsKey(layoutId)){
            val vg = mLayouts.remove(layoutId)
            removeView(vg)
        }
    }

    private fun layout(layoutId: Int):View?{
        if(mLayouts.containsKey(layoutId)){
            return mLayouts[layoutId]
        }
        val layout = mInflater?.inflate(layoutId,this,false)
        layout?.visibility = GONE
        addView(layout)
        mLayouts.put(layoutId,layout!!)

        when(layoutId){
            mEmptyResId ->{
                //val img = layout.findViewById(R.id.empty_image) as ImageView
                empty_image?.let {
                    if(mEmptyImage!=null){
                        empty_image.setImageResource(mEmptyImage)
                    }
                }

                empty_text?.let {
                    empty_text.text = mEmptyText
                    //view.setTextColor()
                    //view.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
                }

                empty_btn?.let {
                    empty_btn.setOnClickListener(mRetryClickListener)
                }
            }

            mErrorResId ->{

                error_image?.let {
                    error_image.setImageResource(mErrorImage)
                }
                error_text?.let {
                    error_text.text = mErrorText
                }

                //重试按钮 init setlistener
                error_text.setOnClickListener(mRetryClickListener)
            }

            mLoadingResId ->{
                //val pb = layout.findViewById(R.id.loading_image) as ProgressBar
            }
            else ->{

            }

        }

        return layout
    }

    private fun text(layoutId: Int,ctrlId: Int,value:String){
            if(mLayouts.containsKey(layoutId)){
                val view = mLayouts[layoutId]?.findViewById(ctrlId) as TextView
                view?.let {
                    view.text = value
                    view.gravity = Gravity.CENTER
                }
            }
    }


    private fun image(layoutId:Int,ctrlId:Int,resid: Int){
        if (mLayouts.containsKey(layoutId)){
            val view = mLayouts[layoutId]?.findViewById(ctrlId) as ImageView
            view?.let {
                if(resid != View.NO_ID){
                    view.setImageResource(resid)
                }

            }
        }
    }

}