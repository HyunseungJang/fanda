package com.lx.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.list.OnPlaceItemClickListener
import com.example.list.PlaceAdapter
import com.lx.myapplication.databinding.FragmentPlaceBinding

class PlaceFragment : Fragment() {
    var _binding: FragmentPlaceBinding? = null

    var placeAdapter: PlaceAdapter? = null

    val placeInfoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

    }
    val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)

        // 리스트 초기화
        initList()

        return binding.root
    }


    fun initList() {
        // 1. 리스트의 모양을 담당하는 것
        var layoutManager = LinearLayoutManager(context)
        binding.placeList.layoutManager = layoutManager

        // 2. 어댑터를 설정하는 것
        // 실제 데이터를 관리하고 갓 아이템의 모양을 만들어주는 것
        placeAdapter = PlaceAdapter()
        binding.placeList.adapter = placeAdapter

        // 3. 테스트로 아이템을 위한 데이터 넣어보기
        placeAdapter?.apply {

            this.items.add(PlaceData(R.drawable.cafehuga, "Cafe Hyuha", "A cafe that renovated\nBTS' accommodation", "Like 539",
                "South Korea, Seoul, Gangnam-gu, Nonhyeon-ro 119-il, 16",
                "You can see the accommodation\nwhere the BTS lived before their debut.\nAfter BTS won the 2020 MAMA Grand Prize,\n" +
                        "They visited the place again and made headlines.",
                R.drawable.huga, R.drawable.cafehuga1, R.drawable.cafehuga2, R.drawable.cafehuga3
            ))
            this.items.add(PlaceData(R.drawable.mvplace, "Yongin Dae Jang Geum Park", "Agust D's Daechwita Music Video a filming location", "Like 408",
                "778-1 Yongchoeon-ri, Baegam-myeon, Cheoin-gu, Yongin-si, Gyeonggi-do, South Korea",
                "This is the location of BTS Suga's Daechwita\nMusic Video Set.\n" +
                        "If you use a tour car, you can hear the explanation of the filming location in more detail.",
                R.drawable.mv, R.drawable.mv1, R.drawable.mv2, R.drawable.mv3
            ))
            this.items.add(PlaceData(R.drawable.btsrestaurant, "Born and Bred", "The Korean Beef Omakase Restaurant that BTS often visits", "Like 219",
                "1 Majangno 42-gil, Seongdong-gu, Seoul, South Korea",
                "This is the restaurant Suga mentioned in an interview during the world tour, and it is a Fine Dining Restaurant where BTS members had a group dinner.",
                R.drawable.bab, R.drawable.bab1, R.drawable.bab2, R.drawable.bab3
            ))
            this.items.add(PlaceData(R.drawable.dadaepo, "Busan Dadaepo Beach", "It is the place that BTS Jimin visited and also released in his Vlog video", "Like 138",
                "Dadaepo Beach, 680 Dadae-ro, Dadae-dong, Saha-gu, Busan, South Korea",
                "Dadaepo Beach, which became a pilgrimage site for overseas ARMYs to Korea, is where Jimin visited his hometown of Busan in 2016.",
                R.drawable.dadaepo1, R.drawable.dadaepo2, R.drawable.dadaepo3, R.drawable.dadaepo4
            ))

        }

        // 4. 아이템을 클릭했을 때 동작할 코드 넣어주기
        placeAdapter?.listener = object: OnPlaceItemClickListener {
            override fun onItemClick(holder: PlaceAdapter.ViewHolder?, view: View?, position: Int) {
                placeAdapter?.apply {
                    val item = items.get(position)

                    AppData.selectedItem = item

                    val placeInfoIntent = Intent(context, PlaceInfoActivity::class.java)
                    placeInfoLauncher.launch(placeInfoIntent)

                }

            }
        }
    }

}
