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
            this.items.add(ArtistData(R.drawable.bts,"방탄소년단",))
            this.items.add(ArtistData(R.drawable.girlsgeneration,"소녀시대",))
            this.items.add(ArtistData(R.drawable.blackpinck,"BLACKPINK",))
            this.items.add(ArtistData(R.drawable.gidle,"여자아이들",))
            this.items.add(ArtistData(R.drawable.jang,"장현승",))
            this.items.add(ArtistData(R.drawable.stayc,"STAYC",))
            this.items.add(ArtistData(R.drawable.bigbang,"빅뱅",))
            this.items.add(ArtistData(R.drawable.aespa,"AESPA",))
            this.items.add(ArtistData(R.drawable.seventeen,"세븐틴",))
            this.items.add(ArtistData(R.drawable.monstax,"몬스타엑스",))
            this.items.add(ArtistData(R.drawable.ive,"아이브",))
            this.items.add(ArtistData(R.drawable.newjeans,"뉴진스",))
        }


    }
}