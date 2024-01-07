package com.example.notesapplication.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapplication.dao.NotesDao
import com.example.notesapplication.entities.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDataBase : RoomDatabase() {
    companion object {
        private var notesDataBase: NotesDataBase? = null

        fun getDatabase(context: android.content.Context): NotesDataBase {
            return notesDataBase ?: synchronized(this) {
                notesDataBase ?: buildDatabase(context).also { notesDataBase = it }
            }
        }

        //TODO: Później można dodać migracje
        private fun buildDatabase(context: android.content.Context): NotesDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                NotesDataBase::class.java,
                "notes_database.db"
            )
                .build()
        }
    }

    abstract fun notesDao(): NotesDao
}