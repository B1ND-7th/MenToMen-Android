package kr.hs.b1nd.intern.mentomen.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.NoticeStatus
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.network.model.StdInfo
import kr.hs.b1nd.intern.mentomen.network.model.User
import retrofit2.Call
import retrofit2.Response

class UserViewModel : ViewModel() {

    val noticeStatus = MutableLiveData("NONE")
    val email = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val stdInfo = MutableLiveData<StdInfo>()
    val profileImage = MutableLiveData<String>()
    val itemList = MutableLiveData<List<Post>>()

    fun callUser() {
        val call = RetrofitClient.userService.getUserInfo()

        call.enqueue(object : retrofit2.Callback<BaseResponse<User>> {
            override fun onResponse(
                call: Call<BaseResponse<User>>,
                response: Response<BaseResponse<User>>
            ) {
                if (response.isSuccessful) {
                    email.value = response.body()?.data!!.email
                    name.value = response.body()?.data!!.name
                    stdInfo.value = response.body()?.data!!.stdInfo
                    profileImage.value = response.body()?.data!!.profileImage
                }
            }
            override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {
            }
        })
    }

    fun callPost() {
        val call = RetrofitClient.userService.getMyPost()

        call.enqueue(object : retrofit2.Callback<BaseResponse<List<Post>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<Post>>>,
                response: Response<BaseResponse<List<Post>>>
            ) {
                if (response.isSuccessful)
                    itemList.value = response.body()?.data ?: emptyList()
            }

            override fun onFailure(call: Call<BaseResponse<List<Post>>>, t: Throwable) {
            }
        })
    }

    fun callNotice() {
        val call = RetrofitClient.noticeService.checkNotice()

        call.enqueue(object : retrofit2.Callback<BaseResponse<NoticeStatus>> {
            override fun onResponse(
                call: Call<BaseResponse<NoticeStatus>>,
                response: Response<BaseResponse<NoticeStatus>>
            ) {
                if (response.isSuccessful) {
                    Log.d("test123", "onResponse: call")
                    noticeStatus.value = response.body()?.data!!.noticeStatus
                }
            }

            override fun onFailure(call: Call<BaseResponse<NoticeStatus>>, t: Throwable) {
            }

        })
    }
}