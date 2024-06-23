package com.example.billduassignment.layouts.states

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class EditContactState(
    val firstName: TextFieldState = TextFieldState(),
    val secondName: TextFieldState = TextFieldState(),
    val number: TextFieldState = TextFieldState(),
    var isFavorite: MutableState<Boolean> = mutableStateOf(false),
    var id: Int
)