package kr.hs.b1nd.intern.mentomen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.network.model.PostSubmitDto
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Response

class AddViewModel : ViewModel() {
    val onClickConfirmEvent = SingleLiveEvent<Any>()
    val onClickImageEvent = SingleLiveEvent<Any>()
    val onCLickDesignEvent = SingleLiveEvent<Any>()
    val onCLickWebEvent = SingleLiveEvent<Any>()
    val onCLickServerEvent = SingleLiveEvent<Any>()
    val onCLickAndroidEvent = SingleLiveEvent<Any>()
    val onCLickIosEvent = SingleLiveEvent<Any>()

    val content = MutableLiveData<String>()
    val imgUrl = MutableLiveData<String>()

    private var tag: String = ""

    fun onClickImage() {
        onClickImageEvent.call()
    }

    fun onCLickConfirm() {
        val call = RetrofitClient.postService.submitPost(
            PostSubmitDto(content.value ?: "", imgUrl.value ?: "", tag)
        )

        call.enqueue(object : retrofit2.Callback<BaseResponse<Any>> {
            override fun onResponse(call: Call<BaseResponse<Any>>, response: Response<BaseResponse<Any>>) {
                if (response.isSuccessful) {

                    onClickConfirmEvent.call()
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {

            }

        })
    }

    fun onClickDesignBtn() {
        tag = "DESIGN"
        onCLickDesignEvent.call()
    }

    fun onClickWebBtn() {
        tag = "WEB"
        onCLickWebEvent.call()
    }

    fun onClickServerBtn() {
        tag = "SERVER"
        onCLickServerEvent.call()
    }

    fun onClickAndroidBtn() {
        tag = "ANDROID"
        onCLickAndroidEvent.call()
    }

    fun onClickIosBtn() {
        tag = "IOS"
        onCLickIosEvent.call()
    }
}