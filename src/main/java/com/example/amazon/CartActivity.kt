package com.example.amazon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amazon.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private val cartItems = CartManager.cartItems.toMutableList() // Make a mutable copy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)

        // Create adapter and set it to RecyclerView
        cartAdapter = CartAdapter(cartItems) { product ->
            removeItem(product)
        }
        binding.cartRecyclerView.adapter = cartAdapter

        // Calculate and display total price initially
        updateTotalPrice()

        // Handle checkout button click
        binding.checkoutButton.setOnClickListener {
            // Handle payment logic here
        }
    }

    private fun removeItem(product: Product) {
        cartItems.remove(product) // Remove the item from the cart
        cartAdapter.notifyDataSetChanged() // Notify adapter of the change
        updateTotalPrice() // Update the total price after removal
    }

    private fun updateTotalPrice() {
        // Calculate the total price based on item quantities
        val totalPrice = cartItems.sumOf {
            // Remove currency symbol and parse to Double
            it.price.replace("€", "").replace("$", "").toDouble() * it.quantity
        }
        // Update the TextView with the formatted total price
        binding.totalPriceTextView.text = "Subtotal (${cartItems.sumOf { it.quantity }} items): €$totalPrice"
    }
}