package com.example.checkconnectionmodule

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast

class MainModule(application:Application) {

    interface ScanNotify {
        fun onDetected(response: Response)
    }

    companion object {
        var instance: MainModule? = null
        var scanNotify: ScanNotify? = null
        lateinit var mContext: Context

        fun initSDK(
            application: Application,
            isscanNotify: ScanNotify
        ) {
            instance = MainModule(application)
            scanNotify = isscanNotify
        }
    }

    init {
        mContext = application
        registerCallbackActivities(application)
    }

    fun registerCallbackActivities(application: Application) {
        application.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

                }

                override fun onActivityStarted(activity: Activity) {

                }

                override fun onActivityResumed(activity: Activity) {
                    isDebug(mContext)
                    isDevMode(mContext)
                    checkVPNStatus()

                }

                override fun onActivityPaused(activity: Activity) {
                    TODO("Not yet implemented")
                }

                override fun onActivityStopped(activity: Activity) {
                    TODO("Not yet implemented")
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                    TODO("Not yet implemented")
                }

                override fun onActivityDestroyed(activity: Activity) {
                    TODO("Not yet implemented")
                }

            }
        )
    }

    fun isDevMode(context: Context) {
        when {
            Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN -> {
                val isDeveloperEnable = Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
                    0
                ) != 0
                if (isDeveloperEnable) {
                    scanNotify?.onDetected(
                        Response(
                            1,
                            true
                        )
                    )
                } else {
                    scanNotify?.onDetected(
                        Response(
                            1,
                            false
                        )
                    )
                }
            }
            Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN -> {
                val isDeveloperEnable = Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED,
                    0
                ) != 0
                if (isDeveloperEnable) {
                    scanNotify?.onDetected(
                        Response(
                            1,
                            true
                        )
                    )
                } else {
                    scanNotify?.onDetected(
                        Response(
                            1,
                            false
                        )
                    )
                }
            }
            else -> {
                scanNotify?.onDetected(
                    Response(
                        1,
                        false
                    )
                )
            }
        }
    }

    fun isDebug(context: Context) {
        when {
            Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN -> {
                val isDebugEnable = Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Global.ADB_ENABLED,
                    0
                ) != 0
                if (isDebugEnable) {
                    scanNotify?.onDetected(
                        Response(
                            2,
                            true
                        )
                    )
                } else {
                    scanNotify?.onDetected(
                        Response(
                            2,
                            false
                        )
                    )
                }
            }
            Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN -> {
                @Suppress("DEPRECATION")

                val isDebugEnable = Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Secure.ADB_ENABLED,
                    0
                ) != 0
                if (isDebugEnable) {
                    scanNotify?.onDetected(
                        Response(
                            2,
                            true
                        )
                    )
                } else {
                    scanNotify?.onDetected(
                        Response(
                            2,
                            false
                        )
                    )
                }
            }
            else -> {
                scanNotify?.onDetected(
                    Response(
                        2,
                        false
                    )
                )
            }
        }
    }

    fun checkVPNStatus() {
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            Toast.makeText(mContext, "in New Android Version", Toast.LENGTH_LONG).show()
            val isVPNConnected =
                capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            if (isVPNConnected) {
                scanNotify?.onDetected(
                    Response(
                        3,
                        true
                    )
                )
            } else {
                scanNotify?.onDetected(
                    Response(
                        3,
                        false
                    )
                )
            }
        } else {
            Toast.makeText(mContext, "in old Android Version", Toast.LENGTH_LONG).show()
            val isVPNConnected =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN)!!.isConnectedOrConnecting
            if (isVPNConnected) {
                scanNotify?.onDetected(
                    Response(
                        3,
                        true
                    )
                )
            } else {
                scanNotify?.onDetected(
                    Response(
                        3,
                        false
                    )
                )
            }
        }
    }
}