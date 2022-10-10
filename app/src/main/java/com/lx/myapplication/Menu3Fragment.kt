package com.lx.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.lx.myapplication.api.BasicClient
import com.lx.data.CartListResponse
import com.lx.myapplication.databinding.FragmentMenu3Binding
import retrofit2.Callback
import retrofit2.Response

class Menu3Fragment : Fragment() {
    var _binding: FragmentMenu3Binding? = null
    val binding get() = _binding!!

    val cartLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMenu3Binding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    fun initView() {
        binding.orderButton.setOnClickListener {
            postCartAdd()

            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }
    }

    fun postCartAdd() {
        val name = binding.name.text.toString()
        val price = binding.price.text.toString()
        val filepath = "/images/menu31665371357415.png"

        BasicClient.api.postCartAdd(
            requestCode = "1001",
            name = name,
            price = price,
            filepath = filepath
        ).enqueue(object : Callback<com.lx.data.CartListResponse> {
            override fun onResponse(
                call: retrofit2.Call<com.lx.data.CartListResponse>,
                response: Response<com.lx.data.CartListResponse>
            ) {
                println("onResponse 호출됨 : ${response.body().toString()}")

                showToast("정보 업로드 완료됨")
            }

            override fun onFailure(call: retrofit2.Call<com.lx.data.CartListResponse>, t: Throwable) {
                println("postCartAdd onFailure 호출됨 : ${t.message}")
            }
        })
    }

    fun showToast(message:String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}