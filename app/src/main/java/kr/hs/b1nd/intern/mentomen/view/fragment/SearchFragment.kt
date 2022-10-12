package kr.hs.b1nd.intern.mentomen.view.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentSearchBinding
import kr.hs.b1nd.intern.mentomen.view.adapter.HomeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.SearchViewModel


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )

        performViewModel()
        initHomeAdapter()
        observeViewModel()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        fun View.hideKeyboard() {
            Log.d(TAG, "hideKeyboard: 로그 하이드 키ㅗ드")
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

        binding.searchPage.setOnClickListener{
            binding.etSearch.isCursorVisible=false
            binding.etSearch.hideKeyboard()
        }



        binding.cancelButton.setOnClickListener {
            binding.etSearch.text = null

        }

        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            binding.etSearch.isCursorVisible=true
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                with(searchViewModel) {
                    searchPost()
                }
            }

            true
        }




        return binding.root
    }


    private fun observeViewModel() {
        with(searchViewModel) {
            itemList.observe(viewLifecycleOwner) {
                homeAdapter.submitList(it)
            }
        }
    }

    private fun initHomeAdapter() {
        homeAdapter = HomeAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.postId)
            findNavController().navigate(action)
        }
        binding.rvSearch.adapter = homeAdapter
    }

    private fun performViewModel() {
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding.vm = searchViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }


}