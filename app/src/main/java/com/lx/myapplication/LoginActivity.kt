package com.lx.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lx.myapplication.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    var DB: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        DB = DBHelper(this)

        binding.loginBtn!!.setOnClickListener {
            val user = binding.nickname!!.text.toString()
            AppData.user = binding.nickname!!.text.toString()
            val pass = binding.password!!.text.toString()
            if (user == "" || pass == "")
                showToast("회원정보를 전부 입력해주세요ㅎ")
                else {
                val checkUserpass = DB!!.checkUserpass(user, pass)
                if (checkUserpass == true) {
                    showToast("로그인 되었습니다.")
                    val homeIntent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(homeIntent)
                } else {
                    showToast("회원정보가 일치하지 않습니다.")
                }
            }
        }
        binding.registerBtn.setOnClickListener {
            val loginIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(loginIntent)
        }
    }
    fun showToast(message:String) {
        Toast.makeText(this@LoginActivity,message, Toast.LENGTH_LONG).show()
    }
}
