package com.lx.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.lx.myapplication.databinding.ArtistItemBinding

class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {
    var items = ArrayList<ArtistData>()
    var listener: OnArtistItemClickListener? = null

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistAdapter.ViewHolder {
        val binding = ArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.joinButton.setOnClickListener {
            val nextIntent = Intent(ViewHolder(binding).itemView?.context, MainActivity::class.java)
            ContextCompat.startActivity(ViewHolder(binding).itemView.context, nextIntent, null)
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, MainActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
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