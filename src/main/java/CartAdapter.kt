package com.example.amazon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amazon.databinding.ItemCartBinding

class CartAdapter(
    private val cartItems: MutableList<Product>,
    private val onItemRemoved: (Product) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productPrice.text = product.price
            binding.productQuantity.text = product.quantity.toString() // Set quantity
            Glide.with(binding.root.context).load(product.imageUrl).into(binding.productImage)

            // Increase quantity button logic
            binding.increaseQuantityButton.setOnClickListener {
                product.quantity++
                binding.productQuantity.text = product.quantity.toString() // Update displayed quantity
                updateTotalPrice() // Update total price
            }

            // Decrease quantity button logic
            binding.decreaseQuantityButton.setOnClickListener {
                if (product.quantity > 1) {
                    product.quantity--
                    binding.productQuantity.text = product.quantity.toString() // Update displayed quantity
                } else {
                    // If quantity is 1 and decrease is clicked, remove item
                    onItemRemoved(product) // Notify the activity to remove the item
                }
                updateTotalPrice() // Update total price
            }

            // Delete button logic
            binding.deleteButton.setOnClickListener {
                onItemRemoved(product) // Notify the activity to remove the item
            }
        }
    }

    private fun updateTotalPrice() {
        // You need to implement a method in your activity to handle the price update
    }
}