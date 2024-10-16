package com.example.notesapplication.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import com.example.notesapplication.databinding.FragmentCreateNoteBinding
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notesapplication.R
import com.example.notesapplication.database.NotesDataBase
import com.example.notesapplication.entities.Notes
import com.example.notesapplication.utilities.NotesBottomNavigationFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class CreateNoteFragment : BaseFragment() {
    private lateinit var binding: FragmentCreateNoteBinding // Zmienna do przechowywania powiązania z fragmentem
    var currentDate: String? = null // Zmienna przechowująca bieżącą datę
    var selectedNoteColor = "#00BCD4" // Kolor domyślny dla notatki
    var imagePath: String? = null // Zmienna przechowująca ścieżkę obrazu, gdy nie ma obrazu, wartość null
    private var noteId = -1 // Identyfikator notatki, początkowo ustawiony na -1


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

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Wyświetlanie danych notatki, jeśli noteId jest różny -1
        if (noteId != -1) {
            launch {
                context?.let {
                    val notes = NotesDataBase.getDatabase(it).notesDao().getNoteById(noteId)
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor(notes.noteColor))
                    binding.edtTitle.setText(notes.title)
                    binding.etNoteSubTitle.setText(notes.subTitle)
                    binding.etNoteDescription.setText(notes.noteText)

                    Log.d("CreateNoteFragment", "Image Path: ${notes.imagePath}")

                    //If image is not null assign it to image view
                    notes.imagePath?.let { path ->
                        val bitmap = BitmapFactory.decodeFile(path)
                        binding.etNoteImage.setImageBitmap(bitmap)
                    }
                }
            }
        }

        // Rejestracja odbiornika do obsługi akcji z dolnej nawigacji
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver!!,
            IntentFilter("bottom_action")
        )

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        binding.viewNoteColor.setBackgroundColor(Color.parseColor(selectedNoteColor))
        currentDate = sdf.format(Date())

        // Ustawianie tekstu w widoku daty i czasu
        binding.tvDateTime.text = "Created at: $currentDate"

        // Obsługa zatwierdzania notatki
        binding.imgApprove.setOnClickListener {
            if (noteId != -1) {
                updateNote()
            } else {
                saveNote()
            }
        }

        // Obsługa powrotu do poprzedniego ekranu
        binding.imgBack.setOnClickListener {
            //replaceFragment(HomeFragment.newInstance(), false)
            requireActivity().supportFragmentManager?.popBackStack()
        }

        // Obsługa otwierania dolnej nawigacji
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
                val notes = NotesDataBase.getDatabase(it).notesDao().getNoteById(noteId)
                notes.title = binding.edtTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.noteText = binding.etNoteDescription.text.toString()
                notes.dateTime = currentDate
                notes.noteColor = selectedNoteColor
                notes.imagePath = imagePath

                NotesDataBase.getDatabase(it).notesDao().updateNote(notes)
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
                notes.imagePath = imagePath
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

    //Musze odbierac z emitera, coś jak singleton, ale poprzez eventy
    private var BroadcastReceiver: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { intn ->
                val actionColor = intn.getStringExtra("actionNoteColor")
                val actionImage = intn.getStringExtra("imagePath")

                //Akcja nie powinna się tak nazywać, ale na początku miała tylko za zadanie obsługiwać kolory,
                //potem to się zmieniło, ale nazwa została

                actionColor?.let { color ->
                    selectedNoteColor = intn.getStringExtra("selectedColor") ?: selectedNoteColor
                    binding.viewNoteColor.setBackgroundColor(Color.parseColor(selectedNoteColor))
                    when (color) {
                        "Cyan", "Blue", "Purple", "DarkRed", "LightRed", "Orange", "Yellow" -> Unit
                        "Delete" -> deleteNote()
                        else -> binding.viewNoteColor.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    }
                }

                actionImage?.let { path ->
                    imagePath = path
                    val bitmap = BitmapFactory.decodeFile(path)
                    binding.etNoteImage.setImageBitmap(bitmap)
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