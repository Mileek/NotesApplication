package com.example.notesapplication.dao

import androidx.room.*
import com.example.notesapplication.entities.Notes

@Dao
interface NotesDao {
    //Raw sql queries
    @Query("SELECT * FROM notes ORDER BY noteId DESC")
    suspend fun getAllNotes(): List<Notes>

    @Query("SELECT * FROM notes WHERE noteId =:id")
    suspend fun getNoteById(id: Int): Notes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: Notes)

    @Query("DELETE FROM notes WHERE noteId =:id")
    suspend fun deleteNoteById(id: Int)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

    @Update
    suspend fun updateNote(notes: Notes)
}