package com.example.demonotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.demonotes.databinding.FragmentNoteBinding
import com.example.demonotes.model.NoteRequest
import com.example.demonotes.model.NoteResponse
import com.example.demonotes.utils.NetworkResult
import com.example.demonotes.viewmodel.NoteViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private val noteViewModel by viewModels<NoteViewModel>()
    private var note: NoteResponse? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitializeData()
        observer()
        handler()

    }

    private fun handler() {
        binding.imgDelete.setOnClickListener {
            note?.let {
                noteViewModel.deleteNote(it!!._id)
            }
        }

        binding.btnAction.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val decs = binding.etNote.text.toString()
            val noteRequest = NoteRequest(title = title, description = decs)
            if (note == null) {
                noteViewModel.createNote(noteRequest)

            } else {
                noteViewModel.updateNote(noteId = note!!._id, noteRequest = noteRequest)
            }
        }

    }

    private fun observer() {
        noteViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Error -> {
                }
                is NetworkResult.Loading -> {
                }
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                    Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                else -> {}
            }
        })
    }

    private fun setInitializeData() {
        val jsonNote = arguments?.getString("note")
        if (jsonNote != null) {
            note = Gson().fromJson(jsonNote, NoteResponse::class.java)
            note?.let {
                binding.etNote.setText(it.description)
                binding.etTitle.setText(it.title)
            }

        } else {
            binding.tvHading.text = "Add Note"
        }
    }


}