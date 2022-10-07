package com.lx.myapplication

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Telephony.Mms.Part.FILENAME
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.myapplication.databinding.FragmentFeedBinding
import com.permissionx.guolindev.PermissionX
import java.text.SimpleDateFormat
import java.util.*

class FeedFragment : Fragment() {
    var _binding: FragmentFeedBinding? = null
    var itemAdapter: ItemAdapter?=null
    val itemInfoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }
    val binding get() = _binding!!

    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    var bitmap: Bitmap? = null
    val cr: ContentResolver?= null

    //사진찍기를 위한 런처
    val captureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        showToast("Captured!")

        when(it.resultCode){
            AppCompatActivity.RESULT_OK -> {
                bitmap = it.data?.extras?.get("data") as Bitmap
                binding.inputView.setImageBitmap(bitmap)
                val saveCapture = activity as MainActivity
            }
            AppCompatActivity.RESULT_CANCELED -> {
                showToast("Capture canceled")
            }
        }
    }
    // 앨범에서 가져오기를 위한 런처
    val albumLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        showToast("Back from Gallary")

        when(it.resultCode){
            AppCompatActivity.RESULT_OK -> {
                it.data?.apply {
                    val imageUri = this.data
                    imageUri?.let {

                        bitmap = MediaStore.Images.Media.getBitmap(cr, it)
                        binding.inputView.setImageBitmap(bitmap)
                    }
                }

            }
            AppCompatActivity.RESULT_CANCELED -> {
                showToast("Cancel selection")
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)

        //위험권한 요청하기
        PermissionX.init(this).permissions(Manifest.permission.CAMERA)
            .request{ allGranted, grantedList, deniedList ->
                if(allGranted){
                    showToast("Allow all CAM-Permission")
                }else{
                    showToast("Permission denied")
                }
            }
        // 뷰 초기화
        initView()
        // 리스트 초기화
        initList()

        return binding.root

    }

    //뷰 초기화
    fun initView() {

        // 사진찍기 버튼 눌렀을 때
        binding.getPictureButton.setOnClickListener {
            // 단말의 카메라 앱 띄워주기
            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            captureLauncher.launch(captureIntent)
        }

        // 앨범에서 가져오기 버튼 눌렀울 때
        binding.bringButton.setOnClickListener {
            val albumIntent = Intent(Intent.ACTION_GET_CONTENT)
            albumIntent.type = "image/*"
            albumLauncher.launch(albumIntent)
        }
        //파일 업로드버튼 눌렀을때
        binding.uploadButton.setOnClickListener {
            val feedtitleinput = binding.feedWriteTitle.text.toString()
            val feedtextinput = binding.feedWriteText.text.toString()
            val inputpicture = binding.inputView.imageAlpha
            itemAdapter?.apply {
                items.add(ItemData(R.drawable.btsprofile, inputpicture, feedtitleinput,dateFormat.format(Date()),feedtextinput))
                notifyDataSetChanged()

            }
        }

    }

    // 리스트 초기화
    fun initList() {
        // 1. 리스트의 모양을 담당하는 것
        // (LinearLayoutManager : 아래쪽으로 아이템들이 보이는 것, GridLayoutManager : 격자 형태로 보이는 것)
        val layoutManager = LinearLayoutManager(activity)
        binding.feeditemList.layoutManager = layoutManager

        // 2. 어댑터를 설정하는 것
        // 실제 데이터를 관리하고 각 아이템의 모양을 만들어주는 것
        itemAdapter = ItemAdapter()
        binding.feeditemList.adapter = itemAdapter

        // 3. 아이템에 데이터 넣어보기
        itemAdapter?.apply {
            for (i in itemList.feeditemList) {
                this.items.add(i)
            }

        }
    }


    fun showToast(message:String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}