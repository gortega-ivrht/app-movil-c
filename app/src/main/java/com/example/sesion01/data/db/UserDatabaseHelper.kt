package com.example.sesion01.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class UserDatabaseHelper (context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "user_database.db"
        private const val DATABASE_VERSION = 10
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
        Log.d("DatabaseHelper DB_VERSION oldVersion",oldVersion.toString())
        Log.d("DatabaseHelper DB_VERSION newVersion",newVersion.toString())

        if (oldVersion < newVersion){
            Log.d("DatabaseHelper onUpgrade","Agregando la columna email en la versión 2")
            //agregar nueva columna email
            db.execSQL("ALTER TABLE users ADD COLUMN email TEXT DEFAULT 'SIN EMAIl'")
            db.execSQL("ALTER TABLE users ADD COLUMN phone TEXT DEFAULT 'SIN PHONE'")

        }


    }

}
