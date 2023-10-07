package com.example.c323_project_6
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Represents a note entity in the application
 * @property id The unique identifier for the note. It auto-increments
 * @property title The title of the note
 * @property description The detailed description or content of the note
 */
@Entity(tableName = "notes")
data class Note
(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String
)