package com.example.billduassignment.database

interface ContactsRepository {
    suspend fun getAll(): List<Contact>

    suspend fun insert(user: Contact)

    suspend fun delete(id: Int)

    suspend fun update(contact: Contact)
}