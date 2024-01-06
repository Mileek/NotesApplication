package com.example.notesapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_create_note.imgApprove
import kotlinx.android.synthetic.main.fragment_create_note.imgBack
import kotlinx.android.synthetic.main.fragment_create_note.tvDateTime
import java.text.SimpleDateFormat
import java.util.Date

class CreateNoteFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
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
        val currentDate = sdf.format(Date())

        tvDateTime.text = "Created at: $currentDate"

        imgApprove.setOnClickListener {
            saveNote()
        }

//        imgBack.setOnClickListener(View.OnClickListener {
//        saveNote()
//        })
//        }
    }
    private fun saveNote(){

    }
}