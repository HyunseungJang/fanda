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
import com.lx.myapplication.data.CartListResponse
import com.lx.myapplication.databinding.FragmentMenu2Binding
import retrofit2.Callback
import retrofit2.Response

class Menu2Fragment : Fragment() {
    var _binding: FragmentMenu2Binding? = null
    val binding get() = _binding!!

    val cartLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMenu2Binding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    fun initView() {

        //바로 주문하기 버튼1 눌렀을때
        binding.orderButton1.setOnClickListener {
            postCartAdd1()

            //cart activity로 이동
            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

        binding.orderButton2.setOnClickListener {
            postCartAdd2()

            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

        binding.orderButton3.setOnClickListener {
            postCartAdd3()

            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

        binding.orderButton4.setOnClickListener {
            postCartAdd4()

            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

        binding.orderButton5.setOnClickListener {
            postCartAdd5()

            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

        binding.orderButton6.setOnClickListener {
            postCartAdd6()

            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

    }

    fun postCartAdd1() {
        // API에 있는 리스트 조회 요청하기
        val name = binding.name1.text.toString()
        val price = binding.price1.text.toString()
        val filepath = "/images/menu91665371468374.png"

        BasicClient.api.postCartAdd(
            requestCode = "1001",
            name = name,
            price = price,
            filepath = filepath
        ).enqueue(object : Callback<CartListResponse> {
            override fun onResponse(
                call: retrofit2.Call<CartListResponse>,
                response: Response<CartListResponse>
            ) {
                // 성공 응답
                println("onResponse 호출됨 : ${response.body().toString()}")

                showToast("정보 업로드 완료됨")
            }

            override fun onFailure(call: retrofit2.Call<CartListResponse>, t: Throwable) {
                // 실패 응답
                println("postCartAdd onFailure 호출됨 : ${t.message}")
            }
        })
    }

    fun postCartAdd2() {
        val name = binding.name2.text.toString()
        val price = binding.price2.text.toString()
        val filepath = "/images/menu41665371374047.png"

        BasicClient.api.postCartAdd(
            requestCode = "1001",
            name = name,
            price = price,
            filepath = filepath
        ).enqueue(object : Callback<CartListResponse> {
            override fun onResponse(
                call: retrofit2.Call<CartListResponse>,
                response: Response<CartListResponse>
            ) {
                println("onResponse 호출됨 : ${response.body().toString()}")

                showToast("정보 업로드 완료됨")
            }

            override fun onFailure(call: retrofit2.Call<CartListResponse>, t: Throwable) {
                println("postCartAdd onFailure 호출됨 : ${t.message}")
            }
        })
    }

    fun postCartAdd3() {
        val name = binding.name2.text.toString()
        val price = binding.price2.text.toString()
        val filepath = "/images/menu81665371449411.png"

        BasicClient.api.postCartAdd(
            requestCode = "1001",
            name = name,
            price = price,
            filepath = filepath
        ).enqueue(object : Callback<CartListResponse> {
            override fun onResponse(
                call: retrofit2.Call<CartListResponse>,
                response: Response<CartListResponse>
            ) {
                println("onResponse 호출됨 : ${response.body().toString()}")

                showToast("정보 업로드 완료됨")
            }

            override fun onFailure(call: retrofit2.Call<CartListResponse>, t: Throwable) {
                println("postCartAdd onFailure 호출됨 : ${t.message}")
            }
        })
    }

    fun postCartAdd4() {
        val name = binding.name2.text.toString()
        val price = binding.price2.text.toString()
        val filepath = "/images/menu71665371431759.png"

        BasicClient.api.postCartAdd(
            requestCode = "1001",
            name = name,
            price = price,
            filepath = filepath
        ).enqueue(object : Callback<CartListResponse> {
            override fun onResponse(
                call: retrofit2.Call<CartListResponse>,
                response: Response<CartListResponse>
            ) {
                println("onResponse 호출됨 : ${response.body().toString()}")

                showToast("정보 업로드 완료됨")
            }

            override fun onFailure(call: retrofit2.Call<CartListResponse>, t: Throwable) {
                println("postCartAdd onFailure 호출됨 : ${t.message}")
            }
        })
    }

    fun postCartAdd5() {
        val name = binding.name2.text.toString()
        val price = binding.price2.text.toString()
        val filepath = "/images/menu61665371412278.png"

        BasicClient.api.postCartAdd(
            requestCode = "1001",
            name = name,
            price = price,
            filepath = filepath
        ).enqueue(object : Callback<CartListResponse> {
            override fun onResponse(
                call: retrofit2.Call<CartListResponse>,
                response: Response<CartListResponse>
            ) {
                println("onResponse 호출됨 : ${response.body().toString()}")

                showToast("정보 업로드 완료됨")
            }

            override fun onFailure(call: retrofit2.Call<CartListResponse>, t: Throwable) {
                println("postCartAdd onFailure 호출됨 : ${t.message}")
            }
        })
    }

    fun postCartAdd6() {
        val name = binding.name2.text.toString()
        val price = binding.price2.text.toString()
        val filepath = "/images/menu101665371488005.png"

        BasicClient.api.postCartAdd(
            requestCode = "1001",
            name = name,
            price = price,
            filepath = filepath
        ).enqueue(object : Callback<CartListResponse> {
            override fun onResponse(
                call: retrofit2.Call<CartListResponse>,
                response: Response<CartListResponse>
            ) {
                println("onResponse 호출됨 : ${response.body().toString()}")

                showToast("정보 업로드 완료됨")
            }

            override fun onFailure(call: retrofit2.Call<CartListResponse>, t: Throwable) {
                println("postCartAdd onFailure 호출됨 : ${t.message}")
            }
        })
    }

    fun showToast(message:String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}