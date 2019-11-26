package com.vhcastro.coderswag.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vhcastro.coderswag.R
import com.vhcastro.coderswag.utilities.EXTRA_CATEGORY

class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        
        val categoryType = intent.getStringExtra(EXTRA_CATEGORY)
        println(categoryType)
    }
}