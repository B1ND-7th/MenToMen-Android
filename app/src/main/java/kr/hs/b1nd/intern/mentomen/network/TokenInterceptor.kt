package kr.hs.b1nd.intern.mentomen.network

import android.util.Log
import kr.hs.b1nd.intern.mentomen.App
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONException

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${App.prefs.getString("accessToken", "")}")
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
            .addHeader("Authorization", "Bearer ${App.prefs.getString("refreshToken", "")}")
            .build()

        return chain.proceed(request)
    }
}