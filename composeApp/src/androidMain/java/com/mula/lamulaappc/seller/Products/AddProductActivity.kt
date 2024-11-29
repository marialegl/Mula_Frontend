package com.mula.lamulaappc.seller.Products

import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mula.lamulaappc.Adapters.AdapterSelectedImage
import com.mula.lamulaappc.Constant
import com.mula.lamulaappc.Models.CategoryModel
import com.mula.lamulaappc.Models.SelectedImageModel
import com.mula.lamulaappc.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private var imageUri: Uri? = null

    private lateinit var selectedImagesArrayList: ArrayList<SelectedImageModel>
    private lateinit var adapterSelectedImage: AdapterSelectedImage

    private lateinit var categoryArrayList: ArrayList<CategoryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uploadCategories()

        selectedImagesArrayList = ArrayList()

        binding.btnAddProduct.setOnClickListener {
            addProduct()
        }

        binding.imgAddProduct.setOnClickListener {
            selectImage()
        }

        binding.Category.setOnClickListener {
            selectCategories()
        }

        uploadImages()

    }

    private fun addProduct() {
        // Obtener valores de los campos
        val productName = binding.etNameP.text.toString().trim()
        val productDescription = binding.etDescriptionP.text.toString().trim()
        val productQuantity = binding.etQuantityP.text.toString().toIntOrNull()
        val productSize = binding.etPackageSizeP.text.toString().trim()
        val productPrice = binding.etPriceP.text.toString().toDoubleOrNull()
        val discountedPrice = binding.etDiscountedPriceP.text.toString().toDoubleOrNull()
        val discountNote = binding.etDiscountNoteP.text.toString().trim()

        // Validar los campos necesarios antes de guardar
        if (productName.isEmpty() || productQuantity == null || productPrice == null || idCat.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos requeridos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear una referencia a Firebase Database
        val ref = FirebaseDatabase.getInstance().getReference("Products")
        val productId = ref.push().key // Genera un ID único para el producto

        // Crear un mapa con los datos del producto
        val product = hashMapOf(
            "id" to productId,
            "name" to productName,
            "description" to productDescription,
            "quantity" to productQuantity,
            "size" to productSize,
            "price" to productPrice,
            "discountedPrice" to discountedPrice,
            "discountNote" to discountNote,
            "categoryId" to idCat,
            "categoryName" to titleCat
        )

        // Guardar los datos en Firebase Database
        ref.child(productId!!).setValue(product)
            .addOnSuccessListener {
                Toast.makeText(this, "Producto agregado exitosamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al agregar el producto: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun uploadCategories() {
        categoryArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Categories").orderByChild("category")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryArrayList.clear()
                for (ds in snapshot.children) {
                    val model = ds.getValue(CategoryModel::class.java)
                    categoryArrayList.add(model!!)
                }

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun uploadImages() {
        adapterSelectedImage = AdapterSelectedImage(this, selectedImagesArrayList)
        binding.RVImagesProduct.adapter = adapterSelectedImage
    }

    private fun selectImage() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                resulImage.launch(intent)
            }
    }

    private val resulImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                imageUri = data!!.data

                val time = "${Constant().getDeviceTime()}"

                val selectedImageModel = SelectedImageModel(time, imageUri, null, false)
                selectedImagesArrayList.add(selectedImageModel)
                uploadImages()

            } else {
                Toast.makeText(this, "Action cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private var idCat = ""
    private var titleCat = ""
    private fun selectCategories() {
        val categoriesArray = arrayOfNulls<String>(categoryArrayList.size)
        for (i in categoryArrayList.indices) {
            categoriesArray[i] = categoryArrayList[i].category
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Category")
            .setItems(categoriesArray) { dialog, which ->
                idCat = categoryArrayList[which].id
                titleCat = categoryArrayList[which].category
                binding.Category.text = titleCat
            }
            .show()
    }
}

