package kr.hs.b1nd.intern.mentomen.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kr.hs.b1nd.intern.mentomen.App
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentUserBinding
import kr.hs.b1nd.intern.mentomen.view.activity.LoginActivity
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.view.adapter.HomeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.UserViewModel

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user,
            container,
            false
        )
        (activity as MainActivity).hasBottomBar(true)

        performViewModel()
        initHomeAdapter()
        observeViewModel()

        binding.btnNotification.setOnClickListener {
            userViewModel.noticeStatus.value = "NONE"
            findNavController().navigate(UserFragmentDirections.actionUserFragmentToNoticeFragment())
        }

        binding.btnLogout.setOnClickListener {
            App.prefs.logout()
            App.prefs.deleteToken()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }

        return binding.root

    }

    private fun performViewModel() {
        binding.vm = userViewModel
        binding.lifecycleOwner = this
    }

    private fun initHomeAdapter() {
        homeAdapter = HomeAdapter {
            val action = UserFragmentDirections.actionUserFragmentToDetailFragment(it.postId)
            findNavController().navigate(action)
        }
        binding.rvMyPage.adapter = homeAdapter
    }

    private fun observeViewModel() = with(userViewModel) {
        callUser()
        itemList.observe(viewLifecycleOwner) { homeAdapter.submitList(it) }
        noticeStatus.observe(viewLifecycleOwner) {
            Log.d("test123", "observeViewModel: asdfasf")
            when (it) {
                "NONE" -> binding.btnNotification.setImageResource(R.drawable.ic_notification)
                "EXIST" -> binding.btnNotification.setImageResource(R.drawable.ic_notice_on)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        userViewModel.callPost()
        userViewModel.callNotice()
    }
}