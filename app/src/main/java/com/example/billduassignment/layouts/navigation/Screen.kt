package com.example.billduassignment.layouts.navigation

sealed class Screen(val route: String) {
    object Contacts: Screen(route = "contacts_screen")
    object Favorites: Screen(route = "favorites_Screen")
}