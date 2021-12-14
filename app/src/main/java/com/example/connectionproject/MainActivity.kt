package com.example.connectionproject

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.checkconnectionmodule.MainModule
import com.example.checkconnectionmodule.Response
import com.example.connectionproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MainModule.initSDK(
            applicationContext as Application,
           object : MainModule.ScanNotify{
                override fun onDetected(response: Response) {
                    when(response.code){
                        1->if (response.detected==true){
                            binding.showDeveloperOption.setText("DeveloperEnable").toString()

                        }else{
                            binding.showDeveloperOption.setText("DevelopernotEnable").toString()
                        }
                        2->if (response.detected==true){
                            binding.showUSBDebug.setText("DebugEnable").toString()

                        }else{
                            binding.showUSBDebug.setText("DebugnotEnable").toString()
                        }
                        3->if (response.detected==true){
                            binding.showVpn.setText("vpnEnable").toString()

                        }else{
                            binding.showVpn.setText("vpnnotEnable").toString()
                        }


                    }
                    Log.i(TAG, "===========>>>> onDetected: code: ${response.code} --- isDetected: ${response.detected}")
                }
            }
        )

    }
}