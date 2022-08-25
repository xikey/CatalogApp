package com.zikey.android.razancatalogapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.R
import com.zikey.android.razancatalogapp.core.ImageViewWrapper
import com.zikey.android.razancatalogapp.databinding.RowHomeProductBinding
import com.zikey.android.razancatalogapp.databinding.RowProductMainGroupItemBinding
import com.zikey.android.razancatalogapp.model.Product
import com.zikey.android.razancatalogapp.model.ProductMainGroup


class MainTopTenAdapter(private val fragment: Fragment, private val onSelect: OnSelectItem) :
    ListAdapter<Product, RecyclerView.ViewHolder>(ItemsDiffCallback()) {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as ProductViewHolder).bind(plant, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(
            RowHomeProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), fragment, onSelect
        )
    }

    class ProductViewHolder(
        private val binding: RowHomeProductBinding,
        private val fragment: Fragment,
        private val onSelectListener: OnSelectItem
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product, position: Int) {
            binding.apply {

                FontChanger().applyMainFont(txtName)
                ImageViewWrapper(fragment.requireContext()).FromUrl(item.imageUrl).into(imgWallpaper).defaultImage(R.drawable.img_yadegar_loader).load()
                try {

                    txtName.setText(item.name)

                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }
    }

    public interface OnSelectItem{
        fun onSelect(item: Product)
    }

    private class ItemsDiffCallback : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

}

