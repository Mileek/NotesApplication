package com.example.notesapplication.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Notes")
data class Notes (
    @PrimaryKey(autoGenerate = true)
    var noteId: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "subTitle")
    var subTitle: String = "",

    @ColumnInfo(name = "dateTime")
    var dateTime: String = "",

    @ColumnInfo(name = "noteText")
    var noteText: String = "",

    @ColumnInfo(name = "imgPath")
    var imgPath: String = "",

    @ColumnInfo(name = "webLink")
    var webLink: String = "",

    @ColumnInfo(name = "noteColor")
    var noteColor: String = ""

): Serializable {
    override fun toString(): String {
        return "$title : $dateTime"
    }
}

