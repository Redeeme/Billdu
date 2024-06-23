package com.example.billduassignment

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billduassignment.database.Contact
import com.example.billduassignment.database.ContactsRepository
import com.example.billduassignment.database.MySharedPreferences
import com.example.billduassignment.layouts.states.AddNewContactState
import com.example.billduassignment.layouts.states.ContactsState
import com.example.billduassignment.layouts.states.EditContactState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ContactsState())
    val state = _state.asStateFlow()

    init {
        getContacts()
    }

    private fun getContacts() {
        viewModelScope.launch {
            val data = contactsRepository.getAll()
            _state.update {
                it.copy(
                    contactsList = data
                )
            }
        }
    }

    fun showAddNewContactDialog() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    addContactDialogState = AddNewContactState(
                        firstName = TextFieldState(initialText = ""),
                        secondName = TextFieldState(initialText = ""),
                        number = TextFieldState(initialText = ""),
                        isFavorite = mutableStateOf(false),
                    )
                )
            }
        }
    }

    fun closeAddNewContactDialog() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    addContactDialogState = null
                )
            }
        }
    }

    fun showEditContactsDialogState(id: Int) {
        viewModelScope.launch {
            val contact = _state.value.contactsList.find { it.id == id }
            contact.let { contactToEdit ->
                _state.update {
                    it.copy(
                        editContactsDialogState = EditContactState(
                            firstName = TextFieldState(
                                initialText = contactToEdit?.firstName ?: ""
                            ),
                            secondName = TextFieldState(
                                initialText = contactToEdit?.secondName ?: ""
                            ),
                            number = TextFieldState(initialText = contactToEdit?.phoneNumber ?: ""),
                            isFavorite = mutableStateOf(contactToEdit?.isFavorite ?: false),
                            id = contactToEdit?.id ?: 0
                        )
                    )
                }
            }
        }
    }

    fun closeEditContactsDialogState() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    editContactsDialogState = null
                )
            }
        }
    }


    fun saveNewContact() {
        viewModelScope.launch {
            contactsRepository.insert(
                Contact(
                    firstName = state.value.addContactDialogState?.firstName?.text.toString(),
                    secondName = state.value.addContactDialogState?.secondName?.text.toString(),
                    phoneNumber = state.value.addContactDialogState?.number?.text.toString(),
                    isFavorite = state.value.addContactDialogState?.isFavorite?.value ?: false,
                )
            )
            getContacts()
            closeAddNewContactDialog()
        }
    }

    fun editContact() {
        viewModelScope.launch {
            contactsRepository.update(
                Contact(
                    firstName = state.value.editContactsDialogState?.firstName?.text.toString(),
                    secondName = state.value.editContactsDialogState?.secondName?.text.toString(),
                    phoneNumber = state.value.editContactsDialogState?.number?.text.toString(),
                    isFavorite = state.value.editContactsDialogState?.isFavorite?.value ?: false,
                    id = state.value.editContactsDialogState?.id ?: 0
                )
            )
            getContacts()
            closeEditContactsDialogState()
        }
    }

    fun deleteContact(id: Int) {
        viewModelScope.launch {
            contactsRepository.delete(id)
            getContacts()
        }
    }

    fun addToFavorites(id: Int) {
        viewModelScope.launch {
            val contact = _state.value.contactsList.find { it.id == id }
            contact.let { contactToEdit ->
                contactsRepository.update(
                    Contact(
                        firstName = contactToEdit?.firstName ?: "",
                        secondName = contactToEdit?.secondName ?: "",
                        phoneNumber = contactToEdit?.phoneNumber ?: "",
                        isFavorite = true,
                        id = contactToEdit?.id ?: 0

                    )
                )
            }
            getContacts()
        }
    }

    fun removeFromFavorites(id: Int) {
        viewModelScope.launch {
            val contact = _state.value.contactsList.find { it.id == id }
            contact.let { contactToEdit ->
                contactsRepository.update(
                    Contact(
                        firstName = contactToEdit?.firstName ?: "",
                        secondName = contactToEdit?.secondName ?: "",
                        phoneNumber = contactToEdit?.phoneNumber ?: "",
                        isFavorite = false,
                        id = contactToEdit?.id ?: 0

                    )
                )
            }
            getContacts()
        }
    }

    fun changeLanguage(context: Context, country: String) {
        viewModelScope.launch {
            val mySharedPreferences = MySharedPreferences(context)
            mySharedPreferences.setLang(country)
            updateLocale(context, Locale(country))
        }
    }

    fun updateLocale(context: Context, locale: Locale) {
        val config = Configuration(context.resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun showLanguageChangeDialog() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    showLanguageChangeDialog = true
                )
            }
        }
    }

    fun closeLanguageChangeDialog() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    showLanguageChangeDialog = null
                )
            }
        }
    }
}