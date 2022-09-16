package kr.hs.b1nd.intern.mentomen.network

import kr.hs.b1nd.intern.mentomen.App
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONException

class TokenInterceptor : Interceptor {

    private val accessToken = App.prefs.getString("accessToken", "")
    private val refreshToken = App.prefs.getString("refreshToken", "")
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(request)

        when (response.code) {
            200 -> return response
            401 -> {
                try {
                    response.close()
                    return makeTokenRefreshCall(chain)
                } catch (e:JSONException) {
                    e.printStackTrace()
                }
            }
        }

        return response
    }

    private fun makeTokenRefreshCall(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $refreshToken")
            .build()


        return chain.proceed(request)
    }
}