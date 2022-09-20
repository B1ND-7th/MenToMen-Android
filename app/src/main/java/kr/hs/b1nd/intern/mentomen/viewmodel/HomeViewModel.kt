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
        if (tagState.value!!.isDesignChecked) {
            tagState.value = TagState(
                isDesignChecked = true,
                isWebChecked = false,
                isAndroidChecked = false,
                isServerChecked = false,
                isiOSChecked = false
            )
            callTagPost("DESIGN", onClickDesignEvent)
        }
        else {
            allTagsSelected()
            callPost()
        }
    }

    fun onClickWebBtn() {
        if (tagState.value!!.isWebChecked) {
            tagState.value = TagState(
                isDesignChecked = false,
                isWebChecked = true,
                isAndroidChecked = false,
                isServerChecked = false,
                isiOSChecked = false
            )
            callTagPost("WEB", onCLickWebEvent)
        }
        else {
            allTagsSelected()
            callPost()
        }
    }

    fun onClickServerBtn() {
        if (tagState.value!!.isServerChecked) {
            tagState.value = TagState(
                isDesignChecked = false,
                isWebChecked = false,
                isAndroidChecked = false,
                isServerChecked = true,
                isiOSChecked = false
            )
            callTagPost("SERVER", onCLickServerEvent)
        }
        else {
            allTagsSelected()
            callPost()
        }
    }

    fun onClickAndroidBtn() {
        if (tagState.value!!.isAndroidChecked) {
            tagState.value = TagState(
                isDesignChecked = false,
                isWebChecked = false,
                isAndroidChecked = true,
                isServerChecked = false,
                isiOSChecked = false
            )
            callTagPost("ANDROID", onCLickAndroidEvent)
        }
        else {
            allTagsSelected()
            callPost()
        }
    }

    fun onClickIosBtn() {
        if (tagState.value!!.isiOSChecked) {
            tagState.value = TagState(
                isDesignChecked = false,
                isWebChecked = false,
                isAndroidChecked = false,
                isServerChecked = false,
                isiOSChecked = true
            )
            callTagPost("IOS", onCLickIosEvent)
        }
        else {
            allTagsSelected()
            callPost()
        }
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
    private fun allTagsSelected() {
        tagState.value = TagState(
            isDesignChecked = true,
            isWebChecked = true,
            isAndroidChecked = true,
            isServerChecked = true,
            isiOSChecked = true
        )
    }
}
