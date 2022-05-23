package com.test.app

import androidx.multidex.MultiDexApplication
import com.test.login.facebook.FacebookLoginData
import com.test.login.google.GoogleLoginData
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TestApp : MultiDexApplication() {

    @Inject
    lateinit var facebookLoginData: FacebookLoginData

    @Inject
    lateinit var googleLoginData: GoogleLoginData

    override fun onCreate() {
        super.onCreate()

        facebookLoginData.startFacebookSDK(this)
        googleLoginData.build()
    }
}