package kr.hs.b1nd.intern.mentomen.network.model

data class PostSubmitDto(
    val content : String,
    val imgUrls : List<ImgUrl?> = emptyList(),
    val tag : String
)
