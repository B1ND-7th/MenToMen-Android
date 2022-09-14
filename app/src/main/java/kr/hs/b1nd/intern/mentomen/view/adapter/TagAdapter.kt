package kr.hs.b1nd.intern.mentomen.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.TagItemBinding
import kr.hs.b1nd.intern.mentomen.network.model.TagInfo

class TagAdapter() : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    private val item =
        listOf(
            TagInfo("DESIGN", R.drawable.corner_radious, R.color.design, R.color.white),
            TagInfo("WEB", R.drawable.corner_radious, R.color.web, R.color.white),
            TagInfo("SERVER", R.drawable.corner_radious, R.color.server, R.color.white),
            TagInfo("ANDROID", R.drawable.corner_radious, R.color.android, R.color.white),
            TagInfo("IOS", R.drawable.corner_radious, R.color.iOS, R.color.white),
        )

    inner class TagViewHolder(private val binding: TagItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TagInfo) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = TagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount() = item.size

}