package com.example.billduassignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contacts_table")
    suspend fun getAll(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: Contact)

    @Query("DELETE FROM contacts_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Update
    suspend fun updateContact(contact: Contact)
}