package kr.hs.b1nd.intern.mentomen.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentNoticeBinding
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.view.adapter.NoticeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.NoticeViewModel

class NoticeFragment : Fragment() {
    private lateinit var binding: FragmentNoticeBinding
    private val noticeViewModel: NoticeViewModel by viewModels()
    private lateinit var noticeAdapter: NoticeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notice,
            container,
            false
        )
        (activity as MainActivity).hasBottomBar(false)

        performViewModel()
        initNoticeAdapter()
        observeViewModel()

        binding.backButton.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }

    private fun observeViewModel() = with(noticeViewModel) {
        itemList.observe(viewLifecycleOwner) { noticeAdapter.submitList(it) }
    }

    private fun initNoticeAdapter() {
        noticeAdapter = NoticeAdapter {
            val action = NoticeFragmentDirections.actionNoticeFragmentToDetailFragment(it.postId)
            findNavController().navigate(action)
        }
        binding.rvNotice.adapter = noticeAdapter
    }

    private fun performViewModel() {
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        noticeViewModel.callNotice()
    }
}
