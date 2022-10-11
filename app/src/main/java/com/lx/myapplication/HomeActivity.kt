package com.lx.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.myapplication.databinding.ActivityHomeBinding
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private val arrayList = ArrayList<ArtistData>()
    val displayList = ArrayList<ArtistData>()
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
        this.artistList.layoutManager = layoutManager

        // 어댑터 설정 및 아이템의 모양잡기
        artistAdapter = ArtistAdapter()
        this.artistList.adapter = artistAdapter
        with(binding) {
            artistList.layoutManager = layoutManager

            // 어댑터 설정 및 아이템의 모양잡기
            artistAdapter = ArtistAdapter()
            artistList.adapter = artistAdapter
            searchView.setOnQueryTextListener(searchViewListener)
        }


        // 데이터 넣기
        artistAdapter?.apply {
            this.items.add(ArtistData(R.drawable.bts,"BTS",))
            this.items.add(ArtistData(R.drawable.girlsgeneration,"Girl's Generation",))
            this.items.add(ArtistData(R.drawable.blackpinck,"BLACKPINK",))
            this.items.add(ArtistData(R.drawable.gidle,"G-IDLE",))
            this.items.add(ArtistData(R.drawable.jang,"Hyunseung Jang",))
            this.items.add(ArtistData(R.drawable.stayc,"STAYC",))
            this.items.add(ArtistData(R.drawable.bigbang,"BIGBANG",))
            this.items.add(ArtistData(R.drawable.aespa,"AESPA",))
            this.items.add(ArtistData(R.drawable.seventeen,"SEVENTEEN",))
            this.items.add(ArtistData(R.drawable.monstax,"MONSTAX",))
            this.items.add(ArtistData(R.drawable.ive,"IVE",))
            this.items.add(ArtistData(R.drawable.newjeans,"New Jeans",))
        }

    }
    var searchViewListener :SearchView.OnQueryTextListener =
        object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.isNotEmpty()){
                    displayList.clear()
                    val search = newText.toLowerCase(Locale.getDefault())
                    arrayList.forEach {
                        if(it.artistName!!.toLowerCase(Locale.getDefault()).contains(search)){
                            displayList.add(it)
                        }
                    }
                    binding.artistList.adapter!!.notifyDataSetChanged()
                } else {
                    displayList.clear()
                    displayList.addAll(arrayList)
                    binding.artistList.adapter!!.notifyDataSetChanged()
                }
                return true
            }
        }


}