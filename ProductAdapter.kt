package com.ecom.ecommerceapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(private val products: List<ProductEntity>?) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products?.get(position)
        if (product != null) {
            holder.bind(product)
        }
    }

    override fun getItemCount(): Int {
        return products?.size ?: 0
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewProductName)
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewProduct)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                val product = products?.get(position)
                product?.let {
                    val context = itemView.context
                    val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                        putExtra("product", it) // Pass product data to the activity
                    }
                    context.startActivity(intent)
                }
            }
        }

        fun bind(product: ProductEntity) {
            nameTextView.text = product.name
            Glide.with(itemView.context).load(product.imageUrl).into(imageView)
        }
    }
}
