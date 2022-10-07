package com.lx.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.component1
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.lx.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Glide.with(this).load(R.raw.logo).override(560, 560).into(binding.imageView)

        // 하단 탭이 눌렸을 때 화면을 전환하기 위해선 이벤트 처리하기 위해 BottomNavigationView 객체 생성
        var bnv_main2 = findViewById(R.id.bnv_main2) as ChipNavigationBar

        // OnNavigationItemSelectedListener를 통해 탭 아이템 선택 시 이벤트를 처리
        bnv_main2.run {
            setOnItemSelectedListener() {
                when (it) {
                    R.id.follow -> {
                        // 다른 프래그먼트 화면으로 이동하는 기능
                        val placeFragment = PlaceFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, placeFragment).commit()
                    }
                    R.id.home -> {
                        val homeFragment = HomeFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, homeFragment).commit()
                    }
                    R.id.community -> {
                        val feedFragment = FeedFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, feedFragment).commit()
                    }
                    R.id.mypage -> {
                        val myFragment = MyFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, myFragment).commit()
                    }
                }
                true
            }
            bnv_main2.setItemSelected(R.id.home)
        }

//        binding.button.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
    }
}