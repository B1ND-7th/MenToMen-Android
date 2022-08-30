package kr.hs.b1nd.intern.mentomen.network.response

data class LoginResponse (
    val status: Int,
    val message: String,
    val data: CodeDatas

)

data class CodeDatas (
    val name: String,
    val profileImage: String,
    val location: String
    )