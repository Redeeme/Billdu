package com.example.billduassignment.layouts.dialogs

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.billduassignment.R


@Composable
fun ChangeLanguageDialog(changeLanguage: (Context, String) -> Unit, closeDialog: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.select_language))
        Button(onClick = {
            changeLanguage(context, "en")
            closeDialog.invoke()
        }) {
            Text(text = stringResource(id = R.string.english),
                style = MaterialTheme.typography.bodyMedium)
        }
        Button(
            onClick = {
                changeLanguage(context, "sk")
                closeDialog.invoke()
            },
            modifier = Modifier.padding(bottom = 40.dp)
        ) {
            Text(text = stringResource(id = R.string.slovak),
                style = MaterialTheme.typography.bodyMedium)
        }
    }
}