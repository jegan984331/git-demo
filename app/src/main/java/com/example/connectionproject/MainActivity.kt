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
                    Log.i(TAG, "===========>>>> onDetected: code: ${response.code} --- isDetected: ${response.detected}")
                }
            }
        )

    }
}