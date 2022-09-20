package kr.hs.b1nd.intern.mentomen.viewmodel

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.network.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val grade = MutableLiveData<Int>()
    val profileImage = MutableLiveData<String>()
    val number =  MutableLiveData<Int>()
    val room =  MutableLiveData<Int>()
    val list = MutableLiveData<List<Post>>()


    init {
        val call = RetrofitClient.userService.getUserInfo()

        call.enqueue(object : Callback<BaseResponse<User>> {
            override fun onResponse(
                call: Call<BaseResponse<User>>,
                response: Response<BaseResponse<User>>
            ) {
                if (response.isSuccessful) {
                    email.value = response.body()?.data!!.email
                    name.value=response.body()?.data!!.name
                    grade.value=response.body()?.data!!.stdInfo.grade
                    profileImage.value=response.body()?.data!!.profileImage
                    number.value=response.body()?.data!!.stdInfo.number
                    room.value=response.body()?.data!!.stdInfo.room
                }
            }

            override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {

            }

        })

        val callPost = RetrofitClient.userService.getMyPost()

        callPost.equals(object : Callback<BaseResponse<List<Post>>>{
            override fun onResponse(
                call: Call<BaseResponse<List<Post>>>,
                response: Response<BaseResponse<List<Post>>>
            ) {
                if (response.isSuccessful){
                    list.value = response.body()?.data ?: emptyList()

                }
            }
            override fun onFailure(call: Call<BaseResponse<List<Post>>>, t: Throwable) {

            }

        })

    }

    companion object{
        const val EVENT_SET_MY_POST = 1
    }


}