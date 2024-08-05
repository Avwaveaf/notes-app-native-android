package com.avwaveaf.thenotesapp.repository

import com.avwaveaf.thenotesapp.database.NoteDatabase
import com.avwaveaf.thenotesapp.model.Note

class NoteRepository(private val db: NoteDatabase) {

    suspend fun insert(note: Note) = db.noteDao().insert(note)
    suspend fun update(note: Note) = db.noteDao().update(note)
    suspend fun delete(note: Note) = db.noteDao().delete(note)

    fun getAllNotes() = db.noteDao().getAllNotes()
    fun searchNotes(searchQuery: String?) = db.noteDao().searchNotes(searchQuery)
}