package kr.hs.b1nd.intern.mentomen.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ItemHomeBinding
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.util.PostDiffUtilCallback

class HomeAdapter(private val itemClick: (Post) -> Unit) :
    ListAdapter<Post, HomeAdapter.HomeViewHolder>(PostDiffUtilCallback) {

    inner class HomeViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post) {

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

            if (item.imgUrls.isNullOrEmpty()) {
                binding.cardView.visibility = View.GONE
            } else {
                binding.cardView.visibility = View.VISIBLE
                Glide.with(binding.ivContent.context)
                    .load(item.imgUrls[0])
                    .into(binding.ivContent)

            }
            binding.item = item

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_home,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}