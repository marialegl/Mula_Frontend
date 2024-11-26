package com.mula.lamulaappc.client.Nav_Fragments_Client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mula.lamulaappc.R
import com.mula.lamulaappc.client.Bottom_Nav_Fragments_Client.FragmentMyOrdersC
import com.mula.lamulaappc.client.Bottom_Nav_Fragments_Client.FragmentStoreC
import com.mula.lamulaappc.databinding.FragmentHomeCBinding

class FragmentHomeC : Fragment() {

    private lateinit var binding: FragmentHomeCBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeCBinding.inflate(inflater, container, false)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.op_store_c -> {
                    replaceFragment(FragmentStoreC())
                }

                R.id.op_my_orders_c -> {
                    replaceFragment(FragmentMyOrdersC())
                }
            }
            true
        }

        replaceFragment(FragmentStoreC())
        binding.bottomNavigation.selectedItemId = R.id.op_store_c

        return binding.root

    }
    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.bottomFragment, fragment)
            .commit()
        }
}