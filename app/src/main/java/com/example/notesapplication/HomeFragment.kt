package com.example.notesapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapplication.adapter.NotesAdapter
import com.example.notesapplication.database.NotesDataBase
import com.example.notesapplication.databinding.FragmentHomeBinding
import com.example.notesapplication.entities.Notes
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    var notesAdapter: NotesAdapter = NotesAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Utwórz binding FragmentHomeBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Zwróć widok związany z bindingiem
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                val notes = NotesDataBase.getDatabase(it)?.notesDao()?.getAllNotes()
                notesAdapter.setData(notes!!)
                binding.recyclerView.adapter = notesAdapter
            }
        }

        notesAdapter.setOnClickListener(onItemClicked)
        binding.fabAddNote.setOnClickListener {
            replaceFragment(CreateNoteFragment.newInstance(), false)
        }
    }

    private val onItemClicked = object : NotesAdapter.OnItemClickListener {
        override fun onItemClick(notesId: Int) {
            var bundle = Bundle()
            var fragment = Fragment()
            bundle.putInt("noteId", notesId!!)
            fragment= CreateNoteFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment, false)
        }
    }

    private fun replaceFragment(fragment: Fragment, istransition: Boolean) {
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