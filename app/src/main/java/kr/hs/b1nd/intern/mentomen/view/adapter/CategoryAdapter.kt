package kr.hs.b1nd.intern.mentomen.view.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ItemCategoryBinding
import kr.hs.b1nd.intern.mentomen.network.model.CategoryItem
import kr.hs.b1nd.intern.mentomen.view.adapter.callback.CategoryItemDiffUtilCallback

class CategoryAdapter(val context: Context, val action: (name: String) -> Unit) : ListAdapter<CategoryItem, CategoryAdapter.CategoryViewHolder>(CategoryItemDiffUtilCallback) {
    inner class CategoryViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryItem) {
            binding.info = item
            binding.tvCategoryName.setOnClickListener(View.OnClickListener {
                action.invoke(item.name)
            })
            if (item.isSelect)
                when (item.name) {
                    "Android" -> {
                        binding.tvCategoryName.background =
                            ContextCompat.getDrawable(context, R.drawable.category_item_green)
                        binding.tvCategoryName.setTextColor(ContextCompat.getColor(context,R.color.android))
                    }
                    "iOS" -> {
                        binding.tvCategoryName.background =
                            ContextCompat.getDrawable(context, R.drawable.category_item_gray)
                        binding.tvCategoryName.setTextColor(ContextCompat.getColor(context,R.color.iOS))
                    }
                    "Web" -> {
                        binding.tvCategoryName.background =
                            ContextCompat.getDrawable(context, R.drawable.category_item_yellow)
                        binding.tvCategoryName.setTextColor(ContextCompat.getColor(context,R.color.web))
                    }
                    "Server" -> {
                        binding.tvCategoryName.background =
                            ContextCompat.getDrawable(context, R.drawable.category_item_blue)
                        binding.tvCategoryName.setTextColor(ContextCompat.getColor(context,R.color.server))
                    }
                    "Design" -> {
                        binding.tvCategoryName.background =
                            ContextCompat.getDrawable(context, R.drawable.category_item_pink)
                        binding.tvCategoryName.setTextColor(ContextCompat.getColor(context,R.color.design))
                    }
                }
            else{
                binding.tvCategoryName.setTextColor(ContextCompat.getColor(context,R.color.black))
                binding.tvCategoryName.background =
                    ContextCompat.getDrawable(context, R.drawable.category_item_black)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }


}