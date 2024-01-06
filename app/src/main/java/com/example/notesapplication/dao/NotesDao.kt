package com.example.notesapplication.dao

import androidx.room.*
import com.example.notesapplication.entities.Notes

@Dao
interface NotesDao {
    //Raw sql queries
    @Query("SELECT * FROM notes ORDER BY noteId DESC")
    suspend fun getAllNotes(): List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: Notes)

    @Delete
    suspend fun deleteNotes(notes: Notes)
}