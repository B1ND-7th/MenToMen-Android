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

        with(addViewModel) {
            onCLickDesignEvent.observe(viewLifecycleOwner) {
                with(binding) {
                    btnState(btnDesign, kr.hs.b1nd.intern.mentomen.R.drawable.corner_radious, kr.hs.b1nd.intern.mentomen.R.color.white)
                    btnState(btnWeb, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_web, kr.hs.b1nd.intern.mentomen.R.color.web)
                    btnState(btnAndroid, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_android, kr.hs.b1nd.intern.mentomen.R.color.android)
                    btnState(btnIos, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_ios, kr.hs.b1nd.intern.mentomen.R.color.iOS)
                    btnState(btnServer, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_server, kr.hs.b1nd.intern.mentomen.R.color.server)
                }
            }

            onCLickWebEvent.observe(viewLifecycleOwner) {
                with(binding) {
                    btnState(btnWeb, kr.hs.b1nd.intern.mentomen.R.drawable.corner_radious, kr.hs.b1nd.intern.mentomen.R.color.white)
                    btnState(btnDesign, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_design, kr.hs.b1nd.intern.mentomen.R.color.design)
                    btnState(btnAndroid, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_android, kr.hs.b1nd.intern.mentomen.R.color.android)
                    btnState(btnIos, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_ios, kr.hs.b1nd.intern.mentomen.R.color.iOS)
                    btnState(btnServer, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_server, kr.hs.b1nd.intern.mentomen.R.color.server)
                }
            }

            onCLickServerEvent.observe(viewLifecycleOwner) {
                with(binding) {
                    btnState(btnServer, kr.hs.b1nd.intern.mentomen.R.drawable.corner_radious, kr.hs.b1nd.intern.mentomen.R.color.white)
                    btnState(btnWeb, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_web, kr.hs.b1nd.intern.mentomen.R.color.web)
                    btnState(btnAndroid, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_android, kr.hs.b1nd.intern.mentomen.R.color.android)
                    btnState(btnIos, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_ios, kr.hs.b1nd.intern.mentomen.R.color.iOS)
                    btnState(btnDesign, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_design, kr.hs.b1nd.intern.mentomen.R.color.design)
                }
            }

            onCLickAndroidEvent.observe(viewLifecycleOwner) {
                with(binding) {
                    btnState(btnAndroid, kr.hs.b1nd.intern.mentomen.R.drawable.corner_radious, kr.hs.b1nd.intern.mentomen.R.color.white)
                    btnState(btnWeb, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_web, kr.hs.b1nd.intern.mentomen.R.color.web)
                    btnState(btnDesign, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_design, kr.hs.b1nd.intern.mentomen.R.color.design)
                    btnState(btnIos, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_ios, kr.hs.b1nd.intern.mentomen.R.color.iOS)
                    btnState(btnServer, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_server, kr.hs.b1nd.intern.mentomen.R.color.server)
                }
            }

            onCLickIosEvent.observe(viewLifecycleOwner) {
                with(binding) {
                    btnState(btnIos, kr.hs.b1nd.intern.mentomen.R.drawable.corner_radious, kr.hs.b1nd.intern.mentomen.R.color.white)
                    btnState(btnWeb, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_web, kr.hs.b1nd.intern.mentomen.R.color.web)
                    btnState(btnAndroid, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_android, kr.hs.b1nd.intern.mentomen.R.color.android)
                    btnState(btnDesign, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_design, kr.hs.b1nd.intern.mentomen.R.color.design)
                    btnState(btnServer, kr.hs.b1nd.intern.mentomen.R.drawable.unselected_server, kr.hs.b1nd.intern.mentomen.R.color.server)
                }
            }
        }

        addViewModel.onClickConfirmEvent.observe(viewLifecycleOwner) {
            Toast.makeText(context, "글 등록을 성공했습니다.", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun btnState(btn: Button, drawable: Int, color: Int) {
        btn.apply {
            setBackgroundResource(drawable)
            setTextColor(ContextCompat.getColor(context, color))
        }
    }

    private fun performViewModel() {
        addViewModel = ViewModelProvider(this)[AddViewModel::class.java]
        binding.vm = addViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}
