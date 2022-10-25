package kr.hs.b1nd.intern.mentomen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.App
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.ImgUrl
import kr.hs.b1nd.intern.mentomen.network.model.PostSubmitDto
import kr.hs.b1nd.intern.mentomen.network.response.ErrorResponse
import kr.hs.b1nd.intern.mentomen.network.response.TokenResponse
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import kr.hs.b1nd.intern.mentomen.util.TagState
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class AddViewModel : ViewModel() {
    val onClickConfirmEvent = SingleLiveEvent<Unit>()
    val onClickImageEvent = SingleLiveEvent<Unit>()
    val successConfirmEvent = SingleLiveEvent<Unit>()
    val successImageEvent = SingleLiveEvent<Unit>()
    val errorImageEvent = SingleLiveEvent<Unit>()

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
    val imgFile = MutableLiveData<ArrayList<MultipartBody.Part?>>(arrayListOf())
    val imgUrl = MutableLiveData<List<ImgUrl?>>(emptyList())
    val tag = MutableLiveData("")

    fun onClickImage() {
        onClickImageEvent.call()
    }

    fun loadImage() {
        val call = RetrofitClient.fileService.loadImage(imgFile.value!!)

        call.enqueue(object : retrofit2.Callback<BaseResponse<List<ImgUrl?>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<ImgUrl?>>>,
                response: Response<BaseResponse<List<ImgUrl?>>>
            ) {
                if (response.isSuccessful) {
                    imgUrl.value = response.body()?.data ?: emptyList()
                    successImageEvent.call()
                }
                else if (response.code() == 413){
                    errorImageEvent.call()
                }
            }
            override fun onFailure(call: Call<BaseResponse<List<ImgUrl?>>>, t: Throwable) {
            }
        })
    }

    fun submitPost() {
        if (tag.value != "" && content.value != "") {
            val call = RetrofitClient.postService.submitPost(
                PostSubmitDto(content.value!!, imgUrl.value ?: emptyList(), tag.value!!)
            )

            call.enqueue(object : retrofit2.Callback<BaseResponse<Unit>> {
                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>
                ) {
                    if (response.isSuccessful)
                        successConfirmEvent.call()
                    else {
                        val errorBody = response.errorBody()?.let {
                            RetrofitClient.retrofit.responseBodyConverter<ErrorResponse>(
                                ErrorResponse::class.java, ErrorResponse::class.java.annotations
                            ).convert(it)
                        }
                        if (errorBody?.status == 500) {
                            refreshToken()
                            onCLickConfirm()
                        }
                    }
                }
                override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
                }
            })
        }
    }

    fun onCLickConfirm() {
        onClickConfirmEvent.call()
        if (imgFile.value.isNullOrEmpty()) submitPost()
        else loadImage()
    }

    private fun refreshToken() {
        val call = RetrofitClient.tokenService.refreshToken()

        call.enqueue(object : retrofit2.Callback<BaseResponse<TokenResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<TokenResponse>>,
                response: Response<BaseResponse<TokenResponse>>
            ) {
                if (response.isSuccessful)
                    App.prefs.setString("accessToken", response.body()?.data!!.accessToken)
            }
            override fun onFailure(call: Call<BaseResponse<TokenResponse>>, t: Throwable) {
            }
        })
    }

    fun onClickDesignBtn() {
        tag.value = "DESIGN"
        tagState.value = TagState(
            isDesignChecked = true,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = false,
            isChecked = true
        )
    }

    fun onClickWebBtn() {
        tag.value = "WEB"
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = true,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = false,
            isChecked = true
        )
    }

    fun onClickServerBtn() {
        tag.value = "SERVER"
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = true,
            isiOSChecked = false,
            isChecked = true
        )
    }

    fun onClickAndroidBtn() {
        tag.value = "ANDROID"
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = true,
            isServerChecked = false,
            isiOSChecked = false,
            isChecked = true
        )
    }

    fun onClickIosBtn() {
        tag.value = "IOS"
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = true,
            isChecked = true
        )
    }
}