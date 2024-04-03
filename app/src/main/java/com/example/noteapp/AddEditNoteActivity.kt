package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.Date

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var  edtNoteTitle  : EditText
    lateinit var edtNoteDescription : EditText
    lateinit var btnAddUpdate: Button
    lateinit var viewModel: NoteViewModel
    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        edtNoteTitle = findViewById(R.id.edtNoteTitle)
        edtNoteDescription = findViewById(R.id.edtNoteDescription)
        btnAddUpdate = findViewById(R.id.btnAddUpdate)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID",-1)
            btnAddUpdate.setText("Update Note")
            edtNoteTitle.setText(noteTitle)
            edtNoteDescription.setText(noteDesc)
        }
        else{
            btnAddUpdate.setText("Save Note")
        }

        btnAddUpdate.setOnClickListener {
            val noteTitle = edtNoteTitle.text.toString()
            val noteDescription = edtNoteDescription.text.toString()

            if(noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM ,yyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updateNote = Note(noteTitle, noteDescription, currentDate)
                    updateNote.id = noteID
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            }
            else{
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){

                    val sdf = SimpleDateFormat("dd MMM ,yyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    viewModel.addNote(Note(noteTitle,noteDescription,currentDate))
                    Toast.makeText(this,"Note Added..",Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
        }

    }
}