package kr.hs.b1nd.intern.mentomen.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ActivityMainBinding
import kr.hs.b1nd.intern.mentomen.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        with(binding) {
            bottomNavigation.setupWithNavController(navHostFragment.findNavController())

            bottomNavigation.background = null

            addButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddActivity::class.java))
            }
        }
    }
    fun hasBottomBar(hasBottomBar: Boolean = true) {
        binding.bottomNavigation.visibility = if (hasBottomBar) View.VISIBLE else View.GONE
        binding.bottomAppBar.visibility = if (hasBottomBar) View.VISIBLE else View.GONE
        binding.addButton.visibility = if (hasBottomBar) View.VISIBLE else View.GONE
    }
}