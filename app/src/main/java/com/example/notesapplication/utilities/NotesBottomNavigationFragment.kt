package com.example.notesapplication.utilities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notesapplication.R
import com.example.notesapplication.databinding.FragmentCreateNoteBinding
import com.example.notesapplication.databinding.FragmentNotesBottomNavigationBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotesBottomNavigationFragment :BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotesBottomNavigationBinding
    var selectedNoteColor = "#00BCD4"

    companion object {
        fun newInstance(): NotesBottomNavigationFragment{
            val args = Bundle()
            val fragment = NotesBottomNavigationFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_navigation, null)
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
        setListeners()
    }

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
            selectedNoteColor = "#00BCD4"

            val intent = Intent("bottom_action")
            intent.putExtra("actionNoteColor", "Cyan")
            intent.putExtra("selectedColor", selectedNoteColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
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
            selectedNoteColor = "#3F51B5"

            val intent = Intent("bottom_action")
            intent.putExtra("actionNoteColor", "Blue")
            intent.putExtra("selectedColor", selectedNoteColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
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
            selectedNoteColor = "#673AB7"

            val intent = Intent("bottom_action")
            intent.putExtra("actionNoteColor", "Purple")
            intent.putExtra("selectedColor", selectedNoteColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
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
            selectedNoteColor = "#E91E63"

            val intent = Intent("bottom_action")
            intent.putExtra("actionNoteColor", "DarkRed")
            intent.putExtra("selectedColor", selectedNoteColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
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
            selectedNoteColor = "#F44336"

            val intent = Intent("bottom_action")
            intent.putExtra("actionNoteColor", "LightRed")
            intent.putExtra("selectedColor", selectedNoteColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
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
            selectedNoteColor = "#FF9800"

            val intent = Intent("bottom_action")
            intent.putExtra("actionNoteColor", "Orange")
            intent.putExtra("selectedColor", selectedNoteColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
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
            selectedNoteColor = "#FFEB3B"

            val intent = Intent("bottom_action")
            intent.putExtra("actionNoteColor", "Yellow")
            intent.putExtra("selectedColor", selectedNoteColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
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
            selectedNoteColor = "#4CAF50"

            val intent = Intent("bottom_action")
            intent.putExtra("actionNoteColor", "Green")
            intent.putExtra("selectedColor", selectedNoteColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
    }
}