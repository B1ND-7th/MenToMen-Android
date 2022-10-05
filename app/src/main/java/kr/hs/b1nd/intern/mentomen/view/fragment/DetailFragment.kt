package kr.hs.b1nd.intern.mentomen.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentDetailBinding
import kr.hs.b1nd.intern.mentomen.network.model.Comment
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.view.adapter.CommentAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.DetailViewModel

class DetailFragment : Fragment(), PopupMenu.OnMenuItemClickListener {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private lateinit var commentAdapter: CommentAdapter

    private val navArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        (activity as MainActivity).hasBottomBar(false)
        performViewModel()
        with(detailViewModel) {
            postId.value = navArgs.postId
            readOne()
            readComment()
        }
        initCommentAdapter()
        observeViewModel()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnMore.setOnClickListener { showPopup(binding.btnMore) }

        with(detailViewModel) {
            deletePostEvent.observe(viewLifecycleOwner) {
                findNavController().popBackStack()
            }
            successCommentEvent.observe(viewLifecycleOwner) {
                binding.etComment.setText("")
                readComment()
            }
        }

        return binding.root
    }

    private fun observeViewModel() {
        with(detailViewModel) {
            itemList.observe(viewLifecycleOwner) {
                commentAdapter.submitList(it)
            }
        }
    }

    private fun performViewModel() {
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        binding.vm = detailViewModel
        binding.lifecycleOwner = this
    }

    private fun initCommentAdapter() {
        commentAdapter = CommentAdapter()
        binding.rvComment.adapter = commentAdapter
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(R.menu.popub, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.edit -> {

            }
            R.id.delete -> {
                detailViewModel.deletePost()
            }
        }
        return item != null
    }
}