package kr.hs.b1nd.intern.mentomen.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentHomeBinding
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.util.TagState
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
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        performViewModel()
        with(homeViewModel.tagState.value!!) {
            with(homeViewModel) {
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
        observeViewModel()

        (activity as MainActivity).hasTopBar()
        (activity as MainActivity).hasBottomBar()


        return binding.root
    }


    private fun observeViewModel() {
        with(homeViewModel) {
            itemList.observe(viewLifecycleOwner) {
                initHomeAdapter(it)
            }
        }
    }


    private fun initHomeAdapter(items: List<Post>) {
        homeAdapter = HomeAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.postId)
            findNavController().navigate(action)
        }
        homeAdapter.submitList(items)
        binding.rvHome.adapter = homeAdapter
    }


    private fun performViewModel() {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.vm = homeViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

}
