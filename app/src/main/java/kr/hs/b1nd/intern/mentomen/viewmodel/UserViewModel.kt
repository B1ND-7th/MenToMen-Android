package kr.hs.b1nd.intern.mentomen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.network.model.User
import kr.hs.b1nd.intern.mentomen.view.adapter.HomeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.state.GetMyPostState
import retrofit2.Call
import retrofit2.Response

class UserViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val grade = MutableLiveData<Int>()
    val profileImage = MutableLiveData<String>()
    val number =  MutableLiveData<Int>()
    val room =  MutableLiveData<Int>()

    val _getMyPostState = MutableSharedFlow<GetMyPostState>()
    val getMyPostState : SharedFlow<GetMyPostState> = _getMyPostState


    init {
        val call = RetrofitClient.userService.getUserInfo()

        call.enqueue(object : retrofit2.Callback<BaseResponse<User>> {
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

        callPost.equals(object : retrofit2.Callback<BaseResponse<List<Post>>>{
            override fun onResponse(
                call: Call<BaseResponse<List<Post>>>,
                response: Response<BaseResponse<List<Post>>>
            ) {
                if (response.isSuccessful){
                    viewModelScope.launch {response.body()?.data ?: emptyList()}

                }
            }
            override fun onFailure(call: Call<BaseResponse<List<Post>>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
    companion object{
        const val EVENT_SET_MY_POST = 1
    }


}