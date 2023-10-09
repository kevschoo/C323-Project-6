package com.example.c323_project_6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for displaying notes in a RecyclerView
 * Provides functionality to display the list of notes, as well as handling user interactions like deleting and editing a note
 * @param onNoteDelete Callback function that gets called when a note is requested to be deleted
 * @param onNoteEdit Callback function that gets called when a note is requested to be edited
 */
class NoteListAdapter(
    private val onNoteDelete: (Note) -> Unit,
    private val onNoteEdit: (Note) -> Unit
) : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>()
{

    private var notes = emptyList<Note>()

    /**
     * ViewHolder for displaying individual note items
     */
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tvNoteTitle: TextView = itemView.findViewById(R.id.tvNoteTitle)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    /**
     * Creates and returns a ViewHolder for the note item
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position
     * @param viewType The view type of the new View
     * @return A new ViewHolder that holds a View of the given view type
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    /**
     * Binds the data to the ViewHolder for displaying the note item
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position
     * @param position The position of the item within the data set
     */
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int)
    {
        val current = notes[position]
        holder.tvNoteTitle.text = current.title
        holder.btnDelete.setOnClickListener { onNoteDelete(current) }
        holder.itemView.setOnClickListener { onNoteEdit(current) }
    }

    /**
     * @return The total number of items in this adapter
     */
    override fun getItemCount() = notes.size

    /**
     * Sets or updates the list of notes to be displayed
     * @param newNotes The new list of notes
     */
    internal fun setNotes(newNotes: List<Note>)
    {
        val diffCallback = NoteDiffCallback(this.notes, newNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.notes = newNotes
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Internal class to handle the DiffUtil callbacks
     */
    private class NoteDiffCallback(
        private val oldList: List<Note>,
        private val newList: List<Note>
    ) : DiffUtil.Callback()
    {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}