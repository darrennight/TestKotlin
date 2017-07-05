package com.test.kotlin.DB.frame

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.text.TextUtils
import android.util.SparseArray

/**
 * Created by zenghao on 2017/6/30.
 */
abstract class BaseContentProvider(var authority: String) :ContentProvider(){

    private var dataBaseHelper:DataBaseHelper? = null
    protected var allTableHelper:HashMap<String,BaseTableHelper>?= null
    protected var codeType:SparseArray<String>? = null
    protected var codeTBHelper:SparseArray<BaseTableHelper>? = null

    init {
        allTableHelper = createAllTableHelper()
        initUriMatcher(authority, allTableHelper)
    }

    companion object{

       var uriMatcher:UriMatcher? = null

        fun getContentAuthoritySlash(authority: String): String{
            return "content:// $authority/"
        }
    }

    protected abstract fun getDataBaseName():String
    protected abstract fun getDataBaseVersion():Int

    protected abstract fun createAllTableHelper():HashMap<String,BaseTableHelper>

    fun getContentAuthoritySlash(): String{
        return getContentAuthoritySlash(authority)
    }

    private fun initUriMatcher(authority: String,tableHelpers:HashMap<String,BaseTableHelper>?){
        if (TextUtils.isEmpty(authority) || tableHelpers == null || tableHelpers.isEmpty()){
            return
        }

        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        codeType = SparseArray()
        codeTBHelper = SparseArray()

        var code = 0
        for (item in tableHelpers){
            var keyName = item.key
            var tableHelperValue = item.value

            codeType!!.put(code,tableHelperValue.getContentType(authority))
            codeTBHelper!!.put(code,tableHelperValue)
            uriMatcher!!.addURI(authority,keyName,code++)


            codeType!!.put(code,tableHelperValue.getEntryContentType(authority))
            codeTBHelper!!.put(code,tableHelperValue)
            uriMatcher!!.addURI(authority,"$keyName/#",code++)
        }

    }

    override fun onCreate(): Boolean {
        dataBaseHelper = DataBaseHelper(context)
        return dataBaseHelper != null
    }

    override fun getType(uri: Uri?): String {
        return codeType!!.get(uriMatcher!!.match(uri))
    }


    protected fun getReadableDatabase():SQLiteDatabase{
        return dataBaseHelper!!.readableDatabase
    }
    protected fun getWritableDatabase():SQLiteDatabase{
        return dataBaseHelper!!.writableDatabase
    }

    protected fun onDataBaseCreate(db:SQLiteDatabase){
        if (allTableHelper == null || allTableHelper!!.isEmpty()){
            return
        }
        for (item in allTableHelper!!){
            item.value.onDataBaseCreate(db)
        }
    }

    protected fun onDataBaseUpgrade(db: SQLiteDatabase,oldVersion: Int,newVersion: Int){
        if (allTableHelper == null || allTableHelper!!.isEmpty()){
            return
        }
        for (item in allTableHelper!!){
            item.value.onDataBaseUpgrade(oldVersion,newVersion,db!!)
        }
    }

    protected fun getEntrySelect(uri:Uri,primaryKey:Any):String{
        if (uri == null || primaryKey == null) {
            return ""
        }
        return "$primaryKey=${ContentUris.parseId(uri)}"
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        var orderBy:String
        var tableName:String
        var selectionTem:String = selection!!
        val matcher:Int = uriMatcher!!.match(uri)
        var tableHelper = codeTBHelper!!.get(matcher)
        val type:String = codeType!!.get(matcher)

        var cursor:Cursor
        if(tableHelper == null || type == null){
            throw IllegalArgumentException("Unknown Uri: $uri matcher$matcher")
        }

        tableName = tableHelper.getTableName()
        if(TextUtils.isEmpty(sortOrder)){
            orderBy = tableHelper.getDefaultSortOrder()
        }else{
            orderBy = sortOrder!!
        }

        if(type.equals(tableHelper.getEntryContentType(authority))){
         if(TextUtils.isEmpty(selectionTem)){
             selectionTem = getEntrySelect(uri!!,tableHelper.getPrimaryKey())
             }else{
             selectionTem = "$selectionTem AND ${getEntrySelect(uri!!,tableHelper.getPrimaryKey())}"
             }
        }

        var db = getReadableDatabase()
        cursor = db.query(tableName,projection,selection,selectionArgs,null,null,orderBy)
        cursor.setNotificationUri(context.contentResolver,uri!!)
        return cursor
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {

        var matcher = uriMatcher!!.match(uri!!)
        var initValues = if (values!=null) ContentValues(values) else ContentValues()
        var db = getWritableDatabase()
        var tableName:String
        var rowId:Long
        var tableHelper:BaseTableHelper = codeTBHelper!!.get(matcher)

        do {
            if (tableHelper == null) {
                break
            }

            tableName = tableHelper.getTableName()
            rowId = db.insert(tableName, null, initValues)

            if (rowId > 0) {
                var songUri = ContentUris.withAppendedId(tableHelper.getContentUri(authority), rowId);
                context.contentResolver.notifyChange(songUri, null)
                return songUri
            }

        } while (false)
         throw IllegalArgumentException("Failed to insert row into $uri")
    }


    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        var db = getWritableDatabase()
        var count:Int
        var initValues = if (values!=null) ContentValues(values) else ContentValues()
        var matcher = uriMatcher!!.match(uri)
        var tableHelper:BaseTableHelper = codeTBHelper!!.get(matcher)

        count = db.update(tableHelper.getTableName(),initValues,selection,selectionArgs)

        context.contentResolver.notifyChange(uri,null)
        return count
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
       var matcher = uriMatcher!!.match(uri)
        var db = getWritableDatabase()
        var count:Int
        var tableHelper = codeTBHelper!!.get(matcher)
        count = db.delete(tableHelper.getTableName(),selection,selectionArgs)
        context.contentResolver.notifyChange(uri,null)
        return  count
    }

    private inner class DataBaseHelper(context: Context) :SQLiteOpenHelper(context, this@BaseContentProvider.getDataBaseName(),null, this@BaseContentProvider.getDataBaseVersion()){

        override fun onCreate(db: SQLiteDatabase?) {
            onDataBaseCreate(db!!)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            onDataBaseUpgrade(db!!,oldVersion,newVersion)
        }

        override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            // 虽然没调用到，但要保留本函数
            // 4.0以上系统在数据库从高降到低时，会强制抛出异常，通过重写这个方法，可以解决问题
        }
    }
}