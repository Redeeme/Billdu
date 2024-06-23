package com.example.billduassignment.layouts.views

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.billduassignment.R
import com.example.billduassignment.database.Contact
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun ContactList(contacts: List<Contact>, openDetail: (Int) -> Unit, deleteContact: (Int) -> Unit, addToFavorites: (Int) -> Unit) {
    LazyColumn {
        items(contacts) { contact ->
            DraggableContactItem(
                contact = contact,
                onClick = openDetail,
                onSwipeLeft = deleteContact,
                onSwipeRight = addToFavorites
            )
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableContactItem(
    contact: Contact,
    onClick: (Int) -> Unit,
    onSwipeLeft: (Int) -> Unit,
    onSwipeRight: (Int) -> Unit
) {
    val density = LocalDensity.current
    val defaultActionSize = 40.dp
    val endActionSizePx = with(density) { (defaultActionSize * 2).toPx() }
    val startActionSizePx = with(density) { defaultActionSize.toPx() }
    val coroutineScope = rememberCoroutineScope()

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Center,
            anchors = DraggableAnchors {
                DragAnchors.Start at endActionSizePx // reversed anchor for start action
                DragAnchors.Center at 0f
                DragAnchors.End at -startActionSizePx // reversed anchor for end action
            },
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = exponentialDecay(),
        )
    }

    Box(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        // Background actions (always visible behind the draggable item)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(6f)
                    .background(Color.Red)
                    .clickable {
                        coroutineScope.launch {
                            onSwipeLeft(contact.id)
                            state.animateTo(DragAnchors.Center)
                        }
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    color = Color.White,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(4f)
                    .background(if (!contact.isFavorite) Color.Green else Color.Red)
                    .clickable {

                    },
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        onSwipeRight(contact.id)
                        state.animateTo(DragAnchors.Center)
                    }
                }) {
                    Icon(if (!contact.isFavorite) Icons.Default.Favorite else Icons.Default.Undo, contentDescription = "Settings")
                }
            }
        }

        // Draggable contact item
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .offset {
                    IntOffset(
                        x = state
                            .requireOffset()
                            .roundToInt(),
                        y = 0,
                    )
                }
                .anchoredDraggable(state, Orientation.Horizontal),
            content = {
                ContactItem(contact = contact, onClick = onClick)
            }
        )
    }
}


@Composable
fun ContactItem(
    contact: Contact,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.Gray)
            .clickable {
                onClick.invoke(contact.id)
            }
            .padding(vertical = 8.dp, horizontal = 8.dp),
    ) {
        Text(
            text = "${stringResource(id = R.string.name)}: ${contact.firstName} ${contact.secondName}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(text = "${stringResource(id = R.string.phone_number)}: ${contact.phoneNumber}", style = MaterialTheme.typography.bodyMedium)
    }
}


enum class DragAnchors {
    Start,
    Center,
    End,
}

