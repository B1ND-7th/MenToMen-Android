package kr.hs.b1nd.intern.mentomen.viewmodel

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
    val logoClickEvent = SingleLiveEvent<Unit>()

    val itemList = MutableLiveData<List<Post>>()
    val tagState = MutableLiveData(TagState())

    fun onClickDesignBtn() {
        if (tagState.value!!.isDesignChecked && tagState.value!!.isChecked) {
            allTagsSelected()
            callPost()
        }
        else if (tagState.value!!.isDesignChecked || (!tagState.value!!.isDesignChecked && tagState.value!!.isChecked)) {
            tagState.value = TagState(
                isDesignChecked = true,
                isWebChecked = false,
                isAndroidChecked = false,
                isServerChecked = false,
                isiOSChecked = false,
                isChecked = true
            )
            callTagPost("DESIGN")
        }
    }

    fun onClickWebBtn() {
        if (tagState.value!!.isWebChecked && tagState.value!!.isChecked) {
            allTagsSelected()
            callPost()
        }
        else if (tagState.value!!.isWebChecked || (!tagState.value!!.isWebChecked && tagState.value!!.isChecked)) {
            tagState.value = TagState(
                isDesignChecked = false,
                isWebChecked = true,
                isAndroidChecked = false,
                isServerChecked = false,
                isiOSChecked = false,
                isChecked = true
            )
            callTagPost("WEB")
        }
    }

    fun onClickServerBtn() {
        if (tagState.value!!.isServerChecked && tagState.value!!.isChecked) {
            allTagsSelected()
            callPost()
        }
        else if (tagState.value!!.isServerChecked || (!tagState.value!!.isServerChecked && tagState.value!!.isChecked)) {
            tagState.value = TagState(
                isDesignChecked = false,
                isWebChecked = false,
                isAndroidChecked = false,
                isServerChecked = true,
                isiOSChecked = false,
                isChecked = true
            )
            callTagPost("SERVER")
        }
    }

    fun onClickAndroidBtn() {
        if (tagState.value!!.isAndroidChecked && tagState.value!!.isChecked) {
            allTagsSelected()
            callPost()
        }
        else if (tagState.value!!.isAndroidChecked || (!tagState.value!!.isAndroidChecked && tagState.value!!.isChecked)) {
            tagState.value = TagState(
                isDesignChecked = false,
                isWebChecked = false,
                isAndroidChecked = true,
                isServerChecked = false,
                isiOSChecked = false,
                isChecked = true
            )
            callTagPost("ANDROID")
        }
    }

    fun onClickIosBtn() {
        if (tagState.value!!.isiOSChecked && tagState.value!!.isChecked) {
            allTagsSelected()
            callPost()
        }
        else if (tagState.value!!.isiOSChecked || (!tagState.value!!.isiOSChecked && tagState.value!!.isChecked)) {
            tagState.value = TagState(
                isDesignChecked = false,
                isWebChecked = false,
                isAndroidChecked = false,
                isServerChecked = false,
                isiOSChecked = true,
                isChecked = true
            )
            callTagPost("IOS")
        }
    }

    fun callPost() {
        val call = RetrofitClient.postService.readPost()

        call.enqueue(object : retrofit2.Callback<BaseResponse<List<Post>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<Post>>>,
                response: Response<BaseResponse<List<Post>>>
            ) {
                if (response.isSuccessful) {
                    itemList.value = response.body()?.data ?: emptyList()
                    logoClickEvent.call()
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<Post>>>, t: Throwable) {

            }

        })
    }

    fun callTagPost(tag: String) {
        val call = RetrofitClient.postService.readTagPost(tag)

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
    fun allTagsSelected() {
        tagState.value = TagState(
            isDesignChecked = true,
            isWebChecked = true,
            isAndroidChecked = true,
            isServerChecked = true,
            isiOSChecked = true,
            isChecked = false
        )
    }
}
