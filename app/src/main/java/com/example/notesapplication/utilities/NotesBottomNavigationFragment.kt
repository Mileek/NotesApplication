package com.example.notesapplication.utilities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notesapplication.R
import com.example.notesapplication.databinding.FragmentNotesBottomNavigationBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.FileOutputStream

class NotesBottomNavigationFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotesBottomNavigationBinding
    var selectedNoteColor = "#00BCD4"
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CAMERA_PERMISSION = 100

    companion object {
        var noteId = -1

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
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_navigation, null)
        dialog.setContentView(view)

        val layoutParams = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = layoutParams.behavior

        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss()
                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
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

    private fun setListeners() {
        binding.fNote1.setOnClickListener { selectNoteColor("#00BCD4", "Cyan") }
        binding.fNote2.setOnClickListener { selectNoteColor("#3F51B5", "Blue") }
        binding.fNote3.setOnClickListener { selectNoteColor("#673AB7", "Purple") }
        binding.fNote4.setOnClickListener { selectNoteColor("#E91E63", "DarkRed") }
        binding.fNote5.setOnClickListener { selectNoteColor("#F44336", "LightRed") }
        binding.fNote6.setOnClickListener { selectNoteColor("#FF9800", "Orange") }
        binding.fNote7.setOnClickListener { selectNoteColor("#FFEB3B", "Yellow") }

        binding.deleteNote.setOnClickListener {
            val intent = Intent("bottom_action")
            intent.putExtra("actionNoteColor", "Delete")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }

        binding.fPhoto.setOnClickListener {
            checkCameraPermission()
        }
    }

// Sprawdza, czy aplikacja ma uprawnienia do korzystania z kamery
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // Jeśli nie ma uprawnień, prosi użytkownika o ich przyznanie
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            // Jeśli uprawnienia są przyznane, uruchamia aparat
            dispatchTakePictureIntent()
        }
    }

    // Uruchamia intencję do zrobienia zdjęcia
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    // Obsługuje wynik żądania uprawnień
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Jeśli uprawnienia są przyznane, uruchamia aparat
                dispatchTakePictureIntent()
            } else {
                // Permission denied
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val imagePath = saveImageToStorage(imageBitmap)
            emitImageBroadcast(imagePath)
        }
    }

    private fun saveImageToStorage(bitmap: Bitmap): String {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        // Tworzenie pliku w zewnętrznym katalogu aplikacji
        val file = File(requireContext().getExternalFilesDir(null), filename)
        // Zapisywanie bitmapy do pliku w formacie JPEG w pamięci zewnętrznej
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        // Zwracanie pełnej ścieżki do zapisanego pliku
        return file.absolutePath
    }

    private fun emitImageBroadcast(imagePath: String) {
        val intent = Intent("bottom_action")
        intent.putExtra("imagePath", imagePath)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }

    private fun selectNoteColor(colorCode: String, colorName: String) {
        selectedNoteColor = colorCode
        val intent = Intent("bottom_action")
        intent.putExtra("actionNoteColor", colorName)
        intent.putExtra("selectedColor", selectedNoteColor)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }
}