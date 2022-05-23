package com.test.app.presentation.fragments.logout

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.test.app.databinding.FragmentLogoutBinding
import com.test.app.presentation.activities.SplashActivity
import com.test.app.presentation.base.BaseFragment
import com.test.login.facebook.FacebookLoginData
import com.test.login.google.GoogleLoginData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LogoutFragment : BaseFragment<FragmentLogoutBinding>() {

    @Inject
    lateinit var facebookLoginData: FacebookLoginData

    @Inject
    lateinit var googleLoginData: GoogleLoginData

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLogoutBinding
        get() = FragmentLogoutBinding::inflate

    private val viewModel: LogoutViewModel by viewModels()

    override fun bindData() {
        if (!facebookLoginData.isLoggedIn()) binding.facebookLogin.visibility = View.GONE

        if (!googleLoginData.isLoggedIn()) binding.googleLogin.visibility = View.GONE

        binding.facebookLogin.setOnClickListener {
            facebookLoginData.loginManager().logOut()
            restartApp()
        }

        binding.googleLogin.setOnClickListener {
            googleLoginData.googleClient().signOut()
            restartApp()
        }
    }

    private fun restartApp() {
        startActivity(Intent(requireActivity(), SplashActivity::class.java))
        requireActivity().finish()
    }
}