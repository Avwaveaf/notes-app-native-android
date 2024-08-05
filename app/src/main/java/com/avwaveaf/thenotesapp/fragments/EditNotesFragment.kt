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
import androidx.navigation.fragment.navArgs
import com.avwaveaf.thenotesapp.MainActivity
import com.avwaveaf.thenotesapp.R
import com.avwaveaf.thenotesapp.databinding.FragmentEditNotesBinding
import com.avwaveaf.thenotesapp.model.Note
import com.avwaveaf.thenotesapp.viewmodel.NoteViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class EditNotesFragment : Fragment(R.layout.fragment_edit_notes), MenuProvider {

    //  BINDING
    private var editNoteBinding: FragmentEditNotesBinding? = null
    private val binding
        get() = checkNotNull(editNoteBinding) {
            "Edit menu fragment binding are null!"
        }

    // VIEW MODEL
    private lateinit var noteViewModel: NoteViewModel

    // TRACK CURRENT NOTE
    private lateinit var currentNote: Note

    // DATA ARGUMENTS FROM GRAPH
    private val args: EditNotesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        editNoteBinding = FragmentEditNotesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // declare the menu host
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // initialize view model
        noteViewModel = (activity as MainActivity).noteViewModel

        // init current note by args
        currentNote = args.note!!

        // setup current note data
        setupNotePrevData()

        // setup edit note fab
        setupFab()
    }

    private fun setupFab() {
        binding.fabDoneEdit.setOnClickListener {
            val newNoteTitle = binding.editTextNoteTitle.text.toString().trim()
            val newNoteDescription = binding.editTextNoteDescription.text.toString().trim()

            if (newNoteTitle.isNotEmpty()) {
                val updatedNote = Note(
                    currentNote.id,
                    noteTitle = newNoteTitle,
                    noteDescription = newNoteDescription

                )
                noteViewModel.updateNote(updatedNote)


                // return back to home fragment using navigation component
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            } else {
                Toast.makeText(context, "New Note title cannot be empty!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun deleteNote() {
        val builder = MaterialAlertDialogBuilder(requireActivity())

        builder.setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Delete") { dialog, _ ->
                noteViewModel.deleteNote(currentNote)
                Toast.makeText(context, "Note deleted Successfully!", Toast.LENGTH_SHORT).show()
                dialog.dismiss() // Dismiss the dialog after positive action
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog on cancel
            }

        builder.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.delete_menu -> {
                deleteNote()
                true
            }

            else -> false
        }
    }

    private fun setupNotePrevData() {
        binding.editTextNoteTitle.setText(currentNote.noteTitle)
        binding.editTextNoteDescription.setText(currentNote.noteDescription)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        editNoteBinding = null
    }

}