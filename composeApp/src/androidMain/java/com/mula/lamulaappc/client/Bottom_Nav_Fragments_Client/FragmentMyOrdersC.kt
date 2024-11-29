package com.mula.lamulaappc.client.Bottom_Nav_Fragments_Client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mula.lamulaappc.R

class FragmentMyOrdersC : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProductAdapter(getProducts())
    }

    private fun getProducts(): List<Product> {
        // Aquí se cargarían los datos de los productos desde una fuente de datos
        return listOf(
            Product("Brasil Mogiana - Nature", "23€/KG a partir de 1000g", listOf("nature", "brasil", "espresso", "chocolat")),
            // ... otros productos
        )
    }
}