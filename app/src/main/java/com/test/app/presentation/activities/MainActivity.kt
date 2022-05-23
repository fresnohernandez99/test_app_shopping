package com.test.app.presentation.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.test.app.R
import com.test.app.databinding.ActivityMainBinding
import com.test.app.presentation.base.BaseActivity
import com.test.app.utils.imageLoader.ImageLoader
import com.test.login.facebook.FacebookLoginData
import com.test.login.google.GoogleLoginData
import com.test.login.models.UserDataModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var googleLoginData: GoogleLoginData

    @Inject
    lateinit var facebookLoginData: FacebookLoginData

    @Inject
    lateinit var imageLoader: ImageLoader

    override var navHostId = R.id.nav_host_fragment_content_main

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private lateinit var appBarConfiguration: AppBarConfiguration

    // region -> lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        setNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_products, R.id.nav_profile, R.id.nav_configs, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        updateUiUserData()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    // endregion -> lifecycle

    private fun updateUiUserData() {
        googleLoginData.getData()?.let { user ->
            bindUserUI(user, false)
        }

        facebookLoginData.getData()?.let { user ->
            bindUserUI(user, true)
        }
    }

    private fun bindUserUI(user: UserDataModel, fromFacebook: Boolean) {
        binding.navView.getHeaderView(0)?.let {
            user.personName?.let { s ->
                it.findViewById<TextView>(R.id.nameTextView).text = s
            }

            user.personEmail.let { s ->
                it.findViewById<TextView>(R.id.emailTextView).text = s
            }

            if (fromFacebook) {
                facebookLoginData.getProfilePic { obj, _ ->
                    try {
                        val url =
                            obj!!.getJSONObject("picture").getJSONObject("data").getString("url")
                        imageLoader.loadUrl(url, it.findViewById(R.id.imageView), true)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else {
                user.photoUrl?.let { img ->
                    imageLoader.loadUri(
                        img,
                        it.findViewById(R.id.imageView),
                        true
                    )
                }
            }
        }
    }

}