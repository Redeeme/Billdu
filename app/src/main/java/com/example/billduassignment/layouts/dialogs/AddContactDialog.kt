package com.example.billduassignment.layouts.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.billduassignment.R
import com.example.billduassignment.layouts.states.AddNewContactState
import com.example.billduassignment.layouts.views.CustomCheckbox
import com.example.billduassignment.layouts.views.CustomOutlinedTextField


@Composable
fun AddContactDialog(addNewContactState: AddNewContactState, onAddNewContact: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.add_contact))
        CustomOutlinedTextField(
            addNewContactState.firstName,
            stringResource(id = R.string.first_name)
        )
        CustomOutlinedTextField(
            addNewContactState.secondName,
            stringResource(id = R.string.second_name)
        )
        CustomOutlinedTextField(
            addNewContactState.number,
            stringResource(id = R.string.phone_number)
        )
        CustomCheckbox(
            stringResource(id = R.string.is_favorite),
            addNewContactState.isFavorite.value,
            { addNewContactState.isFavorite.value = it })
        Button(
            onClick = { onAddNewContact.invoke() },
            modifier = Modifier.padding(bottom = 30.dp),
            content = {
                Text(text = stringResource(id = R.string.save),
                    style = MaterialTheme.typography.bodyMedium)
            })
    }
}