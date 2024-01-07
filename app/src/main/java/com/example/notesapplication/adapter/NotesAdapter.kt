package com.example.notesapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.databinding.NotesRvListBinding
import com.example.notesapplication.entities.Notes

class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
        var listener: OnItemClickListener? = null

        var arrayNotesList = ArrayList<Notes>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NotesRvListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayNotesList?.size ?: 0
    }

    public fun setData(notesList: List<Notes>?) {
        arrayNotesList = notesList as ArrayList<Notes>
    }

    fun setOnClickListener(passedListener: OnItemClickListener) {
        listener = passedListener
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = arrayNotesList?.get(position)
        holder.binding.noteTitle.text = arrayNotesList?.get(position)?.title
        holder.binding.noteDescription.text = arrayNotesList?.get(position)?.noteText
        holder.binding.noteDate.text = arrayNotesList?.get(position)?.dateTime

        if (currentNote!!.noteColor != null) {
            holder.binding.noteView.setCardBackgroundColor(Color.parseColor((arrayNotesList?.get(position)?.noteColor)))
        } else {
            holder.binding.noteView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        holder.binding.noteView.setOnClickListener {
            listener!!.onItemClick(currentNote.noteId!!)
        }
    }

    class NotesViewHolder(val binding: NotesRvListBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemClick(noteId: Int)
    }
}
