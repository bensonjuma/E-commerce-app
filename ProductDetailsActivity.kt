package com.ecom.ecommerceapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val imageViewProduct: ImageView = findViewById(R.id.imageViewProduct)
        val textViewProductName: TextView = findViewById(R.id.textViewProductName)
        val textViewProductDescription: TextView = findViewById(R.id.textViewProductDescription)
        val textViewProductPrice: TextView = findViewById(R.id.textViewProductPrice)
        val textViewProductCategory: TextView = findViewById(R.id.textViewProductCategory)

        // Get the product details from the Intent
        val product = intent.getParcelableExtra<ProductEntity>("product")

        // Display product details
        product?.let {
            textViewProductName.text = it.name
            textViewProductDescription.text = it.description
            textViewProductPrice.text = "$${it.price}" // Format price as needed
            textViewProductCategory.text = it.category
            Glide.with(this).load(it.imageUrl).into(imageViewProduct)
        }
    }
}
