package kr.hs.b1nd.intern.mentomen.view.adapter.callback

import androidx.recyclerview.widget.DiffUtil
import kr.hs.b1nd.intern.mentomen.network.model.CategoryItem

object CategoryItemDiffUtilCallback : DiffUtil.ItemCallback<CategoryItem>() {

    override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
        return oldItem == newItem
    }

}