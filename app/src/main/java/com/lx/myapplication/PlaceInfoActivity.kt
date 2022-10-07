package com.lx.myapplication


import android.Manifest
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import com.lx.myapplication.databinding.ActivityPlaceInfoBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.permissionx.guolindev.PermissionX

    class PlaceInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlaceInfoBinding

    var locationClient:FusedLocationProviderClient? = null

    lateinit var map: GoogleMap

    var myMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var qrCodeScan = QRCodeScan(this)

        // 이전 화면에서 선택한 아이템의 데이터를 이 화면에 있는 텍스트뷰에 보여주기
        AppData.selectedItem?.apply {
            binding.placeOutput.text = "${this.place}"
            binding.placeInfoOutput.text = "${this.placeInfo}"
            binding.likeInfoOutput.text = "${this.like}"
            binding.InfoOutput.text = "${this.info}"
        }

        binding.artistImage.setImageResource(R.drawable.huga)
        binding.infoImageView1.setImageResource(R.drawable.cafehuga1)
        binding.infoImageView2.setImageResource(R.drawable.cafehuga2)
        binding.infoImageView3.setImageResource(R.drawable.cafehuga3)


        // 주문하기 버튼 눌렀을 때
        binding.orderButton.setOnClickListener {
            val nextIntent = Intent(this, OrderActivity::class.java)
            startActivity(nextIntent)
        }
        // 방문인증하기 버튼 눌렀을 때
        binding.beaconButton.setOnClickListener {
            qrCodeScan.startQRScan()
        }
        // 사진촬영하기 버튼 눌렀을 떄
        binding.arButton.setOnClickListener {

        }

        // Google Map으로 이동 버튼 눌렀을 때
        binding.gmButton.setOnClickListener {

        }

        // 위험 권한 요청하기
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .request { allGranted, grantedList, deniedList ->
                if(allGranted) {
                    showToast("모든 권한 부여됨")
                } else {
                    showToast("거부된 권한 있음")
                }
            }

        // 지도 초기화하기
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it

            // 내 위치 요청하기(바로 뜨게 하는 것)
            requestLocation()

            // 마커 클릭 시 처리
            map.setOnMarkerClickListener {
                showToast("마커 클릭 됨: ${it.title}")

                // 필요 시 다른 화면으로 이동(tag 정보를 이용해서 구분함)


                true
            }

            // 지도 클릭 시 처리
            map.setOnMapClickListener {
                showToast("지도 클릭 됨: ${it.latitude}, ${it.longitude}")
            }

            // 보고있는 지도 영역 구분
            map.setOnCameraIdleListener {
                val bounds = map.projection.visibleRegion.latLngBounds

                val zoomLevel = map.cameraPosition.zoom
                println("zoomLevel: ${zoomLevel}")
            }

        }

        // 내 위치 버튼 눌렀을 떄
        binding.myLocationButton.setOnClickListener {
            requestLocation()
        }

    }

    fun requestLocation() {
        try {
            // 가장 최근에 확인된 위치 알려주기
            locationClient?.lastLocation?.addOnSuccessListener {
                showToast("최근 위치: ${it.latitude}, ${it.longitude}")
            }

            // 위치 클라이언트 만들기
            locationClient = LocationServices.getFusedLocationProviderClient(this)

            // 내 위치를 요청할 때 필요한 설정값
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 30 * 1000
            }

            // 내 위치를 받았을 때 자동으로 호출되는 함수
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    for ((index, location) in result.locations.withIndex()) {
                        showToast("내 위치: ${location.latitude}, ${location.longitude}")

                        showCurrentLocation(location)
                    }
                }
            }

            // 내 위치 요청
            locationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        } catch(e:SecurityException) {
            e.printStackTrace()
        }
    }

    // 내 위치의 지도 보여주기
    fun showCurrentLocation(location: Location) {
        val curPoint = LatLng(location.latitude, location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 17.0f))

        showMarker(curPoint)
    }

    fun showMarker(curPoint:LatLng) {
        myMarker?.remove()

        MarkerOptions().also {
            it.position(curPoint)
            it.title("내 위치")
            it.icon(BitmapDescriptorFactory.fromResource(R.drawable.location))

            myMarker = map.addMarker(it)
        }

    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}