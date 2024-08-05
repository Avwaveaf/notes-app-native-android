package com.avwaveaf.thenotesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avwaveaf.thenotesapp.databinding.ItemNoteLayoutBinding
import com.avwaveaf.thenotesapp.fragments.HomeFragmentDirections
import com.avwaveaf.thenotesapp.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currItem = differ.currentList[position]

        holder.itemBinding.tvNoteTitle.text = currItem.noteTitle
        holder.itemBinding.tvNoteDescription.text = currItem.noteDescription

        holder.itemView.setOnClickListener{
            val navDirection =
                HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(currItem)

            it.findNavController().navigate(navDirection)
        }
    }

    class NoteViewHolder(val itemBinding: ItemNoteLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    // refreshing item on recycler view without reloading all the data
    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteTitle == newItem.noteTitle &&
                    oldItem.noteDescription == newItem.noteDescription
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)
}