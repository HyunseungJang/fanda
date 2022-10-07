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
import com.example.list.PlaceData
import com.lx.myapplication.databinding.FragmentPlaceBinding

class PlaceFragment : Fragment() {
    var _binding: FragmentPlaceBinding? = null

    var placeAdapter:PlaceAdapter? = null

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
            this.items.add(PlaceData(R.drawable.cafehuga, "카페 휴가", "방탄소년단 숙소를 리모델링하여\n만든 카페", "좋아요 539",
                "서울 강남구 논현로119길 16",
                "데뷔 전 방탄소년단이 생활했던 숙소를 그대로 느껴볼 수 있다.\n방탄소년단이 2020 MAMA 대상 수상 후\n다시 이 장소를 방문하여 화제가 되었다."
            ))
            this.items.add(PlaceData(R.drawable.mvplace, "용인 대장금 파크", "방탄소년단의 대취타 뮤직비디오\n촬영지", "좋아요 408",
                "경기 용인시 처인구 백암면 용천로 330",
                "슈가의 대취타 뮤직비디오 촬영지로 투어카를 이용하면\n특히 대취타 촬영지에 대한 설명을 더 자세히 들을 수 있다."
            ))
            this.items.add(PlaceData(R.drawable.btsrestaurant, "본앤브레드", "방탄소년단이 자주 방문하는 한우\n오마카세 식당", "좋아요 219",
                "서울 성동구 마장로42길 1",
                "슈가가 월드투어중 인터뷰에서 언급했던 식당으로\nBTS 멤버들이 단체회식을 했던 파인다이닝 식당이다."
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