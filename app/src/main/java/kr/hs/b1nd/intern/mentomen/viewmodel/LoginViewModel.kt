package kr.hs.b1nd.intern.mentomen.viewmodel

import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent

class LoginViewModel: ViewModel() {

    val onClickLoginEvent = SingleLiveEvent<Any>()

    fun onClickLogin() {
        onClickLoginEvent.call()
    }

}