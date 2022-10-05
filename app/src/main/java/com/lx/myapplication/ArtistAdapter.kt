package com.lx.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lx.myapplication.databinding.ArtistItemBinding

class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {
    var items = ArrayList<ArtistData>()
    var listener: OnArtistItemClickListener? = null

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistAdapter.ViewHolder {
        val binding = ArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class ViewHolder(val binding: ArtistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener?.onItemClick(this, binding.root, adapterPosition)
            }
        }

        // 데이터 설정
        fun setItem(item: ArtistData) {
            // 이미지 표시하기
            item.profile?.apply {
                binding.itemImage.setImageResource(item.profile)
            }
            binding.iconMainText.text = item.artistName

        }
    }
}