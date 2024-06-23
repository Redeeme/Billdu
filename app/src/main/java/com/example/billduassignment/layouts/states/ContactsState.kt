package com.example.billduassignment.layouts.states

import androidx.compose.foundation.text.input.TextFieldState
import com.example.billduassignment.database.Contact

data class ContactsState(
    val contactsList: List<Contact> = emptyList(),

    val addContactDialogState: AddNewContactState? = null,
    val editContactsDialogState: EditContactState? = null,

    val showLanguageChangeDialog: Boolean? = null,

    var contactsScreenSearchedText: TextFieldState = TextFieldState(),


    )
