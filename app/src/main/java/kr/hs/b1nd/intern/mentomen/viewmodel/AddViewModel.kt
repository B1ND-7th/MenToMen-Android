package kr.hs.b1nd.intern.mentomen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.network.model.PostSubmitDto
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import kr.hs.b1nd.intern.mentomen.util.TagState
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

    val tagState = MutableLiveData(TagState(
        isDesignChecked = false,
        isWebChecked = false,
        isAndroidChecked = false,
        isServerChecked = false,
        isiOSChecked = false
    ))

    val content = MutableLiveData("")
    val imgUrl = MutableLiveData<String>()

    private var tag: String = ""

    fun onClickImage() {
        onClickImageEvent.call()
    }

    fun onCLickConfirm() {
        if (content.value != "" && tag != "") {
            val call = RetrofitClient.postService.submitPost(
                PostSubmitDto(content.value ?: "", imgUrl.value, tag)
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
    }

    fun onClickDesignBtn() {
        tag = "DESIGN"
        tagState.value = TagState(
            isDesignChecked = true,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = false
        )
        onCLickDesignEvent.call()
    }

    fun onClickWebBtn() {
        tag = "WEB"
        tagState.value  = TagState(
            isDesignChecked = false,
            isWebChecked = true,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = false
        )
        onCLickWebEvent.call()
    }

    fun onClickServerBtn() {
        tag = "SERVER"
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = true,
            isiOSChecked = false
        )
        onCLickServerEvent.call()
    }

    fun onClickAndroidBtn() {
        tag = "ANDROID"
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = true,
            isServerChecked = false,
            isiOSChecked = false
        )
        onCLickAndroidEvent.call()
    }

    fun onClickIosBtn() {
        tag = "IOS"
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = true
        )
        onCLickIosEvent.call()
    }
}