package com.example.amazon

object CartManager {
    private val _cartItems = mutableListOf<Product>() // Private backing property

    // Public read-only property to expose cart items
    val cartItems: List<Product>
        get() = _cartItems.toList() // Return an immutable copy

    // Method to add product to cart
    fun addToCart(product: Product) {
        _cartItems.add(product)
    }

    // Optional: Method to clear the cart
    fun clearCart() {
        _cartItems.clear()
    }

    // Optional: Method to remove product from cart
    fun removeFromCart(product: Product) {
        _cartItems.remove(product)
    }
}
