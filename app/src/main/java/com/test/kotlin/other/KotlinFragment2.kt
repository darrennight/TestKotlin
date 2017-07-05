package com.test.kotlin.other

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kotlin.R

/**
 * Created by zenghao on 2017/5/25.
 */
class KotlinFragment2 : Fragment(){


    companion object Instance{
        fun getInstance(): KotlinFragment2{
            return KotlinFragment2()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_kotlin,container,false)
    }
}