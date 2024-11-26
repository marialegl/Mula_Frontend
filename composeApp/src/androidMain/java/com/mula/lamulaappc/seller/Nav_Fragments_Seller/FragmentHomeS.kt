package com.mula.lamulaappc.seller.Nav_Fragments_Seller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mula.lamulaappc.R
import com.mula.lamulaappc.databinding.FragmentHomeSBinding
import com.mula.lamulaappc.seller.Bottom_Nav_Fragments_Seller.FragmentMyProductsS
import com.mula.lamulaappc.seller.Bottom_Nav_Fragments_Seller.FragmentOrdersS
import com.mula.lamulaappc.seller.Products.AddProductActivity


class FragmentHomeS : Fragment() {

    private lateinit var binding : FragmentHomeSBinding
    private lateinit var mContext : Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentHomeSBinding.inflate(inflater, container, false)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.op_my_products_s -> {
                    replaceFragment(FragmentMyProductsS())
                }
                R.id.op_my_orders_s -> {
                    replaceFragment(FragmentOrdersS())
                }
            }
            true
        }

        replaceFragment(FragmentMyProductsS())
        binding.bottomNavigation.selectedItemId = R.id.op_my_products_s

        binding.addFab.setOnClickListener {
            startActivity(Intent(context, AddProductActivity::class.java))
        }

        return binding.root

    }

    private fun replaceFragment(fragment: Fragment){
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.bottomFragment, fragment)
            .commit()
    }

}