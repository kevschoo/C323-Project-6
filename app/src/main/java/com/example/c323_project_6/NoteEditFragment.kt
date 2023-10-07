package com.example.c323_project_6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.c323_project_6.databinding.FragmentNoteEditBinding
import androidx.navigation.findNavController

/**
 * Fragment for editing an existing note or creating a new one
 * It displays input fields for the note's title and description
 * The user can either save changes to the note or add a new note to the database
 */
class NoteEditFragment : Fragment()
{
    private var _binding: FragmentNoteEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SharedViewModel

    /**
     * @param inflater The layout inflater
     * @param container The view group that the fragment's UI should be attached to
     * @param savedInstanceState Previous state of the fragment
     * @return The root view for the fragment's UI, or null
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentNoteEditBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        return view
    }

    /**
     * Sets up the UI and event listeners for this fragment
     * @param view The fragment's root view
     * @param savedInstanceState Previous state of the fragment
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedNote.observe(viewLifecycleOwner, Observer { note ->
            if (note != null)
            {
                binding.etNoteTitle.setText(note.title)
                binding.etNoteDescription.setText(note.description)
            }
        })

        binding.btnSave.setOnClickListener {
            val title = binding.etNoteTitle.text.toString().trim()
            val description = binding.etNoteDescription.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty())
            {
                val existingNote = viewModel.selectedNote.value

                if (existingNote != null)
                {
                    val updatedNote = existingNote.copy(title = title, description = description)
                    viewModel.update(updatedNote)
                }
                else
                {
                    val newNote = Note(0, title, description)
                    viewModel.insert(newNote)
                }

                view.findNavController().navigate(R.id.action_noteEditFragment_to_noteListFragment)
            }
            else {Toast.makeText(requireContext(), "Title and Description cannot be empty!", Toast.LENGTH_SHORT).show()}
        }
    }

    /**
     * Called when the view is destroyed. Cleans up resources related to the binding
     */
    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}