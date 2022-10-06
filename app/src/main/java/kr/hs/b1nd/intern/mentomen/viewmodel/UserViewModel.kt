package kr.hs.b1nd.intern.mentomen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.network.model.StdInfo
import kr.hs.b1nd.intern.mentomen.network.model.User
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val stdInfo = MutableLiveData<StdInfo>()
    val profileImage = MutableLiveData<String>()
    val itemList = MutableLiveData<List<Post>>()

    fun callUser() {
        val call = RetrofitClient.userService.getUserInfo()

        call.enqueue(object : Callback<BaseResponse<User>> {
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

        call.enqueue(object : Callback<BaseResponse<List<Post>>> {
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
}