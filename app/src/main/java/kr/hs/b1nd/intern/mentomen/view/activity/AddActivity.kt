package kr.hs.b1nd.intern.mentomen.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ActivityAddBinding
import kr.hs.b1nd.intern.mentomen.view.adapter.ImageAdapter
import kr.hs.b1nd.intern.mentomen.view.adapter.NoticeAdapter
import kr.hs.b1nd.intern.mentomen.viewmodel.AddViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var addViewModel: AddViewModel
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = MutableLiveData<ArrayList<Uri?>>(arrayListOf())

    @SuppressLint("NotifyDataSetChanged")
    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                imageList.value?.clear()
                addViewModel.imgFile.value?.clear()
                if (result.data?.clipData != null) {
                    val count = result.data?.clipData!!.itemCount
                    if (count > 5) {
                        Toast.makeText(this, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                            .show()
                        return@registerForActivityResult
                    }
                    for (i in 0 until count) {
                        val imageUri = result.data?.clipData!!.getItemAt(i).uri
                        val file = File(absolutelyPath(imageUri, this))
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.fade_out)

        performViewModel()
        initImageAdapter()
        observeViewModel()

        imageList.observe(this) { imageAdapter.submitList(it) }

        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.slide_out_bottom)
        }
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

    private fun observeViewModel() = with(addViewModel) {
        content.observe(this@AddActivity) { content ->
            if (content != "") tag.observe(this@AddActivity) { tag ->
                if (tag != "") binding.btnConfirm.setBackgroundResource(
                    R.color.blue
                )
            }
            else binding.btnConfirm.setBackgroundResource(R.color.gray)
        }
        onClickConfirmEvent.observe(this@AddActivity) {
            if (tag.value == "" && content.value == "") Toast.makeText(
                this@AddActivity,
                "태그와 내용을 입력해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            else if (tag.value == "") Toast.makeText(
                this@AddActivity,
                "태그를 선택해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            else if (content.value == "") Toast.makeText(
                this@AddActivity,
                "내용을 입력해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            else Toast.makeText(this@AddActivity, "업로드 중... 잠시만 기다려주세요.", Toast.LENGTH_SHORT).show()

            errorImageEvent.observe(this@AddActivity) {
                Toast.makeText(this@AddActivity, "파일 용량이 너무 커요!", Toast.LENGTH_SHORT).show()
            }
        }


        onClickImageEvent.observe(this@AddActivity) { getImageGallery() }
        successConfirmEvent.observe(this@AddActivity) {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.slide_out_bottom)
        }
        imgFile.observe(this@AddActivity) { loadImage() }
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

    override fun onBackPressed() {
        super.onBackPressed()
        if (isFinishing) {
            overridePendingTransition(R.anim.fade_in, R.anim.slide_out_bottom)
        }
    }
}