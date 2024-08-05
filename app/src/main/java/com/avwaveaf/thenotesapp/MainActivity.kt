package com.avwaveaf.thenotesapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.avwaveaf.thenotesapp.database.NoteDatabase
import com.avwaveaf.thenotesapp.repository.NoteRepository
import com.avwaveaf.thenotesapp.viewmodel.NoteViewModel
import com.avwaveaf.thenotesapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

     lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewModel()
    }

    private fun setupViewModel() {
        val repository = NoteRepository(NoteDatabase(this))
        val noteViewModelFactory = NoteViewModelFactory(application, repository)
        noteViewModel = ViewModelProvider(this, noteViewModelFactory)[NoteViewModel::class.java]
    }
}