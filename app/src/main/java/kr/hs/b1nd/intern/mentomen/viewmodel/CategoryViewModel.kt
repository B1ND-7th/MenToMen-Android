package kr.hs.b1nd.intern.mentomen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.model.CategoryItem

class CategoryViewModel: ViewModel() {
    val itemList = MutableLiveData<ArrayList<CategoryItem>>()

    init {
        val items: ArrayList<CategoryItem> = ArrayList()
        items.add(CategoryItem("Android",false))
        items.add(CategoryItem("iOS",false))
        items.add(CategoryItem("Web",false))
        items.add(CategoryItem("Server",false))
        items.add(CategoryItem("Design",false))

        itemList.value = items
    }

    fun setItemList(items: ArrayList<CategoryItem>) {
        itemList.value = items
    }
}