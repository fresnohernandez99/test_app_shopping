package com.test.app.presentation.fragments.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.test.app.databinding.FragmentProductsBinding
import com.test.app.presentation.base.BaseFragment
import com.test.app.utils.RecyclerViewClickListener
import com.test.app.utils.Status
import com.test.app.utils.imageLoader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>(), RecyclerViewClickListener {

    @Inject
    lateinit var imageLoader: ImageLoader

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductsBinding
        get() = FragmentProductsBinding::inflate

    private val viewModel: ProductsViewModel by viewModels()

    private lateinit var adapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observingData()
    }

    override fun bindData() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.apply {

            recycler.layoutManager = layoutManager

            adapter =
                ProductsAdapter(imageLoader, this@ProductsFragment, ArrayList())

            binding.recycler.adapter = adapter

            idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    viewModel.count++
                    // on below line we are making our progress bar visible.
                    loading.visibility = View.VISIBLE
                    if (viewModel.count < 20) {
                        // on below line we are again calling
                        // a method to load data in our array list.
                        viewModel.getData()
                    }
                }
            })
        }

        viewModel.getData()
    }

    private fun observingData() {
        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.products.collect { resource ->
                    when (resource.status) {
                        Status.NOT_REQUESTED -> {
                        }
                        Status.LOADING -> {
                            binding.loading.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            adapter.add(resource.data!!)
                            binding.loading.visibility = View.GONE
                        }
                        else -> {
                            showMessage("Error getting data")
                            binding.loading.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    override fun recyclerViewListClicked(V: View, pos: Int, id: Int?, extraData: Any?) {
    }
}