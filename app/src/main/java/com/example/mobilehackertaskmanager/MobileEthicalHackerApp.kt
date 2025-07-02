package com.example.mobilehackertaskmanager

import android.app.Application
import com.example.mobilehackertaskmanager.data.firebase.FirebaseConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MobileEthicalHackerApp : Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseConfig.init(this)
    }
}