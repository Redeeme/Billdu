package com.example.billduassignment.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 2)
abstract class MyDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao

    companion object {
        const val DATABASE_NAME: String = "my_database"
    }
}