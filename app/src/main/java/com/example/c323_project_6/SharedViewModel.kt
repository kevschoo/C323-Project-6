package com.example.c323_project_6
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel class responsible for managing and storing UI-related data for the notes app
 */
class SharedViewModel(application: Application) : AndroidViewModel(application)
{
    private val noteRepository: NoteRepository
    val selectedNote: MutableLiveData<Note?> = MutableLiveData()

    init
    {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        noteRepository = NoteRepository(noteDao)
    }

    /**
     * Updates a specific note in the data source
     * @param note The note object containing the updated data
     */
    fun update(note: Note) = viewModelScope.launch { noteRepository.update(note) }

    /**
     * Inserts a new note into the data source
     * @param note The note object to be inserted
     */
    fun insert(note: Note) = viewModelScope.launch { noteRepository.insert(note) }

    /**
     * Retrieves all notes from the data source
     * @return LiveData containing a list of all notes
     */
    fun getAllNotes(): LiveData<List<Note>> { return noteRepository.getAllNotes() }

    /**
     * Deletes a specific note from the data source
     * @param note The note object to be deleted
     */
    fun delete(note: Note) = viewModelScope.launch {noteRepository.delete(note) }
}