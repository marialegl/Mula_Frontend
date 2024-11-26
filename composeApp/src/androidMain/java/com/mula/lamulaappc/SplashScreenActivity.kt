package com.mula.lamulaappc

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mula.lamulaappc.client.MainActivityClient
import com.mula.lamulaappc.seller.MainActivitySeller


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        firebaseAuth = FirebaseAuth.getInstance()

        viewWelcome()
    }


    private fun viewWelcome() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                checkUserType()
            }

        }.start()

    }

    private fun checkUserType() {
        val firebaseUser= firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, SelectTypeActivity::class.java))
        }
        else {
            val reference = FirebaseDatabase.getInstance().getReference("Users")
            reference.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userType = snapshot.child("role").value

                        if (userType == "seller") {
                            startActivity(Intent(this@SplashScreenActivity, MainActivitySeller::class.java))
                            finishAffinity()
                        } else if (userType == "client") {
                            startActivity(Intent(this@SplashScreenActivity, MainActivityClient::class.java))
                            finishAffinity()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })

        }
    }
}