package com.lx.myapplication

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lx.myapplication.api.BasicClient
import com.lx.myapplication.data.CartListResponse
import com.lx.myapplication.databinding.CartItemBinding
import retrofit2.Callback
import retrofit2.Response

class CartAdapter : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    // 각 아이템에 보여질 데이터를 담고 있는 것
    var items = ArrayList<CartData>()

    var listener: OnCartItemClickListener? = null

    //삭제할 때 값 불러오기 위해 만든 ??
    var a = 0

    // 리싸이클러뷰가 아이템 개수가 몇 개인지 물어볼 때
    override fun getItemCount(): Int = items.size

    // 각 아이템의 모양이 처음 만들어질 때
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 이미 만들어진 아이템의 모양에 데이터만 설정할 때
    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    // 각 아이템의 모양을 재사용하기 위해 만들어진 것
    inner class ViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener?.onItemClick(this, binding.root, adapterPosition)
            }
        }

        // 하나의 아이템을 위한 데이터가 전달되었을 때 화면에 어떻게 표시할 지 설정
        fun setItem(item: CartData) {

            // 제목 표시하기
            binding.nameOutPut.text = item.name
            // 가격 표시하기
            binding.priceOutput.text = item.price

            // 이미지 표시하기

//            item.photo?.apply {
//                binding.photoOutput.setImageResource(item.photo)
//            }

//            item.photo?.let { binding.photoOutput.setImageResource(String) }

            // 글라이드를 이용해서 웹서버에 올린 이미지를 가져와서 보여주기
            item.photo?.apply {
                //val uri = Uri.parse("http://172.168.10.11:8001/images/profile21664852389750.jpg")
                val uri = Uri.parse("http://192.168.35.217:8001${this}")

                Glide.with(binding.photoOutput)         // 글라이드를 사용하는데,
                    .load(uri)                              // 이미지 파일을 읽어와서,
                    .into(binding.photoOutput)                  // 이미지뷰에 넣어주세요
            }

            //삭제할 때 필요한 값
            a = item.id!!

            //삭제버튼 눌렀을 때
            binding.deleteButton.setOnClickListener {
                postCartDelete()
            }

        }
    }

    fun postCartDelete() {

        BasicClient.api.postCartDelete(
            requestCode = "1001",
            id = a.toString()

        ).enqueue(object : Callback<CartListResponse> {
            override fun onResponse(
                call: retrofit2.Call<CartListResponse>,
                response: Response<CartListResponse>
            ) {
                println("onResponse 호출됨 : ${response.body().toString()}")

            }

            override fun onFailure(call: retrofit2.Call<CartListResponse>, t: Throwable) {
                println("postCartDelete onFailure 호출됨 : ${t.message}")
            }
        })

    }




}