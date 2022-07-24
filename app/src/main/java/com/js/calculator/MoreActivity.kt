package com.js.calculator

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        val btnBack: ImageButton = findViewById(R.id.more_bar_back)
        btnBack.setOnClickListener { this.finish() }
    }
}