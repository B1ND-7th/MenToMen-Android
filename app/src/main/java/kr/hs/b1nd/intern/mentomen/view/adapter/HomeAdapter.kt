package kr.hs.b1nd.intern.mentomen.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ItemHomeBinding
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.util.DiffUtilCallback

class HomeAdapter(private val itemClick: (Post) -> Unit) : ListAdapter<Post, HomeAdapter.HomeViewHolder>(DiffUtilCallback) {

    inner class HomeViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post, position: Int) {
            binding.item = item
            binding.executePendingBindings()
            when (item.tag) {
                "ANDROID" -> binding.ivTag.setImageResource(R.drawable.ic_android)
                "IOS" -> binding.ivTag.setImageResource(R.drawable.ic_ios)
                "WEB" -> binding.ivTag.setImageResource(R.drawable.ic_web)
                "SERVER" -> binding.ivTag.setImageResource(R.drawable.ic_server)
                "DESIGN" -> binding.ivTag.setImageResource(R.drawable.ic_design)
            }
            binding.root.setOnClickListener {
                itemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}