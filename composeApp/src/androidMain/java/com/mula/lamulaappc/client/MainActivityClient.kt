package com.mul<.lamulaappc.client

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mula.lamulaappc.R
import com.mula.lamulaappc.SelectTypeActivity
import com.mula.lamulaappc.client.Bottom_Nav_Fragments_Client.FragmentMyOrdersC
import com.mula.lamulaappc.client.Bottom_Nav_Fragments_Client.FragmentStoreC
import com.mula.lamulaappc.client.Nav_Fragments_Client.FragmentHomeC
import com.mula.lamulaappc.client.Nav_Fragments_Client.FragmentMyProfileC
import com.mula.lamulaappc.databinding.ActivityMainClientBinding


class MainActivityClient : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainClientBinding
    private var firebaseAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()
        checkSession()

        binding.navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(FragmentHomeC())

    }

    private fun checkSession() {
        if (firebaseAuth!!.currentUser == null) {
            startActivity(Intent(this@MainActivityClient, SelectTypeActivity::class.java))
            finishAffinity()
        }else {
            Toast.makeText(this, "Online client", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logout() {
        firebaseAuth!!.signOut()
        startActivity(Intent(this@MainActivityClient, SelectTypeActivity::class.java))
        finishAffinity()
        Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.op_home_c ->{
                replaceFragment(FragmentHomeC())
            }
            R.id.op_my_profile_c ->{
                replaceFragment(FragmentMyProfileC())
            }
            R.id.op_logout_c ->{
                logout()
            }
            R.id.op_store_c ->{
                replaceFragment(FragmentStoreC())
            }
            R.id.op_my_orders_c -> {
                replaceFragment(FragmentMyOrdersC())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}