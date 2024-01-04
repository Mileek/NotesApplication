package com.example.notesapplication.dao

import androidx.room.*
import com.example.notesapplication.entities.Notes

@Dao
interface NotesDao {
    //Raw sql queries
    @get:Query("SELECT * FROM notes ORDER BY noteId DESC")
    val allNotes: List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Delete
    fun deleteNotse(notes: Notes)
}