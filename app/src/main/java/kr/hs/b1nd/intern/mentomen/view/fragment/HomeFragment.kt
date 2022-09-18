package kr.hs.b1nd.intern.mentomen.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentHomeBinding
import kr.hs.b1nd.intern.mentomen.network.model.Post
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
        observeViewModel()

        (activity as MainActivity).hasTopBar()
        (activity as MainActivity).hasBottomBar()

        binding.refreshLayout.setOnRefreshListener {
            homeViewModel.tagState.value!!.setAllTrue()
            homeViewModel.callPost()
            observeViewModel()
            binding.refreshLayout.isRefreshing = false
        }


        return binding.root
    }

    private fun observeViewModel() {
        with(homeViewModel) {
            itemList.observe(viewLifecycleOwner) {
                initHomeAdapter(it)
            }
        }
    }

    private fun btnState(button: Button, drawable: Int, color: Int) {
        context?.let {
            button.setBackgroundResource(drawable)
            button.setTextColor(ContextCompat.getColor(it, color))
        } ?: return

    }

    private fun initHomeAdapter(items: List<Post>) {
        homeAdapter = HomeAdapter(items)
        binding.rvHome.adapter = homeAdapter
        homeAdapter.notifyDataSetChanged()
    }


    private fun performViewModel() {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.vm = homeViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    private fun allTagsSelected() {
        with(binding) {
            btnState(btnDesign, R.drawable.corner_radious, R.color.white)
            btnState(btnWeb, R.drawable.corner_radious, R.color.white)
            btnState(btnAndroid, R.drawable.corner_radious, R.color.white)
            btnState(btnIos, R.drawable.corner_radious, R.color.white)
            btnState(btnServer, R.drawable.corner_radious, R.color.white)
        }
    }
}
