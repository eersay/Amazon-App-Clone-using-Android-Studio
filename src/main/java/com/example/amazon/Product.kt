package com.example.amazon

data class Product(
    val name: String,
    val price: String,
    val imageUrl: String,
    var quantity: Int = 1 // Default to 1 when the product is added to the cart
)