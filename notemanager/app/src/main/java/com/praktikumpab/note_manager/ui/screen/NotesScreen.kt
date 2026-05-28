package com.praktikumpab.note_manager.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.praktikumpab.note_manager.model.Note
import com.praktikumpab.note_manager.ui.components.NoteCard
import com.praktikumpab.note_manager.ui.theme.IndigoPurpleGradient
import com.praktikumpab.note_manager.ui.viewmodel.NoteUiState
import com.praktikumpab.note_manager.ui.viewmodel.SortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    uiState: NoteUiState,
    onSearchQueryChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onSortOptionChange: (SortOption) -> Unit,
    onToggleViewMode: () -> Unit,
    onNoteClick: (Int) -> Unit,
    onAddNoteClick: () -> Unit,
    onPinClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit
) {
    val categories = remember { listOf("All", "Kuliah", "Tugas", "Ide", "Pribadi", "Organisasi", "Project") }
    var sortMenuExpanded by remember { mutableStateOf(false) }

    val pinnedNotes = uiState.notes.filter { it.isPinned }
    val otherNotes = uiState.notes.filter { !it.isPinned }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick,
                shape = CircleShape,
                containerColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(Brush.linearGradient(IndigoPurpleGradient)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Note",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            // Header Section
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Notes",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Row {
                        IconButton(onClick = onToggleViewMode) {
                            Icon(
                                imageVector = if (uiState.isGridView) Icons.Default.ViewList else Icons.Default.GridView,
                                contentDescription = "Toggle View Mode"
                            )
                        }
                        Box {
                            IconButton(onClick = { sortMenuExpanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.Sort,
                                    contentDescription = "Sort Options"
                                )
                            }
                            DropdownMenu(
                                expanded = sortMenuExpanded,
                                onDismissRequest = { sortMenuExpanded = false }
                            ) {
                                SortOption.values().forEach { option ->
                                    DropdownMenuItem(
                                        text = { 
                                            Text(
                                                text = when (option) {
                                                    SortOption.NEWEST -> "Terbaru"
                                                    SortOption.OLDEST -> "Terlama"
                                                    SortOption.FAVORITES -> "Favorit"
                                                    SortOption.DEADLINE_NEAR -> "Deadline Terdekat"
                                                },
                                                fontWeight = if (uiState.sortOption == option) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        onClick = {
                                            onSortOptionChange(option)
                                            sortMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Search Bar
                TextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    placeholder = { Text("Search notes...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Category Chips Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { category ->
                        FilterChip(
                            selected = uiState.selectedCategory == category,
                            onClick = { onCategoryChange(category) },
                            label = { Text(category) },
                            shape = RoundedCornerShape(12.dp),
                            border = null,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Notes Content
            if (uiState.notes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Notes,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No notes found",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                if (uiState.isGridView) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (pinnedNotes.isNotEmpty()) {
                            items(pinnedNotes, key = { "pinned_${it.id}" }) { note ->
                                NoteCard(
                                    note = note,
                                    isGridView = true,
                                    onClick = { onNoteClick(note.id) },
                                    onPinClick = { onPinClick(note.id) },
                                    onFavoriteClick = { onFavoriteClick(note.id) }
                                )
                            }
                        }
                        items(otherNotes, key = { "other_${it.id}" }) { note ->
                            NoteCard(
                                note = note,
                                    isGridView = true,
                                onClick = { onNoteClick(note.id) },
                                onPinClick = { onPinClick(note.id) },
                                onFavoriteClick = { onFavoriteClick(note.id) }
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (pinnedNotes.isNotEmpty()) {
                            item {
                                Text(
                                    text = "PINNED",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                            items(pinnedNotes, key = { "pinned_${it.id}" }) { note ->
                                NoteCard(
                                    note = note,
                                    isGridView = false,
                                    onClick = { onNoteClick(note.id) },
                                    onPinClick = { onPinClick(note.id) },
                                    onFavoriteClick = { onFavoriteClick(note.id) }
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "ALL NOTES",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                        }
                        items(otherNotes, key = { "other_${it.id}" }) { note ->
                            NoteCard(
                                note = note,
                                isGridView = false,
                                onClick = { onNoteClick(note.id) },
                                onPinClick = { onPinClick(note.id) },
                                onFavoriteClick = { onFavoriteClick(note.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
