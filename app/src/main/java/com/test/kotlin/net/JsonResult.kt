package com.test.kotlin.net

/**
 * Created by zenghao on 2017/6/6.
 */
class JsonResult<T>(val status: Int , val msg: String, val data: T) {
}