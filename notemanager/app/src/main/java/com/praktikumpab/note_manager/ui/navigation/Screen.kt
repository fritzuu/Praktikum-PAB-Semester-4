package com.praktikumpab.note_manager.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Notes : Screen("notes")
    object Profile : Screen("profile")
    object Detail : Screen("detail/{noteId}") {
        fun createRoute(noteId: Int) = "detail/$noteId"
    }
    object AddEdit : Screen("add_edit?noteId={noteId}") {
        fun createRoute(noteId: Int? = null) = if (noteId != null) "add_edit?noteId=$noteId" else "add_edit"
    }
}
