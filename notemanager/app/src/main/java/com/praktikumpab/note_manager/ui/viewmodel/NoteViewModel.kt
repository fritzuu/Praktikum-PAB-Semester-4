package com.praktikumpab.note_manager.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.praktikumpab.note_manager.data.NoteDatabase
import com.praktikumpab.note_manager.data.NoteRepository
import com.praktikumpab.note_manager.model.Note
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

enum class SortOption {
    NEWEST, OLDEST, FAVORITES, DEADLINE_NEAR
}

data class NoteUiState(
    val notes: List<Note> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val sortOption: SortOption = SortOption.NEWEST,
    val isGridView: Boolean = false,
    val completedTasks: Int = 0,
    val activeStreak: Int = 1
)

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val db = NoteDatabase.getDatabase(application, viewModelScope)
    private val repository = NoteRepository(db.noteDao())
    private val prefs = application.getSharedPreferences("note_prefs", Context.MODE_PRIVATE)

    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow("All")
    private val _sortOption = MutableStateFlow(SortOption.NEWEST)
    private val _isGridView = MutableStateFlow(prefs.getBoolean("is_grid_view", false))
    private val _completedTasks = MutableStateFlow(prefs.getInt("completed_tasks", 12))
    private val _activeStreak = MutableStateFlow(prefs.getInt("active_streak", 5))

    val uiState: StateFlow<NoteUiState> = combine(
        repository.allNotes,
        _searchQuery,
        _selectedCategory,
        _sortOption,
        _isGridView,
        _completedTasks,
        _activeStreak
    ) { flows ->
        @Suppress("UNCHECKED_CAST")
        val notes = flows[0] as List<Note>
        val query = flows[1] as String
        val category = flows[2] as String
        val sort = flows[3] as SortOption
        val isGrid = flows[4] as Boolean
        val completed = flows[5] as Int
        val streak = flows[6] as Int

        var filtered = notes.filter { note ->
            (category == "All" || note.category == category) &&
            (note.title.contains(query, ignoreCase = true) || note.content.contains(query, ignoreCase = true))
        }

        filtered = when (sort) {
            SortOption.NEWEST -> filtered.sortedWith(compareByDescending<Note> { it.isPinned }.thenByDescending { it.date })
            SortOption.OLDEST -> filtered.sortedWith(compareByDescending<Note> { it.isPinned }.thenBy { it.date })
            SortOption.FAVORITES -> filtered.sortedWith(compareByDescending<Note> { it.isPinned }.thenByDescending { it.isFavorite }.thenByDescending { it.date })
            SortOption.DEADLINE_NEAR -> filtered.sortedWith(compareByDescending<Note> { it.isPinned }.thenBy { 
                it.deadline ?: "9999-12-31"
            }.thenByDescending { it.date })
        }

        NoteUiState(
            notes = filtered,
            searchQuery = query,
            selectedCategory = category,
            sortOption = sort,
            isGridView = isGrid,
            completedTasks = completed,
            activeStreak = streak
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NoteUiState()
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategoryChange(category: String) {
        _selectedCategory.value = category
    }

    fun onSortOptionChange(sort: SortOption) {
        _sortOption.value = sort
    }

    fun toggleViewMode() {
        val nextMode = !_isGridView.value
        _isGridView.value = nextMode
        prefs.edit().putBoolean("is_grid_view", nextMode).apply()
    }

    fun incrementCompletedTasks() {
        val count = _completedTasks.value + 1
        _completedTasks.value = count
        prefs.edit().putInt("completed_tasks", count).apply()
    }

    fun incrementActiveStreak() {
        val streak = _activeStreak.value + 1
        _activeStreak.value = streak
        prefs.edit().putInt("active_streak", streak).apply()
    }

    fun getNoteById(id: Int): Note? {
        return uiState.value.notes.find { it.id == id }
    }

    fun addNote(title: String, content: String, category: String, deadline: String? = null) {
        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = dateFormat.format(Date())
            val note = Note(
                title = title,
                content = content,
                category = category,
                date = currentDate,
                lastEdited = currentDate,
                deadline = deadline
            )
            repository.insertNote(note)
        }
    }

    fun updateNote(id: Int, title: String, content: String, category: String, deadline: String? = null) {
        viewModelScope.launch {
            val note = repository.getNoteById(id)
            if (note != null) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val updatedNote = note.copy(
                    title = title,
                    content = content,
                    category = category,
                    lastEdited = dateFormat.format(Date()),
                    deadline = deadline
                )
                repository.updateNote(updatedNote)
            }
        }
    }

    fun togglePin(id: Int) {
        viewModelScope.launch {
            val note = repository.getNoteById(id)
            if (note != null) {
                repository.updateNote(note.copy(isPinned = !note.isPinned))
            }
        }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            val note = repository.getNoteById(id)
            if (note != null) {
                repository.updateNote(note.copy(isFavorite = !note.isFavorite))
            }
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            val note = repository.getNoteById(id)
            if (note != null) {
                repository.deleteNote(note)
            }
        }
    }
}
