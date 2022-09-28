package kr.hs.b1nd.intern.mentomen.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    companion object {
        const val IS_LOGIN = "IS_LOGIN"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun isLogin(): Boolean {
        return prefs.getBoolean(IS_LOGIN, false)
    }

    fun autoLogin() {
        prefs.edit().putBoolean(IS_LOGIN, true).apply()
    }

    fun logout() {
        prefs.edit().putBoolean(IS_LOGIN, false).apply()
    }
}
