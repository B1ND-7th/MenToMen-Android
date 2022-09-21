package kr.hs.b1nd.intern.mentomen.view.fragment

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentAddBinding
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.viewmodel.AddViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var addViewModel: AddViewModel
    private var image: Uri? = null

    private var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val imagePath = result.data!!.data

            val file = File(absolutelyPath(imagePath, requireContext()))
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            image = imagePath
            addViewModel.imgFile.value = body
        }
    }

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

        addViewModel.onClickImageEvent.observe(viewLifecycleOwner) {
            getProFileImage()
        }

        addViewModel.imgFile.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(image)
                .into(binding.btnImage)
            addViewModel.loadImage()
        }


        return binding.root
    }

    private fun getProFileImage(){
        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        chooserIntent.putExtra(Intent.EXTRA_INTENT, intent)
        chooserIntent.putExtra(Intent.EXTRA_TITLE,"사용할 앱을 선택해주세요.")
        launcher.launch(chooserIntent)
    }

    private fun absolutelyPath(path: Uri?, context : Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)

        return result!!
    }

    private fun performViewModel() {
        addViewModel = ViewModelProvider(this)[AddViewModel::class.java]
        binding.vm = addViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}
