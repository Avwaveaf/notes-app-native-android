package com.avwaveaf.thenotesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.avwaveaf.thenotesapp.MainActivity
import com.avwaveaf.thenotesapp.R
import com.avwaveaf.thenotesapp.adapters.NoteAdapter
import com.avwaveaf.thenotesapp.databinding.FragmentHomeBinding
import com.avwaveaf.thenotesapp.model.Note
import com.avwaveaf.thenotesapp.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener,
    MenuProvider {

        // BINDING
    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = checkNotNull(homeBinding){
        "Home Fragment Binding are null. please check if the view visible!"
    }

    // VIEW MODELS
    private lateinit var noteViewModel: NoteViewModel

    // ADAPTER
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // declare the menu host
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // initialize view model
        noteViewModel = (activity as MainActivity).noteViewModel

        // setup home fragment recycler view
        setupRecyclerView()

        // setup fab to redirect to addNoteFragment action
        binding.fabAddNote.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addNotesFragment)
        }
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter()
        binding.recyclerViewHome.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            // observe the notes live data from view model
            noteViewModel.getAllNotes().observe(viewLifecycleOwner){notes->
                noteAdapter.differ.submitList(notes)
                updateUI(notes)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // prohibit using submit button
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // each typed letter will sent the query and sent to adapter
        if (newText != null) {
            searchNotes(newText)
        }
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.search_menu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

    private fun searchNotes(searchQuery: String?) {
        if (searchQuery.isNullOrEmpty()) {
            noteViewModel.getAllNotes().observe(this){notes->
                noteAdapter.differ.submitList(notes)
                updateUI(notes)
            }
            return
        }
        val searchString = searchQuery.replace("%", "\\%") // Escape wildcard character
        noteViewModel.searchNotes(searchString).observe(this){ notesFound->
            noteAdapter.differ.submitList(notesFound)
        }
    }

    /*
        * Update UI from empty image background to the recycler view
        * */
    private fun updateUI(notes: List<Note>?) {
        if (notes != null) {
            if (notes.isNotEmpty()) {
                binding.emptyNotesImage.visibility = View.GONE
                // display recycler view
                binding.recyclerViewHome.visibility = View.VISIBLE
            }else{
                binding.emptyNotesImage.visibility = View.VISIBLE
                // display recycler view
                binding.recyclerViewHome.visibility = View.GONE
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        homeBinding = null
    }

}