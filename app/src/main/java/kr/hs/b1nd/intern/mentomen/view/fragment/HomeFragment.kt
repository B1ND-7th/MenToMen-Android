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

        with(homeViewModel) {
            onCLickDesignEvent.observe(viewLifecycleOwner) {
                with(binding) {
                    btnState(btnDesign, R.drawable.corner_radious, R.color.white)
                    btnState(btnWeb, R.drawable.unselected_web, R.color.web)
                    btnState(btnAndroid, R.drawable.unselected_android, R.color.android)
                    btnState(btnIos, R.drawable.unselected_ios, R.color.iOS)
                    btnState(btnServer, R.drawable.unselected_server, R.color.server)
                }
            }

            onCLickWebEvent.observe(viewLifecycleOwner) {
                with(binding) {
                    btnState(btnWeb, R.drawable.corner_radious, R.color.white)
                    btnState(btnDesign, R.drawable.unselected_design, R.color.design)
                    btnState(btnAndroid, R.drawable.unselected_android, R.color.android)
                    btnState(btnIos, R.drawable.unselected_ios, R.color.iOS)
                    btnState(btnServer, R.drawable.unselected_server, R.color.server)
                }
            }

            onCLickServerEvent.observe(viewLifecycleOwner) {
                with(binding) {
                    btnState(btnServer, R.drawable.corner_radious, R.color.white)
                    btnState(btnWeb, R.drawable.unselected_web, R.color.web)
                    btnState(btnAndroid, R.drawable.unselected_android, R.color.android)
                    btnState(btnIos, R.drawable.unselected_ios, R.color.iOS)
                    btnState(btnDesign, R.drawable.unselected_design, R.color.design)
                }
            }

            onCLickAndroidEvent.observe(viewLifecycleOwner) {
                with(binding) {
                    btnState(btnAndroid, R.drawable.corner_radious, R.color.white)
                    btnState(btnWeb, R.drawable.unselected_web, R.color.web)
                    btnState(btnDesign, R.drawable.unselected_design, R.color.design)
                    btnState(btnIos, R.drawable.unselected_ios, R.color.iOS)
                    btnState(btnServer, R.drawable.unselected_server, R.color.server)
                }
            }

            onCLickIosEvent.observe(viewLifecycleOwner) {
                with(binding) {
                    btnState(btnIos, R.drawable.corner_radious, R.color.white)
                    btnState(btnWeb, R.drawable.unselected_web, R.color.web)
                    btnState(btnAndroid, R.drawable.unselected_android, R.color.android)
                    btnState(btnDesign, R.drawable.unselected_design, R.color.design)
                    btnState(btnServer, R.drawable.unselected_server, R.color.server)
                }
            }
        }



        return binding.root
    }

    private fun btnState(btn: Button, drawable: Int, color: Int) {
        btn.apply {
            setBackgroundResource(drawable)
            setTextColor(ContextCompat.getColor(context, color))
        }
    }

    private fun observeViewModel() {
        with(homeViewModel) {
            itemList.observe(viewLifecycleOwner) {
                initHomeAdapter(it)
            }
        }
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
}