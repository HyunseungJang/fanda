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
                showToast("Please enter all information")
                else{
                if(pass == repass){
                    val checkUsername = DB!!.checkUserName(user)
                    if(checkUsername == false){
                        val insert = DB!!.insertData(user, pass)
                        if(insert == true){
                            showToast("Register Success")
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            showToast("Please check your information agian")
                        }
                    } else {
                        showToast("Already exists")
                    }
                } else {
                    showToast("Please try again in a few minutes")
                }
            }
        }


    }
    fun showToast(message:String) {
        Toast.makeText(this@RegisterActivity,message, Toast.LENGTH_LONG).show()
    }
}