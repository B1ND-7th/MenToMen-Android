package kr.hs.b1nd.intern.mentomen.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentHomeBinding
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.view.adapter.HomeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
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
        (activity as MainActivity).hasBottomBar(true)

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

        binding.apply {
            refreshLayout.setOnRefreshListener {
                changeTags()
                refreshLayout.isRefreshing = false
            }
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

    private fun observeViewModel() {
        with(homeViewModel) {
            itemList.observe(viewLifecycleOwner) { homeAdapter.submitList(it) }
            noticeStatus.observe(viewLifecycleOwner) {
                when (it) {
                    "NONE" -> binding.btnNotification.setImageResource(R.drawable.ic_notification)
                    "EXIST" -> binding.btnNotification.setImageResource(R.drawable.ic_notice_on)
                }
            }
            changeTags()
        }
    }

    private fun initHomeAdapter() {
        homeAdapter = HomeAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.postId)
            findNavController().navigate(action)
        }
        binding.rvHome.adapter = homeAdapter
    }

    private fun performViewModel() {
        binding.vm = homeViewModel
        binding.lifecycleOwner = this
    }

    private fun changeTags() = with(homeViewModel) {
        with(tagState.value!!) {
            when {
                isDesignChecked && isChecked -> callTagPost("DESIGN")
                isServerChecked && isChecked -> callTagPost("SERVER")
                isWebChecked && isChecked -> callTagPost("WEB")
                isAndroidChecked && isChecked -> callTagPost("ANDROID")
                isiOSChecked && isChecked -> callTagPost("IOS")
                else -> {
                    callPost()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.callNotice()
    }


}
