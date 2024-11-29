package com.mula.lamulaappc.seller

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mula.lamulaappc.databinding.ActivityLoginSellerBinding

class LoginSellerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSellerBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSellerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnLoginS.setOnClickListener {
            validateData()
        }

        binding.tvRegisterS.setOnClickListener {
            startActivity(Intent(applicationContext, SellerRegistrationActivity::class.java))

        }

    }
    private var email = ""
    private var password = ""

    private fun validateData() {
        email = binding.etEmailS.text.toString().trim()
        password = binding.etPasswordS.text.toString().trim()

        if(email.isEmpty()){
            binding.etEmailS.error = "Please enter your email"
            binding.etEmailS.requestFocus()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmailS.error = "Invalid email pattern"
            binding.etEmailS.requestFocus()
        }
        else if (password.isEmpty()){
            binding.etPasswordS.error = "Please enter your password"
            binding.etPasswordS.requestFocus()
        }
        else {
            loginSeller()
        }

    }

    private fun loginSeller() {
        progressDialog.setMessage("Entering")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivitySeller::class.java))
                finishAffinity()
                Toast.makeText(
                    this,
                    "Welcome",
                    Toast.LENGTH_SHORT
                    ).show()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Login failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                }
    }
}