package kr.hs.b1nd.intern.mentomen.view.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentDetailBinding
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.view.adapter.CommentAdapter
import kr.hs.b1nd.intern.mentomen.view.adapter.DetailImageAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.DetailViewModel

class DetailFragment : Fragment(), PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var imageAdapter: DetailImageAdapter

    private val navArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        (activity as MainActivity).hasBottomBar(false)

        performViewModel()
        initCommentAdapter()
        initImageAdapter()
        observeViewModel()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etComment.setOnKeyListener { _, keyCode, event ->
            with(detailViewModel) {
                if (commentContent.value.isNullOrBlank().not()) {
                    if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        postComment()
                        event.action
                    }
                }
            }
            true
        }

        binding.btnMore.setOnClickListener { showPopup(binding.btnMore) }

        return binding.root
    }

    private fun observeViewModel() = with(detailViewModel) {
        userId.observe(viewLifecycleOwner) {
            author.observe(viewLifecycleOwner) {
                if (userId.value == author.value) binding.btnMore.visibility = View.VISIBLE
                else binding.btnMore.visibility = View.GONE
            }

            itemList.observe(viewLifecycleOwner) { commentAdapter.submitList(it) }
        }

        postId.value = navArgs.postId
        readOne()
        readComment()
        getUser()

        successCommentEvent.observe(viewLifecycleOwner) {
            binding.etComment.setText("")
            readComment()
        }

        deletePostEvent.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    private fun initImageAdapter() = with(detailViewModel) {
        successReadEvent.observe(viewLifecycleOwner) {
            with(binding) {
                if (imgUrl.value.isNullOrEmpty()) viewpagerFrame.visibility = View.GONE
                else {
                    viewpagerFrame.visibility = View.VISIBLE

                    imageAdapter = DetailImageAdapter(imgUrl.value!!)
                    viewpager.adapter = imageAdapter
                    wormDotsIndicator.attachTo(viewpager)

                    if (imgUrl.value!!.size == 1) wormDotsIndicator.visibility = View.GONE
                    else wormDotsIndicator.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initCommentAdapter() {
        detailViewModel.userId.observe(viewLifecycleOwner) {
            commentAdapter = CommentAdapter(detailViewModel.userId.value!!)
            binding.rvComment.adapter = commentAdapter
        }
    }

    private fun performViewModel() {
        binding.vm = detailViewModel
        binding.lifecycleOwner = this
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.popub, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.edit -> {
                with(detailViewModel) {
                    val action = DetailFragmentDirections.actionDetailFragmentToEditFragment(postId = postId.value!!, content = content.value!!, tag = tag.value!!)
                    findNavController().navigate(action)
                }
            }
            R.id.delete -> {
                detailViewModel.deletePost()
            }
        }
        return item != null
    }
}

