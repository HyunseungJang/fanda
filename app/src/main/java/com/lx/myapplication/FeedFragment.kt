package com.lx.myapplication

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.myapplication.ItemAdapter
import com.lx.myapplication.MainActivity
import com.lx.myapplication.OnFeedItemClickListener
import com.lx.myapplication.R
import com.lx.myapplication.api.BasicClient
import com.lx.myapplication.api.BasicClient.Companion.api
import com.lx.myapplication.data.CommunityListResponse
import com.lx.myapplication.data.FileUploadResponse
import com.lx.myapplication.databinding.FragmentFeedBinding
import com.permissionx.guolindev.PermissionX
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FeedFragment : Fragment() {
    var _binding: FragmentFeedBinding? = null
    var itemAdapter: ItemAdapter?=null
    val itemInfoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }
    val binding get() = _binding!!

    val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
    var bitmap: Bitmap? = null
    val cr: ContentResolver?= null

    //사진찍기를 위한 런처
    val captureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        //showToast("Captured!")
        val c = activity as MainActivity
        var inputView = c.findViewById<ImageView>(R.id.inputView)

        when(it.resultCode){
            AppCompatActivity.RESULT_OK -> {
                bitmap = it.data?.extras?.get("data") as Bitmap
                inputView.setImageBitmap(bitmap)

                saveFile()
            }
            AppCompatActivity.RESULT_CANCELED -> {
                // showToast("Capture canceled")
            }
        }
    }
    // 앨범에서 가져오기를 위한 런처
    val albumLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        //showToast("Back from Gallary")

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
                //showToast("Cancel selection")
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)

        //위험권한 요청하기
        PermissionX.init(this).permissions(Manifest.permission.CAMERA)
            .request{ allGranted, grantedList, deniedList ->
                if(allGranted){
                    //showToast("Allow all CAM-Permission")
                }else{
                    //showToast("Permission denied")
                }
            }
        // 뷰 초기화
        initView()

        // 리스트 초기화
        initList()

        //초기 리스트 보이기
        getCommunityList()



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
            (activity as CommunityActivity).onFragmentChange(0)
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

        // 4. 아이템을 클릭했을 때 동작할 코드 넣어주기
        itemAdapter?.listener = object : OnFeedItemClickListener {
            override fun onItemClick(
                holder: ItemAdapter.ViewHolder?,
                view: View?,
                position: Int
            ) {
                itemAdapter?.apply {
                    val item = items.get(position)
                    //showToast("아이템 선택됨 : ${position}, ${item.feedTitle}")

                }
            }
        }

    }


    fun getCommunityList() {

        // API에 있는 리스트 조회 요청하기
        BasicClient.api.postCommunityList(
            requestCode = "1001",
        ).enqueue(object : Callback<CommunityListResponse> {
            override fun onResponse(
                call: Call<CommunityListResponse>,
                response: Response<CommunityListResponse>
            ) {
                // 성공 응답
                println("onResponse 호출됨 : ${response.body().toString()}")
                addToCommunityList(response)
            }

            override fun onFailure(call:Call<CommunityListResponse>, t: Throwable) {
                // 실패 응답
                println("getCommunityList onFailure 호출됨 : ${t.message}")

            }
        })

        println("getCommunityList 요청함")
    }

    // 응답받은 데이터를 화면에 있는 리스트에 추가하기
    fun addToCommunityList(response: Response<CommunityListResponse>) {
        itemAdapter?.apply {
            response.body()?.output?.data?.let {
                for (item in it) {
                    this.items.add(ItemData(item.filepath,item.title, item.content))
                }
            }

            this.notifyDataSetChanged()
        }
    }




    // 게시글에서 사진 찍은거 저장하기
    fun saveFile() {
        println("saveFile 호출됨.")
        val a = activity as MainActivity

        // 파일 이름 만들기
        val filename = dateFormat.format(Date()) + ".jpg"

        bitmap?.apply {
            a.openFileOutput(filename, Context.MODE_PRIVATE).use {
                this@apply.compress(Bitmap.CompressFormat.JPEG, 100, it)
                it.close()

                //showToast("이미지를 파일로 저장함 : ${filename}")
                uploadFile(filename)


            }
        }
    }

    fun uploadFile(filename:String){
        val b = activity as MainActivity
        println("uploadfile 호출됨. : ${filename}")
        val writeTitle = binding.feedWriteTitle.text.toString()
        val writeText = binding.feedWriteText.text.toString()



        val file = File("${b.filesDir}/${filename}")
        val filePart = MultipartBody.Part.createFormData(
            "photo",
            filename,
            file.asRequestBody("image/jpg".toMediaTypeOrNull())
        )
        //추가 파라미터가 있는 경우
        val params = hashMapOf<String,String>()

        //API에 있는 리스트 조회요청하기
        BasicClient.api.uploadFile(
            file = filePart,
            params = params
        ).enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(call: Call<FileUploadResponse>, response: Response<FileUploadResponse>) {
                // 성공 응답
                println("onResponse 호출됨됨 : ${response.body().toString()}")

                response.body()?.output?.filename?.apply{
                    postCommunityAdd("${writeTitle}", "${writeText}",this)
                }

            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                // 실패 응답
                println("uploadFile onFailure 호출됨 : ${t.message}")

            }
        })

        println("uploadFile 요청함")
    }

    fun postCommunityAdd(title:String, content:String, filepath: String) {
        // API에 있는 리스트 조회 요청하기
        BasicClient.api.postCommunityAdd(
            requestCode = "1001",
            title = title,
            content = content,
            filepath = filepath
        ).enqueue(object : Callback<CommunityListResponse> {
            override fun onResponse(
                call: Call<CommunityListResponse>,
                response: Response<CommunityListResponse>
            ) {
                // 성공 응답
                println("onResponse 호출됨 : ${response.body().toString()}")

                // showToast("사진찍은 정보 업로드 완료됨")
            }

            override fun onFailure(call: Call<CommunityListResponse>, t: Throwable) {
                // 실패 응답
                println("postCommunityAdd onFailure 호출됨 : ${t.message}")
            }
        })
    }






//    fun showToast(message:String) {
//        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
//    }
}