package kr.hs.b1nd.intern.mentomen.view.fragment

import android.annotation.SuppressLint
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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.FragmentAddBinding
import kr.hs.b1nd.intern.mentomen.view.activity.MainActivity
import kr.hs.b1nd.intern.mentomen.view.adapter.ImageAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.AddViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var addViewModel: AddViewModel
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = MutableLiveData<ArrayList<Uri?>>(arrayListOf())

    @SuppressLint("NotifyDataSetChanged")
    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                imageList.value?.clear()
                if (result.data?.clipData != null) {
                    val count = result.data?.clipData!!.itemCount
                    if (count > 5) {
                        Toast.makeText(requireContext(), "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                            .show()
                        return@registerForActivityResult
                    }
                    for (i in 0 until count) {
                        val imageUri = result.data?.clipData!!.getItemAt(i).uri
                        val file = File(absolutelyPath(imageUri, requireContext()))
                        val extension = file.toString().split(".")[1]
                        val requestFile = file.asRequestBody("image/$extension".toMediaTypeOrNull())
                        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                        imageList.value?.add(imageUri)
                        addViewModel.imgFile.value?.add(body)
                    }

                }
            }
            imageAdapter.notifyDataSetChanged()

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
        (activity as MainActivity).hasBottomBar(false)

        performViewModel()
        initImageAdapter()
        observeItem()

        binding.backButton.setOnClickListener { findNavController().popBackStack() }

        with(addViewModel) {
            content.observe(viewLifecycleOwner) { content ->
                if (content != "") tag.observe(viewLifecycleOwner) { tag -> if (tag != "") binding.btnConfirm.setBackgroundResource(R.color.blue) }
                 else binding.btnConfirm.setBackgroundResource(R.color.gray)
            }
            onClickConfirmEvent.observe(viewLifecycleOwner) {
                context?.let { context ->
                    if (tag.value == "" && content.value == "") Toast.makeText(context, "태그와 내용을 입력해주세요!", Toast.LENGTH_SHORT).show()
                    else if (tag.value == "") Toast.makeText(context, "태그를 선택해주세요!", Toast.LENGTH_SHORT).show()
                    else if (content.value == "") Toast.makeText(context, "내용을 입력해주세요!", Toast.LENGTH_SHORT).show()
                }
            }

            onClickImageEvent.observe(viewLifecycleOwner) { getImageGallery() }
            successConfirmEvent.observe(viewLifecycleOwner) { findNavController().popBackStack() }
            successImageEvent.observe(viewLifecycleOwner) { submitPost() }
        }
        return binding.root
    }

    private fun getImageGallery() {
        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        val intent = Intent(Intent.ACTION_PICK)

        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        chooserIntent.putExtra(Intent.EXTRA_INTENT, intent)
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "사용할 앱을 선택해주세요.")
        launcher.launch(chooserIntent)
    }

    @SuppressLint("Recycle")
    private fun absolutelyPath(path: Uri?, context: Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)

        return result!!
    }

    private fun observeItem() {
        imageList.observe(viewLifecycleOwner) { imageAdapter.submitList(it) }
    }

    private fun initImageAdapter() {
        imageAdapter = ImageAdapter()
        binding.rvImage.adapter = imageAdapter
    }

    private fun performViewModel() {
        addViewModel = ViewModelProvider(this)[AddViewModel::class.java]
        binding.vm = addViewModel
        binding.lifecycleOwner = this
    }
}
