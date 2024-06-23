package com.example.billduassignment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.billduassignment.database.MySharedPreferences
import com.example.billduassignment.layouts.ContactsScreen
import com.example.billduassignment.layouts.FavoritesScreen
import com.example.billduassignment.layouts.dialogs.AddContactDialog
import com.example.billduassignment.layouts.dialogs.ChangeLanguageDialog
import com.example.billduassignment.layouts.dialogs.EditContactDialog
import com.example.billduassignment.layouts.navigation.Screen
import com.example.billduassignment.layouts.views.BottomSheetDialog
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCompose(
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by sharedViewModel.state.collectAsStateWithLifecycle()
    val prefs = MySharedPreferences(context)
    sharedViewModel.updateLocale(context,Locale(prefs.getLang()?:"en"))

    Box(modifier = Modifier) {
        Scaffold(
            topBar = { TopAppMainBar(sharedViewModel) },
            bottomBar = { BottomAppMainBar(navController = navHostController) }) {
            Surface(modifier = Modifier.padding(it)) {
                NavHost(
                    navController = navHostController,
                    startDestination = Screen.Contacts.route
                ) {
                    composable(route = Screen.Contacts.route) {
                        ContactsScreen(
                            state,
                            sharedViewModel::showEditContactsDialogState,
                            sharedViewModel::deleteContact,
                            sharedViewModel::addToFavorites
                        )
                    }
                    composable(route = Screen.Favorites.route) {
                        FavoritesScreen(
                            state,
                            sharedViewModel::showEditContactsDialogState,
                            sharedViewModel::deleteContact,
                            sharedViewModel::removeFromFavorites
                        )
                    }
                }
            }
            BottomSheetDialog(
                value = state.addContactDialogState,
                dismiss = sharedViewModel::closeAddNewContactDialog,
            ) { addContactDialogState ->
                AddContactDialog(addContactDialogState, sharedViewModel::saveNewContact)
            }
            BottomSheetDialog(
                value = state.editContactsDialogState,
                dismiss = sharedViewModel::closeEditContactsDialogState,
            ) { editContactsDialogState ->
                EditContactDialog(editContactsDialogState, sharedViewModel::editContact)
            }
            BottomSheetDialog(
                value = state.showLanguageChangeDialog,
                dismiss = sharedViewModel::closeLanguageChangeDialog,
            ) {
                ChangeLanguageDialog(
                    sharedViewModel::changeLanguage,
                    sharedViewModel::closeLanguageChangeDialog
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppMainBar(sharedViewModel: SharedViewModel) {
    TopAppBar(title = { TopBarTitleView() },
        actions = {
            IconButton(onClick = { sharedViewModel.showLanguageChangeDialog() }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
            IconButton(onClick = { sharedViewModel.showAddNewContactDialog() }) {
                Icon(Icons.Default.PersonAddAlt, contentDescription = "Settings")
            }
        })
}

@Composable
fun TopBarTitleView() {
    Text(text = stringResource(id = R.string.app_name),
        style = MaterialTheme.typography.labelLarge)
}

@Composable
fun BottomAppMainBar(navController: NavController) {
    NavigationBar {
        val currentBackStackEntry = navController.currentBackStackEntryAsState()
        NavigationBarItem(
            selected = Screen.Contacts.route == currentBackStackEntry.value?.destination?.route,
            onClick = { navController.navigate(Screen.Contacts.route) },
            icon = { Image(imageVector = Icons.Default.Contacts, contentDescription = "") })
        NavigationBarItem(
            selected = Screen.Favorites.route == currentBackStackEntry.value?.destination?.route,
            onClick = { navController.navigate(Screen.Favorites.route) },
            icon = { Image(imageVector = Icons.Default.Favorite, contentDescription = "") })
    }
}








