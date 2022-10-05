package com.lx.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.myapplication.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    var artistAdapter:ArtistAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        List()
    }
    fun List() {
        // 리스트 모양
        val layoutManager = GridLayoutManager(this,3)
        binding.artistList.layoutManager = layoutManager




        // 어댑터 설정 및 아이템의 모양잡기
        artistAdapter = ArtistAdapter()
        binding.artistList.adapter = artistAdapter

        // 데이터 넣기
        artistAdapter?.apply {
            this.items.add(ArtistData(R.drawable.blackpinck,"블랙핑크",))
            this.items.add(ArtistData(R.drawable.girlsgeneration,"소녀시대",))
            this.items.add(ArtistData(R.drawable.bts,"방탄소년단",))
        }
    }
}