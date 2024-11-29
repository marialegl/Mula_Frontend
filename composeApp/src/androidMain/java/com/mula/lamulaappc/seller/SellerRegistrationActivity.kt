package com.mula.lamulaappc.seller

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mula.lamulaappc.Constant
import com.mula.lamulaappc.databinding.ActivitySellerRegistrationBinding
import com.google.firebase.database.FirebaseDatabase


class SellerRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellerRegistrationBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegisterS.setOnClickListener {
            validateData()
        }
    }

    private var siretNumber = ""
    private var fullName = ""
    private var email = ""
    private var address = ""
    private var phoneNumber = ""
    private var password = ""
    private var confirmPassword = ""

    private fun validateData() {
        siretNumber = binding.etSiretS.text.toString().trim()
        fullName = binding.etNameS.text.toString().trim()
        email = binding.etEmailS.text.toString().trim()
        address = binding.etAddressS.text.toString().trim()
        phoneNumber = binding.etPhoneS.text.toString().trim()
        password = binding.etPasswordS.text.toString().trim()
        confirmPassword = binding.etConfirmPasswordS.text.toString().trim()

        if (siretNumber.isEmpty()) {
            binding.etSiretS.error = "Enter SIRET number"
            binding.etSiretS.requestFocus()
        } else if (fullName.isEmpty()) {
            binding.etNameS.error = "Enter full name"
            binding.etNameS.requestFocus()
        } else if (email.isEmpty()) {
            binding.etEmailS.error = "Enter email"
            binding.etEmailS.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmailS.error = "Enter email correctly"
            binding.etEmailS.requestFocus()
        } else if (address.isEmpty()) {
            binding.etAddressS.error = "Enter Address"
            binding.etAddressS.requestFocus()
        } else if (phoneNumber.isEmpty()) {
            binding.etPhoneS.error = "Enter phone number"
            binding.etPhoneS.requestFocus()
        } else if (password.isEmpty()) {
            binding.etPasswordS.error = "Enter password"
            binding.etPasswordS.requestFocus()
        } else if (password.length < 6) {
            binding.etPasswordS.error = "It requires at least 6 characters"
            binding.etPasswordS.requestFocus()
        } else if (confirmPassword.isEmpty()) {
            binding.etConfirmPasswordS.error = "Confirm password"
            binding.etConfirmPasswordS.requestFocus()
        } else if (password != confirmPassword) {
            binding.etConfirmPasswordS.error = "Passwords do not match"
            binding.etConfirmPasswordS.requestFocus()
        } else {
            registerSeller()
        }
    }


    private fun registerSeller() {
        progressDialog.setMessage("Creating account")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                insertInfoToDB()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Registration failed due to ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun insertInfoToDB() {
        progressDialog.setMessage("Saving user info")

        val uidBD = firebaseAuth.uid
        val siretNumberBD = siretNumber
        val nameBD = fullName
        val emailBD = email
        val addressBD = address
        val phoneBD = phoneNumber
        val timestampBD = Constant().getDeviceTime()

        val infoSeller = HashMap<String, Any>()
        infoSeller["uid"] = "$uidBD"
        infoSeller["companyID"] = "$siretNumberBD"
        infoSeller["name"] = "$nameBD"
        infoSeller["email"] = "$emailBD"
        infoSeller["address"] = "$addressBD"
        infoSeller["phone"] = "$phoneBD"
        infoSeller["role"] = "seller"
        infoSeller["timestamp"] = "$timestampBD"

        val references = FirebaseDatabase.getInstance().getReference("Users")
        references.child(uidBD!!)
            .setValue(infoSeller)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivitySeller::class.java))
                finish()

            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Registration in BD failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }


    }
}
