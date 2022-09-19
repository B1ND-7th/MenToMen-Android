package kr.hs.b1nd.intern.mentomen.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import kr.hs.b1nd.intern.mentomen.util.TagState
import retrofit2.Call
import retrofit2.Response

class HomeViewModel: ViewModel() {
    val itemList = MutableLiveData<List<Post>>()

    val onClickDesignEvent = SingleLiveEvent<Any>()
    val onCLickWebEvent = SingleLiveEvent<Any>()
    val onCLickServerEvent = SingleLiveEvent<Any>()
    val onCLickAndroidEvent = SingleLiveEvent<Any>()
    val onCLickIosEvent = SingleLiveEvent<Any>()

    val tagState = MutableLiveData(TagState())

    fun onClickDesignBtn() {
        tagState.value = TagState(
            isDesignChecked = true,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = false
        )
        callTagPost("DESIGN", onClickDesignEvent)
    }

    fun onClickWebBtn() {
        tagState.value  = TagState(
            isDesignChecked = false,
            isWebChecked = true,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = false
        )
        callTagPost("WEB", onCLickWebEvent)
    }

    fun onClickServerBtn() {
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = true,
            isiOSChecked = false
        )
        callTagPost("SERVER", onCLickServerEvent)
    }

    fun onClickAndroidBtn() {
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = true,
            isServerChecked = false,
            isiOSChecked = false
        )
        callTagPost("ANDROID", onCLickAndroidEvent)
    }

    fun onClickIosBtn() {
        tagState.value = TagState(
            isDesignChecked = false,
            isWebChecked = false,
            isAndroidChecked = false,
            isServerChecked = false,
            isiOSChecked = true
        )
        callTagPost("IOS", onCLickIosEvent)
    }

    fun callPost() {
        val call = RetrofitClient.postService.readAll()

        call.enqueue(object : retrofit2.Callback<BaseResponse<List<Post>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<Post>>>,
                response: Response<BaseResponse<List<Post>>>
            ) {
                if (response.isSuccessful) {
                    itemList.value = response.body()?.data ?: emptyList()
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<Post>>>, t: Throwable) {

            }

        })
    }

    private fun callTagPost(tag: String, singleLiveEvent: SingleLiveEvent<Any>) {
        val call = RetrofitClient.postService.readTag(tag)

        call.enqueue(object : retrofit2.Callback<BaseResponse<List<Post>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<Post>>>,
                response: Response<BaseResponse<List<Post>>>
            ) {
                if (response.isSuccessful) {
                    itemList.value = response.body()?.data ?: emptyList()
                    singleLiveEvent.call()
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<Post>>>, t: Throwable) {

            }

        })
    }
}
