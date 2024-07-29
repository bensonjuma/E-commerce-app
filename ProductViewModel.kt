package com.ecom.ecommerceapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<List<ProductEntity>?>()
    val products: MutableLiveData<List<ProductEntity>?> get() = _products

    init {
        fetchProducts()
    }

    private fun fetchProducts() = viewModelScope.launch {
        val productsList = repository.getAllProducts()
        _products.value = productsList
        Log.d("ProductViewModel", "Fetched products: $productsList")
    }
}

