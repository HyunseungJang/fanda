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

        //바로 주문하기 버튼 눌렀을때
        binding.orderButton1.setOnClickListener {
//            postCartAdd("${name1}", "${price1}")

            //cart activity로 이동
            val cartIntent = Intent(context, CartActivity::class.java)
            cartLauncher.launch(cartIntent)

        }

    }

    fun postCartAdd(name:String, price:String) {
        // API에 있는 리스트 조회 요청하기
        BasicClient.api.postCartAdd(
            requestCode = "1001",
            name = name,
            price = price
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

    fun showToast(message:String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}
