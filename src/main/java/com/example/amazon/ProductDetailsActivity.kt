package com.example.amazon

import CommentsAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.amazon.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var similarProductsAdapter: ProductAdapter // Adapter for similar products

    private val commentList = mutableListOf<Comment>() // Mutable list to allow adding new comments
    private val similarProductsList = listOf(
        Product("Similar Product 1", "$19.99", "https://www.realsimple.com/thmb/rJXoXbyd0bGizee7QkBHfOBDrug=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Best-Affordable-Skincare-Brands-RS-tout-4065f1f4988347a99113306d14453f1b.jpg"),
        Product("Similar Product 2", "$24.99", "https://haanready.com/cdn/shop/products/01_10.png?v=1675873019"),
        Product("Similar Product 3", "$29.99", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEH0TAxRZTjzh5Kz-i4_d6ESQfLcqgn9uXjw&s")
    ) // List of similar products (can be dynamic)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive product data from the intent
        val productName = intent.getStringExtra("productName")
        val productPrice = intent.getStringExtra("productPrice")
        val productImage = intent.getStringExtra("productImage")

        // Set data in views using binding
        binding.productNameTextView.text = productName
        binding.productPriceTextView.text = productPrice

        // Load product image using Glide
        Glide.with(this)
            .load(productImage)
            .into(binding.productImageView)

        // Handle Add to Cart button click
        binding.addToCartButton.setOnClickListener {
            val product = Product(productName ?: "", productPrice ?: "", productImage ?: "")
            CartManager.addToCart(product)
            Toast.makeText(this, "$productName added to cart!", Toast.LENGTH_SHORT).show()
        }

        // Setup the comments section (RecyclerView)
        setupCommentsSection()

        // Setup the similar products section (RecyclerView)
        setupSimilarProductsSection()

        // Handle posting a new comment
        binding.postCommentButton.setOnClickListener {
            val newCommentText = binding.commentInput.text.toString()
            if (newCommentText.isNotEmpty()) {
                postNewComment(newCommentText)
            } else {
                Toast.makeText(this, "Please write a comment first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupCommentsSection() {
        // Dummy comments
        commentList.addAll(
            listOf(
                Comment("John Doe", "Great product, highly recommend!", "Reviewed on Oct 20, 2024"),
                Comment("Jane Smith", "Good quality, but took too long to arrive.", "Reviewed on Oct 18, 2024"),
                Comment("Emily Johnson", "Excellent value for money.", "Reviewed on Oct 15, 2024")
            )
        )

        // Set up the comments RecyclerView
        binding.commentsRecyclerView.layoutManager = LinearLayoutManager(this)
        commentsAdapter = CommentsAdapter(commentList)
        binding.commentsRecyclerView.adapter = commentsAdapter
    }

    private fun setupSimilarProductsSection() {
        // Set up the similar products RecyclerView
        binding.relatedItemsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Initialize the adapter for similar products
        similarProductsAdapter = ProductAdapter(similarProductsList) { product ->
            // Handle similar product click, if needed
            val intent = Intent(this, ProductDetailsActivity::class.java).apply {
                putExtra("productName", product.name)
                putExtra("productPrice", product.price)
                putExtra("productImage", product.imageUrl)
            }
            startActivity(intent)
        }

        // Attach the adapter to the RecyclerView
        binding.relatedItemsRecyclerView.adapter = similarProductsAdapter
    }

    private fun postNewComment(commentText: String) {
        val newComment = Comment(
            "You", // Username could be dynamically fetched from user profile
            commentText,
            "Just now" // Timestamp could be more dynamic (current date/time)
        )

        // Add the new comment to the list and notify the adapter
        commentList.add(newComment)
        commentsAdapter.notifyItemInserted(commentList.size - 1)

        // Clear the comment input field
        binding.commentInput.text.clear()

        // Optionally show a toast message
        Toast.makeText(this, "Comment posted!", Toast.LENGTH_SHORT).show()
    }
}