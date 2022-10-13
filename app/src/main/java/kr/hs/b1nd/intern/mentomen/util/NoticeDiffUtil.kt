package kr.hs.b1nd.intern.mentomen.util

import androidx.recyclerview.widget.DiffUtil
import kr.hs.b1nd.intern.mentomen.network.model.Notice

object NoticeDiffUtil : DiffUtil.ItemCallback<Notice>() {
    override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean = oldItem == newItem
}