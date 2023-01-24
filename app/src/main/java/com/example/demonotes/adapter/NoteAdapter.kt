package com.example.demonotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demonotes.databinding.RvListBinding
import com.example.demonotes.model.NoteResponse

class NoteAdapter(private var itemList: List<NoteResponse>,private val onNoteClick: (NoteResponse) -> Unit):RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: RvListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:NoteResponse){
           binding.tvTitle.text = data.title
           binding.tvNotes.text = data.description
            binding.root.setOnClickListener {
                onNoteClick(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    fun updateList(newList:List<NoteResponse>){
        itemList = newList
        notifyDataSetChanged()
    }
}