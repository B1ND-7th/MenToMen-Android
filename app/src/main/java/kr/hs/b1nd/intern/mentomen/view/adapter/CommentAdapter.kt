package kr.hs.b1nd.intern.mentomen.view.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ItemCommentBinding
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Comment
import kr.hs.b1nd.intern.mentomen.util.CommentDiffUtil
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import kr.hs.b1nd.intern.mentomen.view.fragment.DetailFragmentDirections
import kr.hs.b1nd.intern.mentomen.viewmodel.DetailViewModel
import retrofit2.Call
import retrofit2.Response

class CommentAdapter(private val userId: Int) : ListAdapter<Comment, CommentAdapter.CommentViewHolder>(CommentDiffUtil) {

    inner class CommentViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comment) {
            binding.item = item
            if (item.userId == userId) binding.btnMore.visibility = View.VISIBLE
            else binding.btnMore.visibility = View.GONE
            binding.btnMore.setOnClickListener {
                val call = RetrofitClient.commentService.deleteComment(item.commentId)

                call.enqueue(object : retrofit2.Callback<BaseResponse<Unit>> {
                    override fun onResponse(
                        call: Call<BaseResponse<Unit>>,
                        response: Response<BaseResponse<Unit>>
                    ) {

                    }

                    override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
                    }

                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_comment,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}