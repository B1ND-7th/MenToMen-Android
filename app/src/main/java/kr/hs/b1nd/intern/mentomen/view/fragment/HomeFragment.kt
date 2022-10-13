package kr.hs.b1nd.intern.mentomen.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentHomeBinding
import kr.hs.b1nd.intern.mentomen.view.activity.DetailActivity
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.view.adapter.HomeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        performViewModel()
        initHomeAdapter()
        observeViewModel()

        binding.btnNotification.setOnClickListener {
            homeViewModel.noticeStatus.value = "NONE"
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNoticeFragment())
        }

        binding.btnSerach.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }

        binding.logo.setOnClickListener {
            with(homeViewModel) {
                callPost()
                allTagsSelected()
                logoClickEvent.observe(viewLifecycleOwner) {
                    binding.rvHome.smoothScrollToPosition(0)
                }
            }
        }

        return binding.root
    }

    private fun observeViewModel() = with(homeViewModel) {
        itemList.observe(viewLifecycleOwner) { homeAdapter.submitList(it) }
        noticeStatus.observe(viewLifecycleOwner) {
            when (it) {
                "NONE" -> binding.btnNotification.setImageResource(R.drawable.ic_notification)
                "EXIST" -> binding.btnNotification.setImageResource(R.drawable.ic_notice_on)
            }
        }
        with(tagState.value!!) {
            when {
                isDesignChecked && isChecked -> callTagPost("DESIGN")
                isServerChecked && isChecked -> callTagPost("SERVER")
                isWebChecked && isChecked -> callTagPost("WEB")
                isAndroidChecked && isChecked -> callTagPost("ANDROID")
                isiOSChecked && isChecked -> callTagPost("IOS")
                else -> callPost()
            }
        }
    }

    private fun initHomeAdapter() {
        homeAdapter = HomeAdapter {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("postId", it.postId)
            startActivity(intent)
        }
        binding.rvHome.adapter = homeAdapter
    }

    private fun performViewModel() {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.vm = homeViewModel
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.callPost()
        homeViewModel.callNotice()
    }
}
