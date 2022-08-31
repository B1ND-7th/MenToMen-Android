package kr.hs.b1nd.intern.mentomen.network.response

data class MTMLoginResponse(
    val accessToken: String,
    val refreshToken: String
)
