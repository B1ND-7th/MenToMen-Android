package kr.hs.b1nd.intern.mentomen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import kr.hs.b1nd.intern.mentomen.network.model.PostSubmitDto
import retrofit2.Call
import retrofit2.Response

class HomeViewModel: ViewModel() {
    val onCLickDesignEvent = SingleLiveEvent<Any>()
    val onCLickWebEvent = SingleLiveEvent<Any>()
    val onCLickServerEvent = SingleLiveEvent<Any>()
    val onCLickAndroidEvent = SingleLiveEvent<Any>()
    val onCLickIosEvent = SingleLiveEvent<Any>()

    val itemList = MutableLiveData<List<Post>>()

    init {
        val call = RetrofitClient.postService.readAll()

        call.enqueue(object : retrofit2.Callback<BaseResponse<List<Post>>> {
            override fun onResponse(call: Call<BaseResponse<List<Post>>>, response: Response<BaseResponse<List<Post>>>) {
                if (response.isSuccessful) {
                    itemList.value = response.body()?.data ?: emptyList()
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<Post>>>, t: Throwable) {

            }

        })
    }
    fun onClickDesignBtn() {
        callTagService("DESIGN", onCLickDesignEvent)
    }

    fun onClickWebBtn() {
        callTagService("WEB", onCLickWebEvent)
    }

    fun onClickServerBtn() {
        callTagService("SERVER", onCLickServerEvent)
    }

    fun onClickAndroidBtn() {
        callTagService("ANDROID", onCLickAndroidEvent)
    }

    fun onClickIosBtn() {
        callTagService("IOS", onCLickIosEvent)
    }

    private fun callTagService(tag: String, singleLiveEvent: SingleLiveEvent<Any>) {
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