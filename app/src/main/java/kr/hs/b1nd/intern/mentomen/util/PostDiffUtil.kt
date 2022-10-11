package kr.hs.b1nd.intern.mentomen.util

import androidx.recyclerview.widget.DiffUtil
import kr.hs.b1nd.intern.mentomen.network.model.Post

object PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.postId == newItem.postId
}