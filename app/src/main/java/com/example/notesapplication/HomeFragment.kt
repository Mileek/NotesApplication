package com.example.notesapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapplication.adapter.NotesAdapter
import com.example.notesapplication.database.NotesDataBase
import com.example.notesapplication.databinding.FragmentHomeBinding
import com.example.notesapplication.entities.Notes
import kotlinx.coroutines.launch
import java.util.Locale

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val notesAdapter = NotesAdapter()
    private var notesArray = ArrayList<Notes>()
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
        setupRecycleView()
        setupListeners()
    }

    private fun setupListeners() {
        notesAdapter.setOnClickListener(onItemClicked)
        binding.fabAddNote.setOnClickListener {
            replaceFragment(CreateNoteFragment.newInstance(), false)
        }
        //Usuń wszystkie notatki, bardziej przycisk developerski
        binding.deleteAllNotes.setOnClickListener {
            launch {
                context?.let {
                    NotesDataBase.getDatabase(it)?.notesDao()?.deleteAllNotes()
                    notesAdapter.setData(ArrayList())
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
        //Wyszukiwanie notatek
        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterNotes(newText)
                return true
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterNotes(query: String?) {
        val tempArray = ArrayList<Notes>()
        query?.let {
            for (notes in notesArray) {
                if (notes.title?.lowercase(Locale.getDefault())?.contains(it) == true) {
                    tempArray.add(notes)
                }
            }
            notesAdapter.setData(tempArray)
            notesAdapter.notifyDataSetChanged()
        }
    }

    private fun setupRecycleView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                val notes = NotesDataBase.getDatabase(it)?.notesDao()?.getAllNotes()
                notesAdapter.setData(notes!!)
                notesArray = notes as ArrayList<Notes>
                binding.recyclerView.adapter = notesAdapter
            }
        }
    }

    private val onItemClicked = object : NotesAdapter.OnItemClickListener {
        override fun onItemClick(noteId: Int) {
            val bundle = Bundle()
            var fragment = Fragment()
            bundle.putInt("noteId", noteId)
            fragment = CreateNoteFragment.newInstance()
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
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
    }
}