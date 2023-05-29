package com.example.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.databinding.FragmentCreateNoteBinding
import com.example.notesapp.entities.Notes
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.navigation.fragment.findNavController


class CreateNoteFragment : BaseFragment() {
    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    private var currentDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        binding.tvDateTime.text = currentDate

        binding.imgDone.setOnClickListener {
            saveNote()
        }
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun saveNote() {
        val noteTitle = binding.etNoteTitle.text.toString()
        val noteSubTitle = binding.etNoteSubTitle.text.toString()
        val noteDesc = binding.etNoteDesc.text.toString()

        if (noteTitle.isEmpty()) {
            Toast.makeText(context, "Note Title is required", Toast.LENGTH_SHORT).show()
            return
        }
        if (noteSubTitle.isEmpty()) {
            Toast.makeText(context, "Note Sub Title is required", Toast.LENGTH_SHORT).show()
            return
        }
        if (noteDesc.isEmpty()) {
            Toast.makeText(context, "Note Description must not be empty", Toast.LENGTH_SHORT).show()
            return
        }

        launch {
            val notes = Notes()
            notes.title = noteTitle
            notes.subTitle = noteSubTitle
            notes.noteText = noteDesc
            notes.dateTime = currentDate

            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
            }

            replaceFragment(HomeFragment.newInstance(), false)
        }
    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (isTransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.replace(binding.frameLayout.id, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateNoteFragment()
    }
}
