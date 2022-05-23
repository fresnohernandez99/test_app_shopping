package com.test.app.presentation.fragments.configs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.test.app.databinding.FragmentConfigsBinding
import com.test.app.presentation.base.BaseFragment

class ConfigsFragment : BaseFragment<FragmentConfigsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentConfigsBinding
        get() = FragmentConfigsBinding::inflate

    private val viewModel: ConfigsViewModel by viewModels()

    override fun bindData() {

    }
}