package com.praktikumpab.note_manager.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val date: String,
    val category: String,
    val lastEdited: String? = null,
    val isPinned: Boolean = false,
    val isFavorite: Boolean = false,
    val deadline: String? = null,
    val iconRes: Int? = null
)
