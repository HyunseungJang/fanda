package com.lx.myapplication

import android.app.AlertDialog
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.lx.myapplication.AppData.Companion.reward
import com.lx.myapplication.databinding.FragmentMyBinding
import java.text.SimpleDateFormat

class MyFragment : Fragment() {
    var _binding: FragmentMyBinding? = null
    val binding get() = _binding!!
    var itemAdapter: NoticeAdapter? = null
    val itemInfoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    var bitmap: Bitmap? = null
    val cr: ContentResolver? = null

    // QR추가
    private val TAG = "MyFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)



        binding.rewardPoints.setText("${AppData.reward} points")

        // 뷰 초기화
        initView()
        // 리스트 초기화
        initList()

        // qr찍고돌아오면 1000점씩 쌓인다
        binding.showPoints.setOnClickListener { view ->
            binding.progressBar.incrementProgressBy(1000)
            AppData.reward = AppData.reward!! + 1000
            binding.rewardPoints.text = "${AppData.reward} points"

            if (AppData.reward == 10000) {
                binding.rewardText.text = "You can get Special Coupon!"
                binding.rewardView.setImageResource(R.drawable.ok)
            } else if (AppData.reward == 11000) {
                binding.rewardText.text = "You can`t get more rewards"
                binding.rewardView.setImageResource(R.drawable.beacon)
                binding.rewardPoints.text = "10000"
            } else if (AppData.reward == 12000) {
                binding.rewardText.text = "You can`t get more rewards"
                binding.rewardView.setImageResource(R.drawable.beacon)
                binding.rewardPoints.text = "10000"
            } else if (AppData.reward == 13000) {
                binding.rewardText.text = "You can`t get more rewards"
                binding.rewardView.setImageResource(R.drawable.beacon)
                binding.rewardPoints.text = "10000"
            } else if (AppData.reward == 14000) {
                binding.rewardText.text = "You can`t get more rewards"
                binding.rewardView.setImageResource(R.drawable.beacon)
                binding.rewardPoints.text = "10000"
            } else if (AppData.reward == 15000) {
                binding.rewardText.text = "You can`t get more rewards"
                binding.rewardView.setImageResource(R.drawable.beacon)
                binding.rewardPoints.text = "10000"
            }
        }

        // Special Coupon
        binding.rewardButton3.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            when (AppData.reward) {
                in 0..9999 -> binding.rewardText.text =
                    "Please collecting 10,000 points."
                in 10000..15000 -> {
                    builder.setTitle("Congratulation!")
                        .setMessage(
                            "Tmon korea tour package\n" +
                                    "Please write QRCode 'special'\n" +
                                    "URL : 'www.tmon.co.kr'"
                        )
                        .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, id ->
                            })

                    builder.show()
                    binding.rewardText.text = "Lucky! Please Write 'special'"
                    binding.rewardWrite3.text = "'special'"
                }
            }
        }

        // Cafe Coupon
        binding.rewardButton1.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            when (AppData.reward) {
                in 0..4999 -> binding.rewardText.text =
                    "Please collecting 5,000 points."
                in 5000..10000 -> {
                    builder.setTitle("Cafe Hyuga Coupon")
                        .setMessage("Please write QRCode 'cafe'")
                        .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, id ->
                            })
                    builder.show()
                    AppData.reward = AppData.reward!! - 5000
                    binding.rewardPoints.text = "${AppData.reward} points"
                    binding.rewardText.text = "Lucky! Please Write 'cafe' Code"
                    binding.rewardWrite1.text = "'cafe'"
                }
            }
        }

        // Restaurant Coupon
        binding.rewardButton2.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            when (AppData.reward) {
                in 0..4999 -> binding.rewardText.text =
                    "Please collecting 5,000 points."
                in 5000..10000 -> {
                    builder.setTitle("Born and Bred Coupon")
                        .setMessage("Please write QRCode 'born'")
                        .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, id ->
                            })
                    builder.show()
                    AppData.reward = AppData.reward!! - 5000
                    binding.rewardPoints.text = "${AppData.reward} points"
                    binding.rewardText.text = "Lucky! Please Write 'born' Code"
                    binding.rewardWrite2.text = "'born'"
                }
            }
        }

        // QR 생성
        binding.generateBarcode.setOnClickListener {
            val barcodeEncoder = BarcodeEncoder()
            val barcodeBitmap = barcodeEncoder.encodeBitmap(
                binding.barcodeText.text.toString(),
                BarcodeFormat.QR_CODE,
                400,
                400
            )
            binding.barcodeImage.setImageBitmap(barcodeBitmap)
            // QR입력하면 텍스트뷰에 사용했다고 표시됨
            if(binding.rewardWrite3.text == "'special'") {
                binding.rewardWrite3.text = "'used'"
            } else if(binding.rewardWrite1.text == "'cafe'") {
                binding.rewardWrite1.text = "'used'"
            } else if(binding.rewardWrite2.text == "'born'") {
                binding.rewardWrite2.text = "'used'"
            }
        }
        return binding.root
    }

    // QR생성
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {}
            if (result.barcodeImagePath != null) {
                Log.i(TAG, "onActivityResult: ${result.barcodeImagePath}")
                val bitmap = BitmapFactory.decodeFile(result.barcodeImagePath)
                binding.barcodeImage.setImageBitmap(bitmap)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    //뷰 초기화
    fun initView() {

    }

    // 리스트 초기화
    fun initList() {
        // 1. 리스트의 모양을 담당하는 것
        // (LinearLayoutManager : 아래쪽으로 아이템들이 보이는 것, GridLayoutManager : 격자 형태로 보이는 것)
        val layoutManager = LinearLayoutManager(activity)
        binding.rewardItemList.layoutManager = layoutManager

        // 2. 어댑터를 설정하는 것
        // 실제 데이터를 관리하고 각 아이템의 모양을 만들어주는 것
        itemAdapter = NoticeAdapter()
        binding.rewardItemList.adapter = itemAdapter

        // 3. 아이템에 데이터 넣어보기
        itemAdapter?.apply {
            this.items.add(
                NoticeData(
                    R.drawable.moo,
                    R.drawable.blank,
                    "  Hello! My name is",
                    "  ${AppData.user}"
                )
            )
        }
    }
}