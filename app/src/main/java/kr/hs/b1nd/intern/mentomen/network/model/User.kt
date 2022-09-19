package kr.hs.b1nd.intern.mentomen.network.model

data class User(
    val email: String,
    val name: String,
    val profileImage: String,
    val roles: String,
    val stdInfo: StdInfo,
    val userId: Int
)
