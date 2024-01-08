package com.example.notesapplication.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.databinding.NotesRvListBinding
import com.example.notesapplication.entities.Notes

class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    private var listener: OnItemClickListener? = null
    private var arrayNotesList = ArrayList<Notes>()

    companion object {
        private const val DEFAULT_COLOR_WHITE = "#FFFFFF"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NotesRvListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayNotesList?.size ?: 0
    }

    public fun setData(notesList: List<Notes>?) {
        arrayNotesList.clear()
        arrayNotesList.addAll(notesList ?: emptyList())
        notifyDataSetChanged()
    }

    fun setOnClickListener(passedListener: OnItemClickListener) {
        listener = passedListener
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = arrayNotesList[position]
        holder.binding.noteTitle.text = arrayNotesList[position].title
        holder.binding.noteDescription.text = arrayNotesList[position].noteText
        holder.binding.noteDate.text = arrayNotesList[position].dateTime

        holder.binding.noteView.setCardBackgroundColor(
            Color.parseColor(currentNote.noteColor ?: DEFAULT_COLOR_WHITE)
        )

        holder.binding.noteView.setOnClickListener {
            listener!!.onItemClick(currentNote.noteId!!)
        }
    }

    class NotesViewHolder(val binding: NotesRvListBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemClick(noteId: Int)
    }
}
