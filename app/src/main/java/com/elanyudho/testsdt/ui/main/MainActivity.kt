package com.elanyudho.testsdt.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elanyudho.testsdt.databinding.ActivityMainBinding
import com.elanyudho.testsdt.ui.firstpart.FirstPartActivity
import com.elanyudho.testsdt.ui.secondpart.SecondPartActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPart1.setOnClickListener {
            startActivity(Intent(this@MainActivity, FirstPartActivity::class.java))
        }

        binding.btnPart2.setOnClickListener {
            startActivity(Intent(this@MainActivity, SecondPartActivity::class.java))
        }

    }

}