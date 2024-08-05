package com.avwaveaf.thenotesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.avwaveaf.thenotesapp.model.Note
import com.avwaveaf.thenotesapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val repository: NoteRepository) :
    AndroidViewModel(app) {
    fun addNote(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
    fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }
    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun getAllNotes() = repository.getAllNotes()

    fun searchNotes(searchQuery:String?) = repository.searchNotes(searchQuery)


}