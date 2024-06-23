package com.example.billduassignment

import android.content.Context
import androidx.room.Room
import com.example.billduassignment.database.ContactsDao
import com.example.billduassignment.database.ContactsRepoImpl
import com.example.billduassignment.database.ContactsRepository
import com.example.billduassignment.database.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MyDatabase {
        return Room
            .databaseBuilder(
                context,
                MyDatabase::class.java,
                MyDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideContactsDao(
        myDatabase: MyDatabase
    ): ContactsDao = myDatabase.contactsDao()

    @Provides
    fun provideContactsRepository(
        contactsDao: ContactsDao
    ): ContactsRepository = ContactsRepoImpl(contactsDao)
}