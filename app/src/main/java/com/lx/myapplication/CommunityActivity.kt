package com.lx.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.lx.myapplication.databinding.ActivityCommunityBinding
import com.lx.myapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_community.*

class CommunityActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommunityBinding


    lateinit var feed: FeedFragment //프레그먼트feed
    lateinit var chat: ChatFragment //프레그먼트 chat
    lateinit var rank: RankFragment //프레그먼트 rank




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        feed = FeedFragment() //프레그먼트1 객체화
        chat = ChatFragment() //프레그먼트2 객체화
        rank = RankFragment() //프레그먼트3 객체화



        //페이지 넘어가는 상단바
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, feed).commit()

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0->{ //tab1로
                        replaceView(feed)
                    }
                    1->{ //tab2로
                        replaceView(chat)
                    }
                    2->{ //tab3로
                        replaceView(rank)
                    }
                }
            }
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0->{ //tab1로
                        replaceView(feed)
                    }
                    1->{ //tab2로
                        replaceView(chat)
                    }
                    2->{ //tab3로
                        replaceView(rank)
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0->{ //tab1로
                        replaceView(feed)
                    }
                    1->{ //tab2로
                        replaceView(chat)
                    }
                    2->{ //tab3로
                        replaceView(rank)
                    }
                }
            }

            private fun replaceView(tab: Fragment){
                var selectedFragment: Fragment?=null
                selectedFragment = tab
                selectedFragment?.let{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, it).commit()
                }
            }


        })
    }


    //    // 게시글에서 사진 찍은거 저장하기 / FeedFragment에서 사용
//    fun saveFile(bitmap: Bitmap) {
//
//        val filename = "capture.jpg"
//        openFileOutput(filename, Context.MODE_PRIVATE).use {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
//            it.close()
//
//            showToast("Saved in Gallary : ${filename}")
//
//            val uploadMethod = frag as FeedFragment
//            uploadMethod.uploadFile(filename)
//
//        }
//    }
    fun onFragmentChange(index:Int) {
        when(index){
            0 ->{
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, FeedFragment()).commit()
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}