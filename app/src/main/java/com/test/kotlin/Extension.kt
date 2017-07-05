package com.test.kotlin

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import okhttp3.Request
import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.speech.RecognizerIntent
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.annotations.NotNull

/**
 * 拓展
 * Created by zenghao on 2017/6/5.
 */


fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun View.snackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this,message,duration).show()
}

fun View.snackbar(messageRes: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this,messageRes,duration).show()
}


fun getHtml(url: String): String {
    val client = OkClient.instance()
    val request = Request.Builder()
            .url(url)
            .build()

    val response = client.newCall(request).execute()
    return response.body().toString()
}

fun WebView.load(html: String) {
    this.loadDataWithBaseURL("http://ishuhui.net/", html, "text/html", "charset=utf-8", null)
}





/**
 * Created by liuzipeng on 2017/2/15.
 */

val View.ctx: Context
    get() = context

fun Activity.showSnackBar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, msg, time).show()
}

fun Activity.showSnackBar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT, actionMsg: String = "重试", action: (View) -> Unit) {
    Snackbar.make(view, msg, time).setAction(actionMsg, View.OnClickListener { action.invoke(view) }).show()
}

fun Fragment.showSnackBar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, msg, time).show()
}

fun Fragment.showSnackBar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT, actionMsg: String = "重试", action: (View) -> Unit) {
    Snackbar.make(view, msg, time).setAction(actionMsg, View.OnClickListener { action.invoke(view) }).show()
}

fun Any.log(msg: String?) {
    Log.d(this.javaClass.simpleName, msg)
}

fun Any.toast(msg: String?, length: Int? = Toast.LENGTH_SHORT) {
    Toast.makeText(App.instance, msg!!, length!!).show()
}

fun Any.toast(msg: Int?, length: Int? = Toast.LENGTH_SHORT) {
    Toast.makeText(App.instance, msg!!, length!!).show()
}



/**
 * 显示错误图片
 */
fun Activity.showErrorImg(@NotNull viewGroup: ViewGroup, @NotNull msg: String, imgResID: Int = R.mipmap.ic_launcher) {
//    viewGroup.visibility = View.VISIBLE
//    viewGroup.find<ImageView>(R.id.mErrorImg).setImageResource(imgResID)
//    viewGroup.find<TextView>(R.id.mErrorText).text = msg
}

/**
 * 隐藏错无图片
 */
fun Fragment.hideErrorImg(@NotNull viewGroup: ViewGroup) {
    viewGroup.visibility = View.GONE
}

/**
 * 隐藏错无图片
 */
fun Activity.hideErrorImg(@NotNull viewGroup: ViewGroup) {
    viewGroup.visibility = View.GONE
}


