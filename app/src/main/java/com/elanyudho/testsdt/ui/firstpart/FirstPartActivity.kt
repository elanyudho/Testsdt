package com.elanyudho.testsdt.ui.firstpart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elanyudho.testsdt.R
import com.elanyudho.testsdt.databinding.ActivityFirstPartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstPartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstPartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstPartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnServiceOn.setOnClickListener {
            startService(Intent(this@FirstPartActivity, BackgroundService::class.java))
        }
    }
}