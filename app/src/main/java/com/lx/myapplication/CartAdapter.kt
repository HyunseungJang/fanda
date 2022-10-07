package com.lx.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lx.myapplication.databinding.CartItemBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    // 각 아이템에 보여질 데이터를 담고 있는 것
    var items = ArrayList<CartData>()

    var listener:OnCartItemClickListener? = null

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
        fun setItem(item:CartData) {

            // 이미지 표시하기
            item.photo?.apply {
                binding.photoView.setImageResource(this)
            }

            // 제목 표시하기
            binding.nameOutPut.text = item.name
            // 가격 표시하기
            binding.priceOutput.text = item.price

        }
    }


}