package com.mula.lamulaappc.seller.Nav_Fragments_Seller

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.mula.lamulaappc.databinding.FragmentSellerCategoriesBinding


class FragmentSellerCategories : Fragment() {

    private lateinit var binding : FragmentSellerCategoriesBinding
    private lateinit var mContext : Context
    private lateinit var progressDialog: ProgressDialog

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSellerCategoriesBinding.inflate(inflater, container, false)

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnAddCategory.setOnClickListener {
            validateData()
        }

        return binding.root
    }
    private var category = ""
    private fun validateData() {
        category = binding.etCategory.text.toString().trim()
        if (category.isEmpty()){
            Toast.makeText(context, "Enter category", Toast.LENGTH_SHORT).show()
        }else{
            addCategoryDB()
        }

    }

    private fun addCategoryDB() {
        progressDialog.setMessage("Adding category")
        progressDialog.show()

        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        val keyId = ref.push().key

        val hasMap = HashMap<String, Any>()
        hasMap["id"] = "${keyId}"
        hasMap["category"] = "${category}"

        ref.child(keyId!!)
            .setValue(hasMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context, "Category added successfully", Toast.LENGTH_SHORT).show()
                binding.etCategory.setText("")

            }
            .addOnFailureListener { e ->
            progressDialog.dismiss()
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}