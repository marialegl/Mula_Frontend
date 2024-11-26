package com.mula.lamulaappc.seller

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
import com.mula.lamulaappc.databinding.ActivityMainSellerBinding
import com.mula.lamulaappc.seller.Bottom_Nav_Fragments_Seller.FragmentMyProductsS
import com.mula.lamulaappc.seller.Bottom_Nav_Fragments_Seller.FragmentOrdersS
import com.mula.lamulaappc.seller.Nav_Fragments_Seller.FragmentHomeS
import com.mula.lamulaappc.seller.Nav_Fragments_Seller.FragmentMyStoreS
import com.mula.lamulaappc.seller.Nav_Fragments_Seller.FragmentReviewS
import com.mula.lamulaappc.seller.Nav_Fragments_Seller.FragmentSellerCategories

class MainActivitySeller : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainSellerBinding
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainSellerBinding.inflate(layoutInflater)
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

        replaceFragment(FragmentHomeS())
        binding.navigationView.setCheckedItem(R.id.op_home_s)
    }

    private fun logout() {
        firebaseAuth!!.signOut()
        startActivity(Intent(applicationContext, SelectTypeActivity::class.java))
        finish()
        Toast.makeText(applicationContext, "You left the application", Toast.LENGTH_SHORT).show()
    }

    private fun checkSession() {
        /*If the user is not logged in*/
        if (firebaseAuth!!.currentUser==null){
            startActivity(Intent(applicationContext, SelectTypeActivity::class.java))
            Toast.makeText(applicationContext, "Unregistered or unlogged seller", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Online seller", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.op_home_s -> {
                replaceFragment(FragmentHomeS())
            }

            R.id.op_my_store_s -> {
                replaceFragment(FragmentMyStoreS())
            }
            R.id.op_categories_s -> {
                replaceFragment(FragmentSellerCategories())
            }
            R.id.op_review_s -> {
                replaceFragment(FragmentReviewS())
            }

            R.id.op_logout_s -> {
                logout()
            }

            R.id.op_my_products_s -> {
                replaceFragment(FragmentMyProductsS())
            }

            R.id.op_my_orders_s -> {
                replaceFragment(FragmentOrdersS())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}