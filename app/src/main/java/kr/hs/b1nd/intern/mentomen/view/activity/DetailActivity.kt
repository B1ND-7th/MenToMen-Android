package kr.hs.b1nd.intern.mentomen.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ActivityDetailBinding
import kr.hs.b1nd.intern.mentomen.network.model.ImgUrl
import kr.hs.b1nd.intern.mentomen.view.adapter.CommentAdapter
import kr.hs.b1nd.intern.mentomen.view.adapter.DetailImageAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.DetailViewModel
import java.io.Serializable
import java.util.ArrayList

class DetailActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var imageAdapter: DetailImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        performViewModel()
        initCommentAdapter()
        initImageAdapter()
        observeViewModel()

        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        binding.btnMore.setOnClickListener { showPopup(binding.btnMore) }
    }

    private fun observeViewModel() = with(detailViewModel) {
        userId.observe(this@DetailActivity) {
            author.observe(this@DetailActivity) {
                Log.d("test123", "${userId.value} ${author.value}: ")
                if (userId.value == author.value) binding.btnMore.visibility = View.VISIBLE
                else binding.btnMore.visibility = View.GONE
            }
        }

        itemList.observe(this@DetailActivity) { commentAdapter.submitList(it) }

        postId.value = intent.getIntExtra("postId", 0)
        readOne()
        readComment()
        getUser()

        successCommentEvent.observe(this@DetailActivity) {
            binding.etComment.setText("")
            readComment()
        }

        deletePostEvent.observe(this@DetailActivity) { finish() }
    }

    private fun initImageAdapter() = with(detailViewModel) {
        successReadEvent.observe(this@DetailActivity) {
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
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(R.menu.popub, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.edit -> {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("postId", detailViewModel.postId.value)
                intent.putExtra("content", detailViewModel.content.value)
                intent.putExtra("tag", detailViewModel.tag.value)
                startActivity(intent)
            }
            R.id.delete -> {
                detailViewModel.deletePost()
            }
        }
        return item != null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isFinishing) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}