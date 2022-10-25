package kr.hs.b1nd.intern.mentomen.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ItemNoticeBinding
import kr.hs.b1nd.intern.mentomen.network.model.Notice
import kr.hs.b1nd.intern.mentomen.util.NoticeDiffUtil

class NoticeAdapter(private val itemClick: (Notice) -> Unit) : ListAdapter<Notice, NoticeAdapter.NoticeViewHolder>(NoticeDiffUtil) {

    inner class NoticeViewHolder(private val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Notice) {
            binding.item = item
            binding.root.setOnClickListener { itemClick(item) }
            when (item.noticeStatus) {
                "NONE" -> binding.status.visibility = View.GONE
                "EXIST" -> binding.status.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        return NoticeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_notice,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
