package com.test.app.presentation.fragments.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.app.data.dummy.Products
import com.test.app.data.models.Product
import com.test.app.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    private val _products = MutableStateFlow<Resource<ArrayList<Product>>>(Resource.notRequested())
    val products: StateFlow<Resource<ArrayList<Product>>> = _products

    var count = 0

    fun getData() {
        viewModelScope.launch {
            _products.value = Resource.loading()

            // Creating data
            val list = Products.getRandomList()

            // Simulate datasource request delay
            delay(2000)

            _products.value = Resource.success(list)
        }
    }
}