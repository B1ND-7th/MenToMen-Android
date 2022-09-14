package kr.hs.b1nd.intern.mentomen.network.request

data class LoginRequest (
    val id: String,
    val pw: String,
    val clientId: String,
    val redirectUrl: String,
    val state: String? = null
)