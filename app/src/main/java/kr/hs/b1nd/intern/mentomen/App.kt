package kr.hs.b1nd.intern.mentomen

import android.app.Application
import kr.hs.b1nd.intern.mentomen.util.PreferenceUtil

class App: Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
    }
}