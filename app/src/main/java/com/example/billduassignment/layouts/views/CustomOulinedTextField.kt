package com.example.billduassignment.layouts.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomOutlinedTextField(
    state: TextFieldState,
    label: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(4.dp))
            .padding(16.dp)
    ) {
        if (state.text.isEmpty()) {
            Text(
                text = label,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        BasicTextField(
            state = state,
            textStyle = TextStyle(fontSize = 14.sp, color = Color.White),
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier.fillMaxWidth()
        )
    }
}