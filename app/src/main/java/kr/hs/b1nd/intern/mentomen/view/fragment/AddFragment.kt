package kr.hs.b1nd.intern.mentomen.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentAddBinding
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.viewmodel.AddViewModel

class AddFragment : Fragment(){

    private lateinit var binding: FragmentAddBinding
    private lateinit var addViewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add,
            container,
            false
        )
        performViewModel()

        (activity as MainActivity).hasTopBar(false)
        (activity as MainActivity).hasBottomBar(false)
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        addViewModel.content.observe(viewLifecycleOwner) {
            if (it != "") binding.btnConfirm.setBackgroundResource(R.color.blue)
        }

        addViewModel.onClickConfirmEvent.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun performViewModel() {
        addViewModel = ViewModelProvider(this)[AddViewModel::class.java]
        binding.vm = addViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}
