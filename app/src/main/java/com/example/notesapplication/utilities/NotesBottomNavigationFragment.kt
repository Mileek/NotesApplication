package com.example.notesapplication.utilities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notesapplication.R
import com.example.notesapplication.databinding.FragmentNotesBottomNavigationBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotesBottomNavigationFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotesBottomNavigationBinding
    var selectedNoteColor = "#00BCD4"

    companion object {
        var noteId = -1 // Id notatki, początkowo ustawiony na -1

        // Tworzenie nowej instancji fragmentu z przekazanym identyfikatorem
        fun newInstance(id: Int): NotesBottomNavigationFragment {
            val args = Bundle()
            val fragment = NotesBottomNavigationFragment()
            fragment.arguments = args
            noteId = id
            return fragment
        }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view =
            LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_navigation, null)
        dialog.setContentView(view)

        val layoutParams = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = layoutParams.behavior

        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state = ""
                    //Ustaw stan
                    when (newState) {
                        BottomSheetBehavior.STATE_DRAGGING -> state = "DRAGGING"
                        BottomSheetBehavior.STATE_SETTLING -> state = "SETTLING"
                        BottomSheetBehavior.STATE_EXPANDED -> state = "EXPANDED"
                        BottomSheetBehavior.STATE_COLLAPSED -> state = "COLLAPSED"
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            state = "HIDDEN"
                            dismiss()
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBottomNavigationBinding.inflate(inflater, container, false)

        // Ustaw widok związany z bindingiem
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (noteId != -1) {
            binding.deleteNote.visibility = View.VISIBLE
        } else {
            binding.deleteNote.visibility = View.GONE
        }
        setListeners()
    }

    //Proszę nie zwracać uwagi na ten powtarzający się brzydki kod :(
    private fun setListeners() {
        binding.fNote1.setOnClickListener {
            binding.imgNote1.setImageResource(R.drawable.ic_confirm)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            binding.imgNote8.setImageResource(0)
            selectNoteColor("#00BCD4", "Cyan")
        }
        binding.fNote2.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(R.drawable.ic_confirm)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            binding.imgNote8.setImageResource(0)
            selectNoteColor("#3F51B5", "Blue")
        }
        binding.fNote3.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(R.drawable.ic_confirm)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            binding.imgNote8.setImageResource(0)
            selectNoteColor("#673AB7", "Purple")
        }
        binding.fNote4.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(R.drawable.ic_confirm)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            binding.imgNote8.setImageResource(0)
            selectNoteColor("#E91E63", "DarkRed")
        }
        binding.fNote5.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(R.drawable.ic_confirm)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            binding.imgNote8.setImageResource(0)
            selectNoteColor("#F44336", "LightRed")
        }
        binding.fNote6.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(R.drawable.ic_confirm)
            binding.imgNote7.setImageResource(0)
            binding.imgNote8.setImageResource(0)
            selectNoteColor("#FF9800", "Orange")
        }
        binding.fNote7.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(R.drawable.ic_confirm)
            binding.imgNote8.setImageResource(0)
            selectNoteColor("#FFEB3B", "Yellow")
        }
        binding.fNote8.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            binding.imgNote8.setImageResource(R.drawable.ic_confirm)
            selectNoteColor("#4CAF50", "Green")
        }

        binding.deleteNote.setOnClickListener {
            val intent = Intent("bottom_action")
            intent.putExtra("actionNoteColor", "Delete")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }
    }

    private fun selectNoteColor(colorCode: String, colorName: String) {
        selectedNoteColor = colorCode

        val intent = Intent("bottom_action")
        intent.putExtra("actionNoteColor", colorName)
        intent.putExtra("selectedColor", selectedNoteColor)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }
}