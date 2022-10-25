package kr.hs.b1nd.intern.mentomen.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentHomeBinding
import kr.hs.b1nd.intern.mentomen.databinding.FragmentImageZoomBinding


class ImageZoomFragment : Fragment() {
    private lateinit var binding: FragmentImageZoomBinding
    private val navArgs: ImageZoomFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_image_zoom,
            container,
            false
        )

        Glide.with(this)
            .load(navArgs.imgUrl)
            .into(binding.photoview)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }
}