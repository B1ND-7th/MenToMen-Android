package kr.hs.b1nd.intern.mentomen.viewmodel.state

import kr.hs.b1nd.intern.mentomen.network.model.Post
import java.lang.AssertionError

data class GetMyPostState(
    val list : List<Post>,
    val error: String = "error"
)
