package com.example.myappsql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class MyDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "MyDatabase.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                email TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun insertUser(name: String, email: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("email", email)
        }
        db.insert("users", null, values)
    }

    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users", null)
        if (cursor.moveToFirst()) {
            do {
                val user = User(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                )
                users.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return users
    }

    fun deleteUser(id: Int) {
        val db = writableDatabase
        db.delete("users", "id = ?", arrayOf(id.toString()))
    }

    fun deleteAllusers(){
        val db = writableDatabase
        db.delete("users", null, null)
    }

    fun updateUser(user: User) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", user.name)
            put("email", user.email)
        }
        db.update("users", values, "id = ?", arrayOf(user.id.toString()))
    }

    fun deleteUserById(id: Int) {
        val db = writableDatabase
        db.delete("users", "id = ?", arrayOf(id.toString()))
    }


}