package com.test.app.presentation.fragments.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.test.app.databinding.FragmentProfileBinding
import com.test.app.presentation.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    private val viewModel: ProfileViewModel by viewModels()

    override fun bindData() {
        val textView: TextView = binding.textView
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }
}