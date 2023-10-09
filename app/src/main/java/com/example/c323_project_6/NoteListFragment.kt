package com.example.c323_project_6
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.c323_project_6.databinding.FragmentNoteListBinding
import androidx.navigation.findNavController

/**
 * A Fragment class
 * Displays the list of notes using a RecyclerView and provides the ability for the user to interact with notes
 */
class NoteListFragment : Fragment()
{
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SharedViewModel

    /**
     * Inflates the fragment's layout and initializes the ViewModel
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return The root view of the fragment's layout
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        return view
    }

    /**
     * Sets up the RecyclerView and user interaction listeners
     * @param view The root view of the fragment's layout
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val adapter = NoteListAdapter(
            { note ->
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Delete Note")
                builder.setMessage("Are you sure you want to delete this note?")
                builder.setPositiveButton("Confirm") { _, _ -> viewModel.delete(note)}
                builder.setNegativeButton("Cancel", null)
                val dialog = builder.create()
                dialog.show()
            },
            { note -> viewModel.selectedNote.value = note
                view.findNavController().navigate(R.id.action_noteListFragment_to_noteEditFragment)
            }
        )

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer { adapter.setNotes(it) })

        binding.addNoteButton.setOnClickListener {
            viewModel.selectedNote.value = null
            view.findNavController().navigate(R.id.action_noteListFragment_to_noteEditFragment)
        }
    }

    /**
     * Cleans up resources when the view is destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
