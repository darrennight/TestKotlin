package com.test.kotlin.DB

/**
 * Created by zenghao on 2017/6/14.
 * 创建表的字段统一
 */
object PersonTable {
    val TABLE_NAME = "Person"
    val ID = "_id"
    val NAME = "name"
    val ADDRESS = "address"
    val COMPANY_ID = "companyId"
}

object CompanyTable{
    val TABLE_NAME = "Company"
    val ID = "_id"
    val NAME = "name"
    val ADDRESS = "address"
}