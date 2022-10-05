package com.lx.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lx.myapplication.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    var DB: DBHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        DB = DBHelper(this)

        binding.registerBtn.setOnClickListener {
            val user = binding.nameInput.text.toString()
            val pass = binding.passInput.text.toString()
            val repass = binding.repassInput.text.toString()
            if(user == "" || pass == "" || repass == "")
                showToast("회원정보를 전부 입력해주세요")
                else{
                if(pass == repass){
                    val checkUsername = DB!!.checkUserName(user)
                    if(checkUsername == false){
                        val insert = DB!!.insertData(user, pass)
                        if(insert == true){
                            showToast("가입되었습니다.")
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            showToast("비밀번호가 일치하지 않습니다.")
                        }
                    } else {
                        showToast("이미 가입된 회원입니다.")
                    }
                } else {
                    showToast("비밀번호가 일치하지 않습니다.")
                }
            }
        }


    }
    fun showToast(message:String) {
        Toast.makeText(this@RegisterActivity,message, Toast.LENGTH_LONG).show()
    }
}