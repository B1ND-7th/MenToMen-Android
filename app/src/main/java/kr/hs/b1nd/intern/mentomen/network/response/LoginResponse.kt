package kr.hs.b1nd.intern.mentomen.network.response

data class LoginResponse (
    val name: String,
    val profileImage: String,
    val location: String
    )