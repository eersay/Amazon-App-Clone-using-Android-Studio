package com.example.amazon

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val productList = listOf(
        Product("Product 1", "$19.99", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfri7esL5ir1SGRzYx1t7O32_4SEfUGyRfLQ&s"),
        Product("Product 2", "$29.99", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRW6EO0Nejxf7qn0zIhOWuhTEhbxZpwPhpXhA&s"),
        Product("Product 3", "$39.99", "https://example.com/image3.jpg"),
        Product("Product 4", "$22.50", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4QaRqKWxfrGdQ9r5U5mWg-RWItNxzmphX-Q&s"),
        Product("Product 5", "$32.51", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPipmiOkIHVxOBWEbYlFxZHjJR6bnV2hllYA&s"),
        Product("Product 6", "$40.99", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0chA4GKOK51N_ACiqt--_eLcIzAEUbn97EQ&s"),
    )

    private val relatedProductList = listOf(
        Product("Related Product 1", "$9.99", "https://www.tcl.com/usca/content/dam/tcl/products/mobile/40XL_TMT_Special.png"),
        Product("Related Product 2", "$14.99", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz1B4tgiCcBOd7lDTE_n3iXU4l-Ju4_F6wiQ&s"),
        Product("Related Product 3", "$24.99", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIv4RL_1bKjf8dt0C-kHzeTs1Lvuvpf1JZOA&s")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup RecyclerView for related items (horizontal)
        val relatedRecyclerView = findViewById<RecyclerView>(R.id.relatedItemsRecyclerView)
        relatedRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val relatedProductAdapter = RelatedProductAdapter(relatedProductList)
        relatedRecyclerView.adapter = relatedProductAdapter

        // Setup RecyclerView for main product list (Grid layout with 2 columns)
        val productRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        productRecyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns

        // Define the behavior for product click
        val productAdapter = ProductAdapter(productList) { product ->
            // Handle product click - start ProductDetailsActivity, passing the product details
            val intent = Intent(this, ProductDetailsActivity::class.java).apply {
                putExtra("productName", product.name)
                putExtra("productPrice", product.price)
                putExtra("productImage", product.imageUrl)
            }
            startActivity(intent)
        }

        productRecyclerView.adapter = productAdapter

        // Set up Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> true
                R.id.search -> {
                    // Start SearchActivity
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        val addToCartButton: ImageView = findViewById(R.id.cartIcon)
        addToCartButton.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}
