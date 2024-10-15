package com.example.notesapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notesapplication.dao.NotesDao
import com.example.notesapplication.entities.Notes

@Database(entities = [Notes::class], version = 2, exportSchema = false)
abstract class NotesDataBase : RoomDatabase() {
    companion object {
        private var notesDataBase: NotesDataBase? = null

        fun getDatabase(context: Context): NotesDataBase {
            return notesDataBase ?: synchronized(this) {
                notesDataBase ?: buildDatabase(context).also { notesDataBase = it }
            }
        }

        private fun buildDatabase(context: Context): NotesDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                NotesDataBase::class.java,
                "notes_database.db"
            )
                .addMigrations(MIGRATION_1_2)
                .build()
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Notes ADD COLUMN imagePath TEXT")
            }
        }
    }

    abstract fun notesDao(): NotesDao
}