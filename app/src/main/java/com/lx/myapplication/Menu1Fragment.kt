package com.lx.myapplication

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.lx.myapplication.api.BasicClient
import com.lx.myapplication.api.BasicClient.Companion.api
import com.lx.data.CartListResponse
import com.lx.myapplication.databinding.FragmentMenu1Binding
import retrofit2.Callback
import retrofit2.Response

class Menu1Fragment : Fragment() {
    var _binding: FragmentMenu1Binding? = null
    val binding get() = _binding!!

    val cartLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMenu1Binding.inflate(inflater, container, false)

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

        binding.cartButton1.setOnClickListener {
            postCartAdd1()

            // 팝업창 띄우기
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Product has been added.")
                .setMessage("Would you like to move to the shopping cart?")
                .setPositiveButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
//                        resultText.text = "확인 클릭"
                    })
                .setNegativeButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        val cartIntent = Intent(context, CartActivity::class.java)
                        cartLauncher.launch(cartIntent)
                    })
            // 다이얼로그를 띄워주기
            builder.show()

        }

        binding.orderButton2.setOnClickListener {
            postCartAdd2()

            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

        binding.cartButton2.setOnClickListener {
            postCartAdd2()

            // 팝업창 띄우기
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Product has been added.")
                .setMessage("Would you like to move to the shopping cart?")
                .setPositiveButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
//                        resultText.text = "확인 클릭"
                    })
                .setNegativeButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        val cartIntent = Intent(context, CartActivity::class.java)
                        cartLauncher.launch(cartIntent)
                    })
            // 다이얼로그를 띄워주기
            builder.show()

        }

        binding.orderButton3.setOnClickListener {
            postCartAdd3()

            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

        binding.cartButton3.setOnClickListener {
            postCartAdd3()

            // 팝업창 띄우기
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Product has been added.")
                .setMessage("Would you like to move to the shopping cart?")
                .setPositiveButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
//                        resultText.text = "확인 클릭"
                    })
                .setNegativeButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        val cartIntent = Intent(context, CartActivity::class.java)
                        cartLauncher.launch(cartIntent)
                    })
            // 다이얼로그를 띄워주기
            builder.show()

        }

        binding.orderButton4.setOnClickListener {
            postCartAdd4()

            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

        binding.cartButton4.setOnClickListener {
            postCartAdd2()

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Product has been added.")
                .setMessage("Would you like to move to the shopping cart?")
                .setPositiveButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
//                        resultText.text = "확인 클릭"
                    })
                .setNegativeButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        val cartIntent = Intent(context, CartActivity::class.java)
                        cartLauncher.launch(cartIntent)
                    })
            builder.show()

        }

        binding.orderButton5.setOnClickListener {
            postCartAdd5()

            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

        binding.cartButton5.setOnClickListener {
            postCartAdd5()

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Product has been added.")
                .setMessage("Would you like to move to the shopping cart?")
                .setPositiveButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
//                        resultText.text = "확인 클릭"
                    })
                .setNegativeButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        val cartIntent = Intent(context, CartActivity::class.java)
                        cartLauncher.launch(cartIntent)
                    })
            builder.show()

        }

        binding.orderButton6.setOnClickListener {
            postCartAdd6()

            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

        binding.cartButton6.setOnClickListener {
            postCartAdd6()

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Product has been added.")
                .setMessage("Would you like to move to the shopping cart?")
                .setPositiveButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
//                        resultText.text = "확인 클릭"
                    })
                .setNegativeButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        val cartIntent = Intent(context, CartActivity::class.java)
                        cartLauncher.launch(cartIntent)
                    })
            builder.show()

        }

    }

    fun postCartAdd1() {
        // API에 있는 리스트 조회 요청하기
        val name = binding.name1.text.toString()
        val price = binding.price1.text.toString()
        val filepath = "/images/menu11665371296026.png"

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
                // 성공 응답
                println("onResponse 호출됨 : ${response.body().toString()}")

                showToast("정보 업로드 완료됨")
            }

            override fun onFailure(call: retrofit2.Call<com.lx.data.CartListResponse>, t: Throwable) {
                // 실패 응답
                println("postCartAdd onFailure 호출됨 : ${t.message}")
            }
        })
    }

    fun postCartAdd2() {
        val name = binding.name2.text.toString()
        val price = binding.price2.text.toString()
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

    fun postCartAdd3() {
        val name = binding.name2.text.toString()
        val price = binding.price2.text.toString()
        val filepath = "/images/menu21665371328157.png"

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

    fun postCartAdd4() {
        val name = binding.name2.text.toString()
        val price = binding.price2.text.toString()
        val filepath = "/images/menu111665371507652.png"

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

    fun postCartAdd5() {
        val name = binding.name2.text.toString()
        val price = binding.price2.text.toString()
        val filepath = "/images/menu51665371391271.png"

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

    fun postCartAdd6() {
        val name = binding.name2.text.toString()
        val price = binding.price2.text.toString()
        val filepath = "/images/menu121665371525697.png"

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

