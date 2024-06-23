package com.example.billduassignment.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.billduassignment.layouts.states.ContactsState
import com.example.billduassignment.R
import com.example.billduassignment.layouts.views.ContactList
import com.example.billduassignment.layouts.views.CustomOutlinedTextField

@Composable
fun ContactsScreen(
    state: ContactsState,
    openDetail: (Int) -> Unit,
    deleteContact: (Int) -> Unit,
    addToFavorites: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CustomOutlinedTextField(
            state.contactsScreenSearchedText,
            stringResource(id = R.string.search)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ContactList(
            state.contactsList.filter {
                (it.firstName.contains(
                    state.contactsScreenSearchedText.text.toString(),
                    ignoreCase = true
                ) ||
                        it.secondName.contains(
                            state.contactsScreenSearchedText.text.toString(),
                            ignoreCase = true
                        ) ||
                        it.phoneNumber.contains(
                            state.contactsScreenSearchedText.text.toString(),
                            ignoreCase = true
                        )) && !it.isFavorite
            },
            openDetail,
            deleteContact,
            addToFavorites
        )
    }
}
