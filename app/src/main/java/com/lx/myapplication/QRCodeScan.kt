package com.lx.myapplication

import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

//QRCodeScan.kt
class QRCodeScan(private val act: PlaceInfoActivity) {

    /** QRCode Scan */
    fun startQRScan(){
        val intentIntegrator = IntentIntegrator(act)

        intentIntegrator.setPrompt("FANDA QRCode Scan") //QR코드 스캔 액티비티 하단에 띄울 텍스트 설정
        intentIntegrator.setOrientationLocked(true)    //화면회전을 막을 것인지 설정 true회전 X/false 회전 ㅇ
        intentIntegrator.setBeepEnabled(false)          //QR코드 스캔 시 소리 true/false
        activityResult.launch(intentIntegrator.createScanIntent())
    }

    /** onActivityResult */
    private val activityResult = act.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        val data = result.data

        val intentResult: IntentResult? = IntentIntegrator.parseActivityResult(result.resultCode, data)
        if(intentResult != null){
            //QRCode Scan 성공
            if(intentResult.contents != null){



                //QRCode Scan result 있는경우
                Toast.makeText(act, "인식된 QR-data: ${intentResult.contents}", Toast.LENGTH_SHORT).show()
                // text로 보여주자


            }else{
                //QRCode Scan result 없는경우
                Toast.makeText(act, "인식된 QR-data가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }else{
            //QRCode Scan 실패
            Toast.makeText(act, "QR스캔에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }

    }
}