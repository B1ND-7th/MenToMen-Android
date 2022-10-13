package kr.hs.b1nd.intern.mentomen.network.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
