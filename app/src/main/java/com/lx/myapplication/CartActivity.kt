package com.lx.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.myapplication.api.BasicClient
import com.lx.myapplication.data.CartListResponse
import com.lx.myapplication.databinding.ActivityCartBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding

    //cartAdapter라는 변수상자
    var cartAdapter: CartAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //뷰 초기화
        initView()

        //리스트초기화
        initList()

    }

    //리스트 초기화
    fun initList() {

        //리스트의 모양을 담당하는 것
        var layoutManager = LinearLayoutManager(this)
        binding.productList.layoutManager = layoutManager

        //어댑터를 설정하는 것 (실제 데이터 담당, 아이템의 모양을 만들어줌)
        cartAdapter = CartAdapter()
        binding.productList.adapter = cartAdapter

        //테스트로 아이템을 위한 데이터 넣어보기
//        cartAdapter?.apply {
//            this.items.add(CartData(R.drawable.character1, "테스트아이템 들어갔어요", "2222원"))
//
//        }

        // 4. 아이템을 클릭했을 때 동작할 코드 넣어주기
        cartAdapter?.listener = object: OnCartItemClickListener {
            override fun onItemClick(holder: CartAdapter.ViewHolder?, view: View?, position: Int) {
                cartAdapter?.apply {
                    val item = items.get(position)
                    showToast("아이템 선택됨 : ${position}, ${item.name}, ${item.price}")

                }
            }
        }


    }

    fun initView() {

        // API에 있는 리스트 조회 요청하기
        BasicClient.api.getCartList(
            requestCode = "1001"
        ).enqueue(object: Callback<CartListResponse> {
            override fun onResponse(call: Call<CartListResponse>, response: Response<CartListResponse>) {
                printLog("onResponse 호출됨 : ${response.body().toString()}")

                // 리스트에 추가
                addToCartList(response)
            }

            override fun onFailure(call: Call<CartListResponse>, t: Throwable) {
                printLog("onFailure 호출됨 : ${t.message}")

            }
        })

        printLog("getCartList 요청함.")

    }

    // 응답받은 데이터를 화면에 있는 리스트에 추가하기
    fun addToCartList(response: Response<CartListResponse>) {
        cartAdapter?.apply {
            response.body()?.output?.data?.let {
                for (item in it) {
                    //this.items.add(CartData(R.drawable.profile1, item.name, item.age, item.mobile))
                    this.items.add(CartData(R.drawable.character1, item.name, item.price))
                }
            }

            this.notifyDataSetChanged()
        }
    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun printLog(message:String) {
        binding.logOutput.append("${message}\n")
    }
}