package com.mula.lamulaappc.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.mula.lamulaappc.Models.SelectedImageModel
import com.mula.lamulaappc.R
import androidx.databinding.DataBindingUtil
import com.mula.lamulaappc.databinding.ItemSelectedImagesBinding

class AdapterSelectedImage (
    private val context: Context,
    private val imageSelecArrayList: ArrayList<SelectedImageModel>
): Adapter<AdapterSelectedImage.HolderImageSelected>() {
    private lateinit var binding: ItemSelectedImagesBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderImageSelected {
        binding = ItemSelectedImagesBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderImageSelected(binding.root)
    }

    override fun getItemCount(): Int {
        return imageSelecArrayList.size
    }

    override fun onBindViewHolder(holder: HolderImageSelected, position: Int) {
        val model = imageSelecArrayList[position]
        val imageUri = model.imageUri

        //Reading Image-->
        try {
            Glide.with(context)
                .load(imageUri)
                .placeholder(R.drawable.image)
                .into(holder.imageItem)
        }catch (e: Exception){

        }

        //Delete Image-->
        holder.btn_delete.setOnClickListener {
            imageSelecArrayList.removeAt(position)
            notifyDataSetChanged()
        }

    }

    inner class HolderImageSelected(itemView : View) : ViewHolder(itemView){
        var imageItem = binding.imageItem
        var btn_delete = binding.deleteItem

    }

}