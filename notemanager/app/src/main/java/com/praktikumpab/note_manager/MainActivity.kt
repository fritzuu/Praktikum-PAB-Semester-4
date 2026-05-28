package com.praktikumpab.note_manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.praktikumpab.note_manager.ui.navigation.Screen
import com.praktikumpab.note_manager.ui.screen.*
import com.praktikumpab.note_manager.ui.theme.NotemanagerTheme
import com.praktikumpab.note_manager.ui.viewmodel.NoteViewModel
import com.praktikumpab.note_manager.ui.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val themeMode by themeViewModel.themeMode.collectAsState()
            
            NotemanagerTheme(themeMode = themeMode) {
                NoteFlowApp(themeViewModel = themeViewModel)
            }
        }
    }
}

@Composable
fun NoteFlowApp(
    viewModel: NoteViewModel = viewModel(),
    themeViewModel: ThemeViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val uiState by viewModel.uiState.collectAsState()
    val themeMode by themeViewModel.themeMode.collectAsState()

    val navItems = listOf(
        Triple(Screen.Home, "Home", Icons.Default.Home),
        Triple(Screen.Notes, "Notes", Icons.AutoMirrored.Filled.List),
        Triple(Screen.Profile, "Profile", Icons.Default.Person)
    )

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = currentDestination?.route in listOf(Screen.Home.route, Screen.Notes.route, Screen.Profile.route),
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                NavigationBar(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 24.dp)
                        .clip(RoundedCornerShape(32.dp)),
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    tonalElevation = 8.dp
                ) {
                    navItems.forEach { (screen, label, icon) ->
                        val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            selected = selected,
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            ),
                            onClick = {
                                if (!selected) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    uiState = uiState,
                    onNavigateToNotes = { navController.navigate(Screen.Notes.route) },
                    onAddNoteClick = { navController.navigate(Screen.AddEdit.createRoute()) },
                    onNoteClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) }
                )
            }
            composable(Screen.Notes.route) {
                NotesScreen(
                    uiState = uiState,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    onCategoryChange = viewModel::onCategoryChange,
                    onSortOptionChange = viewModel::onSortOptionChange,
                    onToggleViewMode = viewModel::toggleViewMode,
                    onNoteClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                    onAddNoteClick = { navController.navigate(Screen.AddEdit.createRoute()) },
                    onPinClick = viewModel::togglePin,
                    onFavoriteClick = viewModel::toggleFavorite
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    uiState = uiState,
                    currentTheme = themeMode,
                    onThemeChange = themeViewModel::setThemeMode
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("noteId") { type = NavType.IntType })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId")
                val note = noteId?.let { viewModel.getNoteById(it) }
                DetailNoteScreen(
                    note = note,
                    onBack = { navController.popBackStack() },
                    onEdit = { id -> navController.navigate(Screen.AddEdit.createRoute(id)) },
                    onDelete = { id ->
                        viewModel.deleteNote(id)
                        navController.popBackStack()
                    },
                    onPinToggle = viewModel::togglePin,
                    onFavoriteToggle = viewModel::toggleFavorite
                )
            }
            composable(
                route = Screen.AddEdit.route,
                arguments = listOf(navArgument("noteId") { 
                    type = NavType.IntType
                    defaultValue = -1 
                })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId")
                val note = if (noteId != null && noteId != -1) viewModel.getNoteById(noteId) else null
                AddEditNoteScreen(
                    note = note,
                    onSave = { title, content, category, deadline ->
                        if (note == null) {
                            viewModel.addNote(title, content, category, deadline)
                        } else {
                            viewModel.updateNote(note.id, title, content, category, deadline)
                        }
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
