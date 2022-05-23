package com.test.app.presentation.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.test.warofsuits.ui.dialogs.CustomProgressDialog

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    abstract var navHostId: Int

    var isFullScreen: Boolean = false

    private var _binding: VB? = null
    val binding: VB get() = _binding!!
    abstract val bindingInflater: (LayoutInflater) -> VB

    lateinit var navController: NavController

    private lateinit var progressDialog: CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        setFullScreen()
        super.onCreate(savedInstanceState)

        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
        progressDialog = CustomProgressDialog(this, layoutInflater, false)
    }

    override fun onBackPressed() {
        hideKeyboard()
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    open fun setNavController() {
        navController = findNavController(navHostId)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(message: String) {
        val view = window.decorView.rootView
        val snackBarView = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val textView =
            snackBarView.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.maxLines = 5
        snackBarView.setDuration(5000).show()
    }

    fun toggleLoader(show: Boolean) {
        if (show) progressDialog.show()
        else progressDialog.dismiss()
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun setFullScreen() {
        if (isFullScreen) {
            WindowCompat.setDecorFitsSystemWindows(window, false)

            window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            window.statusBarColor = Color.TRANSPARENT

            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )

            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )

            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )

            supportActionBar?.hide()
        }
    }
}
