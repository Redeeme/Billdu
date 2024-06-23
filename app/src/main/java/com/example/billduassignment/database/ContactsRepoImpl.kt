package com.example.billduassignment.database

class ContactsRepoImpl(
    private val contactsDao: ContactsDao
) : ContactsRepository {
    override suspend fun getAll(): List<Contact> = contactsDao.getAll()

    override suspend fun insert(user: Contact) = contactsDao.insert(user)

    override suspend fun delete(id: Int) = contactsDao.deleteById(id)

    override suspend fun update(contact: Contact) = contactsDao.updateContact(contact)
}