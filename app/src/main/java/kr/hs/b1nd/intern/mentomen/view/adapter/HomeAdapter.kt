package kr.hs.b1nd.intern.mentomen.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ItemHomeBinding
import kr.hs.b1nd.intern.mentomen.network.model.Post

class HomeAdapter(private val item: List<Post>, private val clickListener: (Post) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private lateinit var binding: ItemHomeBinding

    inner class HomeViewHolder(binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post) {
            binding.item = item
            when (item.tag) {
                "ANDROID" -> binding.ivTag.setImageResource(R.drawable.ic_android)
                "IOS" -> binding.ivTag.setImageResource(R.drawable.ic_ios)
                "WEB" -> binding.ivTag.setImageResource(R.drawable.ic_web)
                "SERVER" -> binding.ivTag.setImageResource(R.drawable.ic_server)
                "DESIGN" -> binding.ivTag.setImageResource(R.drawable.ic_design)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(item[position])
        binding.root.setOnClickListener {
            clickListener(item[position])
        }
    }

    override fun getItemCount() = item.size
}