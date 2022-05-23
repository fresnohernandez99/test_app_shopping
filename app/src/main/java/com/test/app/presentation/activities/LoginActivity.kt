package com.test.app.presentation.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.test.app.databinding.ActivityLoginBinding
import com.test.app.presentation.base.BaseActivity
import com.test.login.facebook.FacebookLoginData
import com.test.login.facebook.FacebookLoginData.Companion.TAG_FACEBOOK_LOGIN
import com.test.login.google.GoogleLoginData
import com.test.login.google.GoogleLoginData.Companion.TAG_GOOGLE_LOGIN
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    @Inject
    lateinit var facebookLoginData: FacebookLoginData

    @Inject
    lateinit var googleLoginData: GoogleLoginData

    override var navHostId = 0

    private val callbackManager = CallbackManager.Factory.create()

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

    // region -> lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFacebookLoginCallback()

        binding.facebookLogin.setOnClickListener {
            toggleLoader(true)
            callFacebookLogin()
        }

        binding.googleLogin.setOnClickListener {
            toggleLoader(true)
            callGoogleLogin()
        }
    }
    // endregion -> lifecycle

    // region -> facebook
    private fun callFacebookLogin() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, callbackManager, listOf("public_profile"))
    }

    private fun setFacebookLoginCallback() {
        facebookLoginData.loginManager()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Log.w(TAG_FACEBOOK_LOGIN, "login cancel")
                    toggleLoader(false)
                }

                override fun onError(error: FacebookException) {
                    Log.e(TAG_FACEBOOK_LOGIN, "login error")
                    toggleLoader(false)
                }

                override fun onSuccess(result: LoginResult) {
                    Log.i(TAG_FACEBOOK_LOGIN, "login success")

                    val pt = object : ProfileTracker() {
                        override fun onCurrentProfileChanged(
                            oldProfile: Profile?,
                            currentProfile: Profile?
                        ) {
                            toggleLoader(false)

                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                            this.stopTracking()
                        }

                    }
                    pt.startTracking()
                }
            })
    }
    // endregion -> facebook

    // region -> google
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.i(TAG_GOOGLE_LOGIN, "${result.resultCode}")
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleGoogleSignInResult(task)
            }
        }

    private fun callGoogleLogin() {
        val signInIntent = googleLoginData.googleClient().signInIntent
        launcher.launch(signInIntent)
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            toggleLoader(false)
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            Log.i(TAG_GOOGLE_LOGIN, "login success")

            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        } catch (e: ApiException) {
            Log.e(TAG_GOOGLE_LOGIN, "login error: ${e.statusCode}")
        }
    }
    // endregion -> google

}
