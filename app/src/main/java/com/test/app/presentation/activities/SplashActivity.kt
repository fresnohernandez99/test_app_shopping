package com.test.app.presentation.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.test.app.R
import com.test.app.databinding.ActivitySplashBinding
import com.test.app.presentation.base.BaseActivity
import com.test.login.facebook.FacebookLoginData
import com.test.login.facebook.FacebookLoginData.Companion.TAG_FACEBOOK_LOGIN
import com.test.login.google.GoogleLoginData
import com.test.login.google.GoogleLoginData.Companion.TAG_GOOGLE_LOGIN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var facebookLoginData: FacebookLoginData

    @Inject
    lateinit var googleLoginData: GoogleLoginData

    override var navHostId = 0

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    // region -> lifecycle
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        isFullScreen = true
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            delay(100)
            checkUserLogin()
            delay(1200)
        }
    }
    // endregion -> lifecycle

    // region -> check_data

    private fun checkUserLogin() {
        if (checkAppLogin() || checkFacebookLogin() || checkGoogleLogin()) {
            // Show main activity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            // Show login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun checkAppLogin() = if (false) {
        Log.i(TAG_APP_LOGIN, "Is login with app")
        true
    } else {
        Log.i(TAG_APP_LOGIN, "Is not login with app")
        false
    }

    private fun checkFacebookLogin() = if (facebookLoginData.isLoggedIn()) {
        Log.i(TAG_FACEBOOK_LOGIN, "Is login with facebook")
        binding.infoText.text = getString(R.string.login_facebook)
        true
    } else {
        Log.i(TAG_FACEBOOK_LOGIN, "Is not login with facebook")
        false
    }

    private fun checkGoogleLogin() = if (googleLoginData.isLoggedIn()) {
        Log.i(TAG_GOOGLE_LOGIN, "Is login with google")
        binding.infoText.text = getString(R.string.login_google)
        true
    } else {
        Log.i(TAG_GOOGLE_LOGIN, "Is not login with google")
        false
    }

    // endregion -> check_data

    companion object {
        const val TAG_APP_LOGIN = "app-login"
    }
}