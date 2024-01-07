package com.example.notesapplication.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Notes")
class Notes : Serializable {
    @PrimaryKey(autoGenerate = true)
    var noteId: Int? = null

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "subTitle")
    var subTitle: String? = null

    @ColumnInfo(name = "dateTime")
    var dateTime: String? = null

    @ColumnInfo(name = "noteText")
    var noteText: String? = null

    @ColumnInfo(name = "noteColor")
    var noteColor: String? = null

    override fun toString(): String {
        return "$title : $dateTime"
    }

    //Obsolete, do przeniesienia z wpf
    @ColumnInfo(name = "imgPath")
    var imgPath: String? = null

    @ColumnInfo(name = "webLink")
    var webLink: String? = null

}

