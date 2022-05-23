package com.test.app.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.test.app.utils.imageLoader.ImageLoader
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavController()
        bindData()
    }

    override fun onPause() {
        super.onPause()
        hideKeyBoard()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected abstract fun bindData()

    open fun setNavController() {
        navController = NavHostFragment.findNavController(this)
    }

    protected fun showLoader() {
        (activity as? BaseActivity<*>)?.toggleLoader(true)
    }

    protected fun hideLoader() {
        (activity as? BaseActivity<*>)?.toggleLoader(false)
    }

    protected fun navigateBack() {
        requireActivity().onBackPressed()
    }

    fun showToast(message: String) {
        (activity as? BaseActivity<*>)?.showToast(message)
    }

    private fun hideKeyBoard() {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).hideKeyboard()
        }
    }

    fun showKeyBoard() {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).showKeyboard()
        }
    }

    protected fun showMessage(message: String) {
        binding.let {
            val snackBarView = Snackbar.make(it.root, message, Snackbar.LENGTH_SHORT)
            val textView =
                snackBarView.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.maxLines = 5
            snackBarView.setDuration(3000).show()
        }
    }

}
