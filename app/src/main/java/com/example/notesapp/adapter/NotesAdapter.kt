package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemRvNotesBinding
import com.example.notesapp.entities.Notes

class NotesAdapter(private val arrList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemRvNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(arrList[position])
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    inner class NotesViewHolder(private val binding: ItemRvNotesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Notes) {
            binding.tvTitle.text = note.title
            binding.tvDesc.text = note.noteText
            binding.tvDateTime.text = note.dateTime
        }
    }
}

