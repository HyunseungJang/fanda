package com.lx.myapplication

import android.content.ContentResolver
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.lx.myapplication.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat


class HomeFragment : Fragment() {
    var _binding: FragmentHomeBinding? = null
    var itemAdapter: NoticeAdapter?=null
    val itemInfoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }
    val binding get() = _binding!!

    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    var bitmap: Bitmap? = null
    val cr: ContentResolver?= null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        Glide.with(this).load(R.raw.bts2).override(560, 560).into(binding.imageView4)

        // 뷰 초기화
        initView()
        // 리스트 초기화
        initList()

        return binding.root

    }

    //뷰 초기화
    fun initView() {

    }

    // 리스트 초기화
    fun initList() {
        // 1. 리스트의 모양을 담당하는 것2030 부산 세계 박람회 유치 기원 콘서트
        // (LinearLayoutManager : 아래쪽으로 아이템들이 보이는 것, GridLayoutManager : 격자 형태로 보이는 것)
        val layoutManager = LinearLayoutManager(activity)
        binding.noticeitemList.layoutManager = layoutManager

        // 2. 어댑터를 설정하는 것
        // 실제 데이터를 관리하고 각 아이템의 모양을 만들어주는 것
        itemAdapter = NoticeAdapter()
        binding.noticeitemList.adapter = itemAdapter

        // 3. 아이템에 데이터 넣어보기
        itemAdapter?.apply {

            this.items.add(NoticeData(R.drawable.jimin,R.drawable.notice1,"  Anniversary","  Happy Birthday Jimin!"))
            this.items.add(NoticeData(R.drawable.concert1,R.drawable.notice1,"  Concert | Busan Asiad Main Stadium","  2030 Yet to Come in BUSAN"))
            this.items.add(NoticeData(R.drawable.concert2,R.drawable.notice1,"  Concert","  BTS 'MAP OF THE SOUL TOUR' Seoul Concert"))


        }
    }


    fun showToast(message:String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}