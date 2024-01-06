package com.example.notesapplication

import com.example.notesapplication.databinding.FragmentCreateNoteBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.notesapplication.database.NotesDataBase
import com.example.notesapplication.entities.Notes
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class CreateNoteFragment : BaseFragment() {
    private lateinit var binding: FragmentCreateNoteBinding
    var currentDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Utwórz binding FragmentCreateNoteBinding
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)

        // Ustaw widok związany z bindingiem
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        binding.tvDateTime.text = "Created at: $currentDate"

        binding.imgApprove.setOnClickListener {
            saveNote()
        }

        binding.imgBack.setOnClickListener {
            replaceFragment(HomeFragment.newInstance(), false)
        }

    }

    private fun saveNote() {
        if (binding.edtTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Title is required", Toast.LENGTH_SHORT).show()
        }

        if (binding.etNoteSubTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Sub title is required", Toast.LENGTH_SHORT).show()
        }

        if (binding.etNoteDescription.text.isNullOrEmpty()) {
            Toast.makeText(context, "Description can not be empty", Toast.LENGTH_SHORT).show()
        }

        launch {
            context?.let {
                var notes = Notes()
                notes.title = binding.edtTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.noteText = binding.etNoteDescription.text.toString()
                notes.dateTime = currentDate
                context?.let {
                    NotesDataBase.getDatabase(it)?.notesDao()?.insertNotes(notes)
                    binding.edtTitle.setText("")
                    binding.etNoteSubTitle.setText("")
                    binding.etNoteDescription.setText("")
                }
            }
        }
    }

    fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (istransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.replace(R.id.Home_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }
}