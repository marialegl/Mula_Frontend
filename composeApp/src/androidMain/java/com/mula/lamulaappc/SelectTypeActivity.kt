package com.mula.lamulaappc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mula.lamulaappc.client.LoginClientActivity
import com.mula.lamulaappc.databinding.ActivitySelectTypeBinding
import com.mula.lamulaappc.seller.LoginSellerActivity

class SelectTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SellerType.setOnClickListener {
            startActivity(Intent(this@SelectTypeActivity, LoginSellerActivity::class.java))
        }

        binding.ClientType.setOnClickListener {
            startActivity(Intent(this@SelectTypeActivity, LoginClientActivity::class.java))
        }


    }
}