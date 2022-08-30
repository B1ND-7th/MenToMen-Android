package kr.hs.b1nd.intern.mentomen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.model.ImageItem

class HomeViewModel: ViewModel() {
    val itemList = MutableLiveData<ArrayList<ImageItem>>()

    init {
        val items: ArrayList<ImageItem> = ArrayList()
        items.add(ImageItem("ic_android.xml", "조상영", "8월 29일", "안녕하세요 조상영입니다.", "https://upload.wikimedia.org/wikipedia/commons/0/0f/IU_posing_for_Marie_Claire_Korea_March_2022_issue_03.jpg"))
        items.add(ImageItem("ic_ios.xml", "조상영", "8월 29일", "안녕하세요 조상영입니다.", "https://upload.wikimedia.org/wikipedia/commons/0/0f/IU_posing_for_Marie_Claire_Korea_March_2022_issue_03.jpg"))
        items.add(ImageItem("ic_design.xml", "조상영", "8월 29일", "안녕하세요 조상영입니다.", "https://upload.wikimedia.org/wikipedia/commons/0/0f/IU_posing_for_Marie_Claire_Korea_March_2022_issue_03.jpg"))
        items.add(ImageItem("ic_server.xml", "조상영", "8월 29일", "안녕하세요 조상영입니다.", "https://upload.wikimedia.org/wikipedia/commons/0/0f/IU_posing_for_Marie_Claire_Korea_March_2022_issue_03.jpg"))
        items.add(ImageItem("ic_web.xml", "조상영", "8월 29일", "안녕하세요 조상영입니다.", "https://upload.wikimedia.org/wikipedia/commons/0/0f/IU_posing_for_Marie_Claire_Korea_March_2022_issue_03.jpg"))

        itemList.value = items
    }

    fun setItemList(items: ArrayList<ImageItem>) {
        itemList.value = items
    }
}