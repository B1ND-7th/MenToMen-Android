package kr.hs.b1nd.intern.mentomen.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentDetailBinding
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.viewmodel.DetailViewModel

class DetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding

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
        performViewModel()
        detailViewModel.readOne(navArgs.postId)

        (activity as MainActivity).hasTopBar(false)
        (activity as MainActivity).hasBottomBar(false)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }

    private fun performViewModel() {
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        binding.vm = detailViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}