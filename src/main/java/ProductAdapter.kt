package com.example.amazon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(
    private var productList: List<Product>,  // Changed to var for updating
    private val onProductClick: (Product) -> Unit  // Function to handle clicks
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.name
        holder.productPrice.text = product.price

        // Load the product image using Glide
        Glide.with(holder.itemView.context)
            .load(product.imageUrl)  // URL of the product image
            .placeholder(R.drawable.sample_product) // Placeholder image
            .into(holder.productImage)

        // Handle click event
        holder.itemView.setOnClickListener {
            onProductClick(product)  // Pass the clicked product to the listener
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    // New method to update the list of products
    fun updateProducts(newProductList: List<Product>) {
        productList = newProductList // Update the product list
        notifyDataSetChanged() // Notify the adapter to refresh the displayed items
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
    }
}