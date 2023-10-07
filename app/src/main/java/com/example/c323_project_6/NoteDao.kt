package com.example.c323_project_6
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

/**
 * This interface provides methods for performing database operations on the Note table
 */
@Dao
interface NoteDao
{
    /**
     * Inserts a new note into the database
     * @param note The note to be inserted.
     */
    @Insert
    suspend fun insert(note: Note)

    /**
     * Updates an existing note in the database
     * @param note The note with updated information to be replaced in the database
     */
    @Update
    suspend fun update(note: Note)

    /**
     * Deletes a note from the database
     * @param note The note to be deleted
     */
    @Delete
    suspend fun delete(note: Note)

    /**
     * Retrieves all notes from the database
     * @return A LiveData containing a list of all notes stored in the database
     */
    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>
}