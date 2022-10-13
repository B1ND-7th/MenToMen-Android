package kr.hs.b1nd.intern.mentomen.view.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.substring
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentSearchBinding
import kr.hs.b1nd.intern.mentomen.view.activity.DetailActivity
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.view.adapter.HomeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.SearchViewModel
import org.json.JSONObject.NULL


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var homeAdapter: HomeAdapter

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

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
        (activity as MainActivity).hasBottomBar(false)

        performViewModel()
        initHomeAdapter()
        observeViewModel()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.searchPage.setOnClickListener{
            binding.etSearch.clearFocus()
            binding.etSearch.hideKeyboard()
        }

        binding.searchButton.setOnClickListener {
            with(searchViewModel) {
                if(!keyWord.value.isNullOrEmpty())
                    searchPost()
            }
        }

        binding.cancelButton.setOnClickListener {
            binding.etSearch.text = null

        }

        binding.etSearch.setOnClickListener {
            binding.etSearch.isCursorVisible=true
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
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("postId", it.postId)
            startActivity(intent)
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