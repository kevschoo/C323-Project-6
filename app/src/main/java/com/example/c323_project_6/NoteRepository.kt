package com.example.c323_project_6
import androidx.lifecycle.LiveData

/**
 * A repository class abstracting access to the Note data source
 * This class is an abstraction layer between the ViewModel and data access
 */
class NoteRepository(private val noteDao: NoteDao)
{

    /**
     * Retrieves all notes from the data source
     * @return LiveData containing a list of all notes
     */
    fun getAllNotes(): LiveData<List<Note>> {return noteDao.getAllNotes()}

    /**
     * Inserts a new note into the data source
     * @param note The note object to be inserted
     */
    suspend fun insert(note: Note) {noteDao.insert(note)}

    /**
     * Updates an existing note in the data source.
     * @param note The note object containing updated data to replace the existing note with the same ID
     */
    suspend fun update(note: Note) {noteDao.update(note)}

    /**
     * Deletes a specific note from the data source
     * @param note The note object to be deleted
     */
    suspend fun delete(note: Note) {noteDao.delete(note)}
}