package com.avwaveaf.thenotesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.avwaveaf.thenotesapp.MainActivity
import com.avwaveaf.thenotesapp.R
import com.avwaveaf.thenotesapp.databinding.FragmentAddNotesBinding
import com.avwaveaf.thenotesapp.model.Note
import com.avwaveaf.thenotesapp.viewmodel.NoteViewModel


class AddNotesFragment : Fragment(R.layout.fragment_add_notes), MenuProvider {

    // BINDING
    private var addNoteBinding: FragmentAddNotesBinding? = null
    private val binding get() = checkNotNull(addNoteBinding){
        "Add note binding are null!!"
    }

    // VIEW MODEL
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var addNoteView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        addNoteBinding = FragmentAddNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // declare the menu host
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // initialize view model
        noteViewModel = (activity as MainActivity).noteViewModel

        // INITIALIZE THE VIEW
        addNoteView = view
    }

    private fun addNote(view: View) {
        val newNoteTitle = binding.editTextNoteTitle.text.toString().trim()
        val newNoteDescription = binding.editTextNoteDescription.text.toString().trim()

        if (newNoteTitle.isNotEmpty()) {
            val note = Note(id = 0, noteTitle = newNoteTitle, noteDescription = newNoteDescription)
            noteViewModel.addNote(note)

            Toast.makeText(
                addNoteView.context,
                "New note has been added successfullly !",
                Toast.LENGTH_SHORT
            ).show()

            // return back to home fragment using navigation component
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(
                addNoteView.context,
                "Note Title Cannot be Empty!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        // attach menu
        menuInflater.inflate(R.menu.add_menu, menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.save_menu -> {
                addNote(addNoteView)
                true
            }
            else->false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        addNoteBinding = null
    }

}