package com.lx.myapplication

import android.content.ContentResolver
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.myapplication.databinding.FragmentMyBinding
import java.text.SimpleDateFormat

class MyFragment : Fragment() {
    var _binding: FragmentMyBinding? = null
    val binding get() = _binding!!
    var itemAdapter: NoticeAdapter?=null
    val itemInfoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

    }
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    var bitmap: Bitmap? = null
    val cr: ContentResolver?= null

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            _binding = FragmentMyBinding.inflate(inflater, container, false)

            binding.textView38.setText("${AppData.reward} points")

            // 뷰 초기화
            initView()
            // 리스트 초기화
            initList()


            return binding.root
        }
    //뷰 초기화
    fun initView() {

    }
    fun al() {

    }
    // 리스트 초기화
    fun initList() {
        // 1. 리스트의 모양을 담당하는 것
        // (LinearLayoutManager : 아래쪽으로 아이템들이 보이는 것, GridLayoutManager : 격자 형태로 보이는 것)
        val layoutManager = LinearLayoutManager(activity)
        binding.rewardItemList.layoutManager = layoutManager

        // 2. 어댑터를 설정하는 것
        // 실제 데이터를 관리하고 각 아이템의 모양을 만들어주는 것
        itemAdapter = NoticeAdapter()
        binding.rewardItemList.adapter = itemAdapter

        // 3. 아이템에 데이터 넣어보기
        itemAdapter?.apply {

            this.items.add(NoticeData(R.drawable.profile1,R.drawable.blank ,"  Hello! My name is","  ${AppData.user}"))

        }
    }
}