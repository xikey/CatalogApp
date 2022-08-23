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
import com.zikey.android.razancatalogapp.databinding.RowProductMainGroupItemBinding
import com.zikey.android.razancatalogapp.model.ProductMainGroup


class ProductMainGroupAdapter(private val fragment: Fragment, private val onSelect: OnSelectItem) :
    ListAdapter<ProductMainGroup, RecyclerView.ViewHolder>(ItemsDiffCallback()) {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as ProductMainGroupViewHolder).bind(plant, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductMainGroupViewHolder(
            RowProductMainGroupItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), fragment, onSelect
        )
    }

    class ProductMainGroupViewHolder(
        private val binding: RowProductMainGroupItemBinding,
        private val fragment: Fragment,
        private val onSelectListener: OnSelectItem
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductMainGroup, position: Int) {
            binding.apply {

                FontChanger().applyMainFont(txtName)
                ImageViewWrapper(fragment.requireContext()).FromUrl(item.imageURL1).into(imgWallpaper).defaultImage(R.drawable.img_yadegar_loader).load()
                try {

                    txtName.setText(item.name)

                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }
    }

    public interface OnSelectItem{
        fun onSelect(item: ProductMainGroup)
    }

}

private class ItemsDiffCallback : DiffUtil.ItemCallback<ProductMainGroup>() {

    override fun areItemsTheSame(oldItem: ProductMainGroup, newItem: ProductMainGroup): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductMainGroup, newItem: ProductMainGroup): Boolean {
        return oldItem == newItem
    }
}