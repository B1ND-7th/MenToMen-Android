package kr.hs.b1nd.intern.mentomen.view.activity

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kr.hs.b1nd.intern.mentomen.App
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ActivityMainBinding
import kr.hs.b1nd.intern.mentomen.view.fragment.DetailFragment
import kr.hs.b1nd.intern.mentomen.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.findNavController())

    }
    fun hasBottomBar(hasBottomBar: Boolean = true) {
        binding.bottomNavigation.visibility = if (hasBottomBar) View.VISIBLE else View.GONE
    }
}