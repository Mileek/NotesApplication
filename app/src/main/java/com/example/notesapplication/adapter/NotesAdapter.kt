package com.example.notesapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.databinding.NotesRvListBinding
import com.example.notesapplication.entities.Notes

class NotesAdapter(private val notesList: List<Notes>?) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NotesRvListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = notesList?.get(position)
        holder.binding.noteTitle.text = notesList?.get(position)?.title
        holder.binding.noteDescription.text = notesList?.get(position)?.noteText
        holder.binding.noteDate.text = notesList?.get(position)?.dateTime
    }

    class NotesViewHolder(val binding: NotesRvListBinding) : RecyclerView.ViewHolder(binding.root)
}
