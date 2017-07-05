package com.test.kotlin.other

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kotlin.R
import kotlinx.android.synthetic.main.fragment_kotlin.*

/**
 * Created by zenghao on 2017/5/25.
 */
class KotlinFragment3 : Fragment(){


    companion object{
        val KEY_TEXT: String = "text"
        fun getInstance(tex: String): KotlinFragment3{

            val bundle = Bundle()
            bundle.putString(KEY_TEXT,tex)

            val fragment = KotlinFragment3()

            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_kotlin,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val bun =  arguments
        pagerNumber.text = bun.getString(KEY_TEXT)
        pagerNumber.setOnClickListener {
                    OtherDetailActivity.launch(this@KotlinFragment3.activity)
        }
    }
}