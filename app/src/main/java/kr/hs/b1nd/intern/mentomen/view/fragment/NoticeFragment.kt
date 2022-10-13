package kr.hs.b1nd.intern.mentomen.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentNoticeBinding
import kr.hs.b1nd.intern.mentomen.view.activity.DetailActivity
import kr.hs.b1nd.intern.mentomen.view.adapter.HomeAdapter
import kr.hs.b1nd.intern.mentomen.view.adapter.NoticeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.HomeViewModel
import kr.hs.b1nd.intern.mentomen.viewmodel.NoticeViewModel

class NoticeFragment : Fragment() {
    private lateinit var binding: FragmentNoticeBinding
    private lateinit var noticeViewModel: NoticeViewModel
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
        performViewModel()
        initNoticeAdapter()
        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() = with(noticeViewModel) {
        itemList.observe(viewLifecycleOwner) { noticeAdapter.submitList(it) }
    }

    private fun initNoticeAdapter() {
        noticeAdapter = NoticeAdapter {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("postId", it.postId)
            startActivity(intent)
        }
        binding.rvNotice.adapter = noticeAdapter
    }

    private fun performViewModel() {
        noticeViewModel = ViewModelProvider(this)[NoticeViewModel::class.java]
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        noticeViewModel.callNotice()
    }
}
