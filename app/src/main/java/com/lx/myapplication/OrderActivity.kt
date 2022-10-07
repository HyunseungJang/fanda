package com.lx.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lx.myapplication.databinding.ActivityMainBinding
import com.lx.myapplication.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //첫번째 버튼 눌렀을 때
        binding.button1.setOnClickListener {
            onFragmentChanged(0)
        }

        //두번째 버튼 눌렀을 때
        binding.button2.setOnClickListener {
            onFragmentChanged(1)
        }

        //세번째 버튼 눌렀을 때
        binding.button3.setOnClickListener {
            onFragmentChanged(2)
        }

        //supportFragmentManager.beginTransaction().replace(R.id.container, FirstFragment()).commit()
        onFragmentChanged(0)
    }

    fun onFragmentChanged(index:Int) {
        when(index) {
            0 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Menu1Fragment()).commit()
            }
            1 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Menu2Fragment()).commit()
            }
            2 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Menu3Fragment()).commit()
            }
        }
    }

}