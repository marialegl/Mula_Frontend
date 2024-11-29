package com.mula.lamulaappc.client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mula.lamulaappc.databinding.ActivityLoginClientBinding

class LoginClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegisterC.setOnClickListener {
            startActivity(Intent(this@LoginClientActivity, RegistrationClientActivity::class.java))
        }

    }
}