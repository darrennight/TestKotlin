package com.test.kotlin.DB

/**
 * Created by zenghao on 2017/6/14.
 */
import java.util.*

data class Company(val map: MutableMap<String, Any?>) {
    var _id: Long by map
    var name: String by map
    var address: String by map

    constructor() : this(HashMap()) {
    }

    constructor(id:Long,name: String,address:String) : this(HashMap()) {
        this._id = id
        this.name = name
        this.address = address
    }

}
