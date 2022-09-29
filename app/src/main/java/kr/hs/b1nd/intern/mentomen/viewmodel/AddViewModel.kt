package kr.hs.b1nd.intern.mentomen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.ImageFile
import kr.hs.b1nd.intern.mentomen.network.model.PostSubmitDto
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import kr.hs.b1nd.intern.mentomen.util.TagState
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class AddViewModel : ViewModel() {
    val onClickConfirmEvent = SingleLiveEvent<Unit>()
    val onClickImageEvent = SingleLiveEvent<Unit>()
    val successConfirmEvent = SingleLiveEvent<Unit>()

    val tagState = MutableLiveData(
        TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = false,
            isChecked = false
        )
    )

    val content = MutableLiveData("")
    val imgFile = MutableLiveData<MultipartBody.Part?>()
    val imgUrl = MutableLiveData<String?>(null)
    val tag = MutableLiveData("")

    fun onClickImage() {
        onClickImageEvent.call()
    }

    fun loadImage() {
        val call = RetrofitClient.fileService.loadImage(imgFile.value)

        call.enqueue(object : retrofit2.Callback<BaseResponse<ImageFile>> {
            override fun onResponse(
                call: Call<BaseResponse<ImageFile>>,
                response: Response<BaseResponse<ImageFile>>
            ) {
                if (response.isSuccessful)
                    imgUrl.value = response.body()?.data?.imgUrl
            }

            override fun onFailure(call: Call<BaseResponse<ImageFile>>, t: Throwable) {

            }

        })

    }

    fun onCLickConfirm() {
        onClickConfirmEvent.call()
        if (tag.value != "" && content.value != "") {
            val call = RetrofitClient.postService.submitPost(
                PostSubmitDto(content.value!!, imgUrl.value, tag.value!!)
            )

            call.enqueue(object : retrofit2.Callback<BaseResponse<Unit>> {
                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>
                ) {
                    if (response.isSuccessful) {
                        successConfirmEvent.call()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {

                }
            })
        }
    }

    fun onClickDesignBtn() {
        tag.value = "DESIGN"
        tagState.value = TagState(
            isDesignChecked = true,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = false
        )
    }

    fun onClickWebBtn() {
        tag.value = "WEB"
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = true,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = false
        )
    }

    fun onClickServerBtn() {
        tag.value = "SERVER"
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = true,
            isiOSChecked = false
        )
    }

    fun onClickAndroidBtn() {
        tag.value = "ANDROID"
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = true,
            isServerChecked = false,
            isiOSChecked = false
        )
    }

    fun onClickIosBtn() {
        tag.value = "IOS"
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = true
        )
    }
}