package com.mula.lamulaappc.client.Bottom_Nav_Fragments_Client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mula.lamulaappc.R

class FragmentMyOrdersC : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ProductAdapter(getProducts())
    }

    private fun getProducts(): List<Product> {
        return listOf(
            Product("Brasil Mogiana - Nature", "23€/KG a partir de 1000g", listOf("nature", "brasil", "espresso", "chocolat"))
            // ... otros productos
        )
    }
}

data class Product(
    val name: String,
    val price: String,
    val tags: List<String>
)
