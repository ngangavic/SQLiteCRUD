package com.example.sqlitecrud

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "MyDatabase"
        val TABLE_DETAILS = "details"
        val KEY_ID = "id"
        val KEY_NAME = "name"
        val KEY_EMAIL = "email"
        val KEY_AGE = "age"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE =
            (" CREATE TABLE " + TABLE_DETAILS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_AGE + " INTEGER " + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(" DROP TABLE IF EXISTS " + TABLE_DETAILS)
        onCreate(db)
    }

    fun insertData(data: DataModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, data.userId)
        contentValues.put(KEY_NAME, data.userName)
        contentValues.put(KEY_EMAIL, data.userEmail)
        contentValues.put(KEY_AGE, data.userAge)

        val success = db.insert(TABLE_DETAILS, null, contentValues)
        db.close()
        return success

    }

    fun updateData(data: DataModel):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, data.userId)
        contentValues.put(KEY_NAME, data.userName)
        contentValues.put(KEY_EMAIL,data.userEmail )
        contentValues.put(KEY_AGE, data.userAge)

        // Updating Row
        val success = db.update(TABLE_DETAILS, contentValues,"id="+data.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun deleteData(data: DataModel):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, data.userId)
        // Deleting Row
        val success = db.delete(TABLE_DETAILS,"id="+data.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun viewData():List<DataModel>{
        val empList:ArrayList<DataModel> = ArrayList<DataModel>()
        val selectQuery = "SELECT  * FROM $TABLE_DETAILS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var userName: String
        var userEmail: String
        var userAge: Int
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                userAge = cursor.getInt(cursor.getColumnIndex("age"))
                val emp= DataModel(userId = userId, userName = userName, userEmail = userEmail,userAge=userAge)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }


}