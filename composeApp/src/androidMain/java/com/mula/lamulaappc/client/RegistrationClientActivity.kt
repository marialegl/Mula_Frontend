package com.mula.lamulaappc.client

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mula.lamulaappc.Constant
import com.mula.lamulaappc.databinding.ActivityRegistrationClientBinding

class RegistrationClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationClientBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegisterC.setOnClickListener {
            validateData()
        }

    }
    private var id = ""
    private var name = ""
    private var email = ""
    private var phone = ""
    private var address = ""
    private var password = ""
    private var confirmPassword = ""
    private fun validateData() {
        id = binding.etIdC.text.toString().trim()
        name = binding.etNameC.text.toString().trim()
        email = binding.etEmailC.text.toString().trim()
        phone = binding.etPhoneC.text.toString().trim()
        address = binding.etAddressC.text.toString().trim()
        password = binding.etPasswordC.text.toString().trim()
        confirmPassword = binding.etConfirmPasswordC.text.toString().trim()

        if (id.isEmpty()) {
            binding.etIdC.error = "Enter your identification number"
            binding.etIdC.requestFocus()
        } else if (name.isEmpty()) {
            binding.etNameC.error = "Enter your full name"
            binding.etNameC.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmailC.error = "Invalid email"
            binding.etEmailC.requestFocus()
        } else if (phone.isEmpty()) {
            binding.etPhoneC.error = "Enter your phone number"
            binding.etPhoneC.requestFocus()
        } else if (address.isEmpty()) {
            binding.etAddressC.error = "Enter your address"
            binding.etAddressC.requestFocus()
        } else if (password.isEmpty()) {
            binding.etPasswordC.error = "Enter your password"
            binding.etPasswordC.requestFocus()
        } else if (password.length < 6) {
            binding.etPasswordC.error = "It must be at least 6 characters"
            binding.etPasswordC.requestFocus()
        } else if (confirmPassword.isEmpty()) {
            binding.etConfirmPasswordC.error = "Confirm your password"
            binding.etConfirmPasswordC.requestFocus()
        } else if (password != confirmPassword) {
            binding.etConfirmPasswordC.error = "Passwords do not match"
            binding.etConfirmPasswordC.requestFocus()
        } else {
            registerClient()
        }

    }

    private fun registerClient() {
        progressDialog.setMessage("Creating account")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                insertInfoBD()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Registration failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun insertInfoBD() {
        progressDialog.setMessage("Saving information")

        val uid = firebaseAuth.uid
        val idC = id
        val namesC = name
        val emailC = email
        val phoneC = phone
        val addressC = address
        val registrationTimeC = Constant().getDeviceTime()

        val clientData = HashMap<String, Any>()
        clientData["uid"] = "$uid"
        clientData["id"] = "$idC"
        clientData["names"] = "$namesC"
        clientData["email"] = "$emailC"
        clientData["phone"] = "$phoneC"
        clientData["address"] = "$addressC"
        clientData["image"] = ""
        clientData["role"] = "client"
        clientData["registrationTime"] = "$registrationTimeC"

        val reference = FirebaseDatabase.getInstance().getReference("Users")
        reference.child(uid!!)
            .setValue(clientData)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@RegistrationClientActivity, MainActivityClient::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Registration in BD failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }
}