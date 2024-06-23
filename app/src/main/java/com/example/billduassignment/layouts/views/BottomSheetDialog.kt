package com.example.billduassignment.layouts.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.Ref
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> BottomSheetDialog(
    value: T?,
    dismiss: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    content: @Composable (T) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    ValueVisibility(value = value) { safeValue ->
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    dismiss()
                }
            },
            shape = RoundedCornerShape(0.dp),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 8.dp)
                        .width(40.dp)
                        .height(4.dp)
                )
            },
            modifier = modifier
        ) {
            content(safeValue)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
inline fun <T> ValueVisibility(
    value: T?,
    crossinline content: @Composable (T) -> Unit,
) {
    val ref = remember {
        Ref<T>()
    }

    ref.value = value ?: ref.value

    if (value != null) {
        ref.value?.let { value ->
            content(value)
        }
    }
}
