package com.example.notesapplication

import android.graphics.Color
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.example.notesapplication.databinding.FragmentCreateNoteBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notesapplication.database.NotesDataBase
import com.example.notesapplication.entities.Notes
import com.example.notesapplication.utilities.NotesBottomNavigationFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class CreateNoteFragment : BaseFragment() {
    private lateinit var binding: FragmentCreateNoteBinding
    var currentDate: String? = null
    var selectedNoteColor = "#00BCD4"
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = requireArguments().getInt("noteId", -1)
    }

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

        if (noteId != -1) {
            launch {
                context?.let {
                    val notes = NotesDataBase.getDatabase(it)?.notesDao()?.getNoteById(noteId)
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor(notes?.noteColor))
                    //ToString jest nie potrzebny, bo i tak jest stringiem
                    binding.edtTitle.setText(notes?.title.toString())
                    binding.etNoteSubTitle.setText(notes?.subTitle.toString())
                    binding.etNoteDescription.setText(notes?.noteText.toString())
                }
            }
        }

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver!!,
            IntentFilter("bottom_action")
        )
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        binding.viewNoteColor.setBackgroundColor(Color.parseColor(selectedNoteColor))
        currentDate = sdf.format(Date())

        binding.tvDateTime.text = "Created at: $currentDate"

        binding.imgApprove.setOnClickListener {
            if (noteId != -1) {
                updateNote()
            } else {
                saveNote()
            }
        }

        binding.imgBack.setOnClickListener {
            //replaceFragment(HomeFragment.newInstance(), false)
            requireActivity().supportFragmentManager?.popBackStack()
        }

        binding.imgMoreNavi.setOnClickListener {
            val notesBottomNavigationFragment = NotesBottomNavigationFragment.newInstance(noteId)
            notesBottomNavigationFragment.show(
                requireActivity().supportFragmentManager,
                "BottomNavigationFragment"
            )
        }
    }

    private fun updateNote() {
        launch {
            context?.let {
                val notes = NotesDataBase.getDatabase(it)?.notesDao()?.getNoteById(noteId)
                notes?.title = binding.edtTitle.text.toString()
                notes?.subTitle = binding.etNoteSubTitle.text.toString()
                notes?.noteText = binding.etNoteDescription.text.toString()
                notes?.dateTime = currentDate
                notes?.noteColor = selectedNoteColor

                if (notes != null) {
                    NotesDataBase.getDatabase(it)?.notesDao()?.updateNote(notes)
                }
                binding.edtTitle.setText("")
                binding.etNoteSubTitle.setText("")
                binding.etNoteDescription.setText("")
                requireActivity().supportFragmentManager.popBackStack()
            }
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
                val notes = Notes()
                notes.title = binding.edtTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.noteText = binding.etNoteDescription.text.toString()
                notes.dateTime = currentDate
                notes.noteColor = selectedNoteColor
                context?.let {
                    NotesDataBase.getDatabase(it)?.notesDao()?.insertNotes(notes)
                    binding.edtTitle.setText("")
                    binding.etNoteSubTitle.setText("")
                    binding.etNoteDescription.setText("")
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun deleteNote() {
        launch {
            context?.let {
                NotesDataBase.getDatabase(it)?.notesDao()?.deleteNoteById(noteId)
                requireActivity().supportFragmentManager.popBackStack()
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

    //Musze odbierac z emitera
    private var BroadcastReceiver: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var actionColor = intent!!.getStringExtra("actionNoteColor")

            //Akcja nie powinna się tak nazywać, ale na początku miała tylko za zadanie obsługiwać kolory,
            //potem to się zmieniło, ale nazwa została
            when (actionColor!!) {
                "Cyan" -> {
                    selectedNoteColor = intent.getStringExtra("selectedColor")!!
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor(selectedNoteColor))
                }

                "Blue" -> {
                    selectedNoteColor = intent.getStringExtra("selectedColor")!!
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor(selectedNoteColor))
                }

                "Purple" -> {
                    selectedNoteColor = intent.getStringExtra("selectedColor")!!
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor(selectedNoteColor))
                }

                "DarkRed" -> {
                    selectedNoteColor = intent.getStringExtra("selectedColor")!!
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor(selectedNoteColor))
                }

                "LightRed" -> {
                    selectedNoteColor = intent.getStringExtra("selectedColor")!!
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor(selectedNoteColor))
                }

                "Orange" -> {
                    selectedNoteColor = intent.getStringExtra("selectedColor")!!
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor(selectedNoteColor))
                }

                "Yellow" -> {
                    selectedNoteColor = intent.getStringExtra("selectedColor")!!
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor(selectedNoteColor))
                }

                "Green" -> {
                    selectedNoteColor = intent.getStringExtra("selectedColor")!!
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor(selectedNoteColor))
                }

                "Delete" -> {
                    //Delete Item
                    deleteNote()
                }

                else -> {
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor("#FFFFFF"))
                }
            }
        }
    }

    override fun onDestroy() {
        //Odlaczam sie zeby nie bylo wyciekow pamieci
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver!!)
        super.onDestroy()
    }
}