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
import com.zikey.android.razancatalogapp.databinding.RowCatalogImageItemBinding
import com.zikey.android.razancatalogapp.databinding.RowProductMainGroupItemBinding
import com.zikey.android.razancatalogapp.databinding.RowProductSubGroupItemBinding
import com.zikey.android.razancatalogapp.databinding.RowProductsItemBinding
import com.zikey.android.razancatalogapp.model.Product
import com.zikey.android.razancatalogapp.model.ProductMainGroup
import com.zikey.android.razancatalogapp.model.ProductSubGroup


class CatalogAdapter(private val fragment: Fragment, private val onSelect: OnSelectItem) :
    ListAdapter<Product, RecyclerView.ViewHolder>(ItemsDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as ProductsViewHolder).bind(plant, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductsViewHolder(
            RowCatalogImageItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), fragment, onSelect
        )
    }

    class ProductsViewHolder(
        private val binding: RowCatalogImageItemBinding,
        private val fragment: Fragment,
        private val onSelectListener: OnSelectItem
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product, position: Int) {

            binding.apply {

             ImageViewWrapper(fragment.requireContext()).FromUrl(item.imageUrl)
                 .into(pic).defaultImage(R.drawable.img_yadegar_loader).load()

                FontChanger().applyTitleFont(txtName)
                FontChanger().applyTitleFont(txtMainGroup)

                txtName.setText(item.name)
                txtMainGroup.setText(item.mainGroup+" -> "+item.subGroup)

            }
        }
    }

    public interface OnSelectItem {
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

