package com.example.mindsettle.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mindsettle.Model.Person

class DBHelper(context:Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {
    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "EDMT.db"

        //Table
        private val TABLE_NAME = "Person"
        private val COL_ID = "Id"
        private val COL_USERNAME = "Username"
        private val COL_EMAIL = "Email"
        private val COL_PASSWORD = "Password"
        private val COL_BIRTHYEAR = "BirthYear"
        private val COL_COUNTRY = "Country"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY: String =
            ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY,$COL_USERNAME TEXT,$COL_EMAIL TEXT,$COL_PASSWORD TEXT,$COL_BIRTHYEAR INTEGER,$COL_COUNTRY TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    //CRUD
    val allPerson: List<Person>
        get() {
            val lstPersons = ArrayList<Person>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val person = Person()
                    person.userName = cursor.getString(cursor.getColumnIndex(COL_USERNAME))
                    person.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                    person.password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
                    person.birthYear = cursor.getInt(cursor.getColumnIndex(COL_BIRTHYEAR))
                    person.country = cursor.getString(cursor.getColumnIndex(COL_COUNTRY))

                    lstPersons.add(person)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstPersons
        }

    fun addPerson(person:Person){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME,person.userName)
        values.put(COL_EMAIL,person.email)
        values.put(COL_PASSWORD,person.password)
        values.put(COL_BIRTHYEAR,person.birthYear)
        values.put(COL_COUNTRY,person.country)

        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun updatePerson(person:Person):Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME,person.userName)
        values.put(COL_EMAIL,person.email)
        values.put(COL_PASSWORD,person.password)
        values.put(COL_BIRTHYEAR,person.birthYear)
        values.put(COL_COUNTRY,person.country)

        return db.update(TABLE_NAME, values,"$COL_ID=?", arrayOf(person.userName.toString()))
    }

    fun deletePerson(person:Person){
        val db = this.writableDatabase

        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(person.userName.toString()))
        db.close()
    }
}
