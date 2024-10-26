package com.example.amazon

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amazon.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    // Sample product list, replace it with your actual product list
    private val productList = listOf(
        Product("Product 1", "$19.99", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfri7esL5ir1SGRzYx1t7O32_4SEfUGyRfLQ&s"),
        Product("Product 2", "$29.99", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRW6EO0Nejxf7qn0zIhOWuhTEhbxZpwPhpXhA&s"),
        Product("Product 3", "$39.99", "https://example.com/image3.jpg"),
        Product("Product 4", "$22.50", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4QaRqKWxfrGdQ9r5U5mWg-RWItNxzmphX-Q&s"),
        Product("Product 5", "$32.51", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPipmiOkIHVxOBWEbYlFxZHjJR6bnV2hllYA&s"),
        Product("Product 6", "$40.99", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0chA4GKOK51N_ACiqt--_eLcIzAEUbn97EQ&s"),
    )

    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        binding.searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductAdapter(emptyList()) { product ->
            // Handle product click
            val intent = Intent(this, ProductDetailsActivity::class.java).apply {
                putExtra("productName", product.name)
                putExtra("productPrice", product.price)
                putExtra("productImage", product.imageUrl)
            }
            startActivity(intent)
        }
        binding.searchResultsRecyclerView.adapter = productAdapter

        // Set up search button click listener
        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                // Filter the product list based on the search query
                val filteredProducts = productList.filter { product ->
                    product.name.contains(query, ignoreCase = true) // Case-insensitive search
                }
                // Update the RecyclerView with the filtered products
                productAdapter.updateProducts(filteredProducts)
                binding.searchResultsRecyclerView.visibility = if (filteredProducts.isNotEmpty()) View.VISIBLE else View.GONE
            } else {
                // Show all products if the query is empty
                productAdapter.updateProducts(productList)
                binding.searchResultsRecyclerView.visibility = View.GONE
            }
        }
    }
}