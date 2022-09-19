package kr.hs.b1nd.intern.mentomen.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentHomeBinding
import kr.hs.b1nd.intern.mentomen.databinding.FragmentUserBinding
import kr.hs.b1nd.intern.mentomen.view.adapter.HomeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.HomeViewModel
import kr.hs.b1nd.intern.mentomen.viewmodel.UserViewModel

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private lateinit var userViewModel : UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false)

        performViewModel()
        binding.profileTitle.text = "${userViewModel.name.value}님, 환영합니다! "
        binding.profileEmail.text="${userViewModel.email.value}"
        binding.profileClass.text="${userViewModel.grade.value}학년 ${userViewModel.room.value}반 ${userViewModel.number.value}번"


        return binding.root
    }

    private fun performViewModel() {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding.vm = userViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            userViewModel.getMyPostState.collect{
                if(it.list.isNotEmpty()){
                    binding.rvMypage.adapter = HomeAdapter(it.list)
                }
            }
        }
    }
}