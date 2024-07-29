package com.ecom.ecommerceapp

class ProductRepository(private val productDao: ProductDao) {

    suspend fun insertProduct(product: ProductEntity) {
        productDao.insertProduct(product)
    }

    suspend fun getAllProducts(): List<ProductEntity> {
        return productDao.getAllProducts()
    }
}
