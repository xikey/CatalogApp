package com.zikey.android.razancatalogapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.R
import com.zikey.android.razancatalogapp.core.Convertor
import com.zikey.android.razancatalogapp.core.ImageViewWrapper
import com.zikey.android.razancatalogapp.core.ScreenSize
import com.zikey.android.razancatalogapp.databinding.RowProductSubGroupItemBinding
import com.zikey.android.razancatalogapp.model.ProductMainGroup
import com.zikey.android.razancatalogapp.model.ProductSubGroup


class ProductSubGroupAdapter(private val fragment: Fragment, private val onSelect: OnSelectItem) :
    ListAdapter<ProductSubGroup, RecyclerView.ViewHolder>(ItemsDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as ProductSubGroupViewHolder).bind(plant, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductSubGroupViewHolder(
            RowProductSubGroupItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), fragment, onSelect
        )
    }

    class ProductSubGroupViewHolder(
        private val binding: RowProductSubGroupItemBinding,
        private val fragment: Fragment,
        private val onSelectListener: OnSelectItem
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductSubGroup, position: Int) {
            binding.apply {

                val box: View = binding.crdContent
                val width = ScreenSize.width
                val height = (width / 3) * 2
                val params = box.layoutParams
                params.height = height

                box.layoutParams = params


                var boxWidth = width / 2
                boxWidth -= Convertor.toPixcel(60f, fragment.requireContext())
                val imageBox: View = binding.imgWallpaper
                val imageParams = imageBox.layoutParams
                imageParams.height = boxWidth
                imageParams.width = boxWidth

                imageBox.layoutParams = imageParams


                FontChanger().applyTitleFont(txtName)
                FontChanger().applyNumberFont(txtProductCount)
                ImageViewWrapper(fragment.requireContext()).FromUrl(item.imageURL1)
                    .into(imgWallpaper).defaultImage(R.drawable.img_yadegar_loader).loadCenterCrop()
                try {

                    txtName.setText(item.name)


                    crdContent.setOnClickListener {
                        onSelectListener.onSelect(item)
                    }

                    txtProductCount.setText("${item.productsCount} کالا ")

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    public interface OnSelectItem {
        fun onSelect(item: ProductSubGroup)
    }

    private class ItemsDiffCallback : DiffUtil.ItemCallback<ProductSubGroup>() {

        override fun areItemsTheSame(oldItem: ProductSubGroup, newItem: ProductSubGroup): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductSubGroup,
            newItem: ProductSubGroup
        ): Boolean {
            return oldItem == newItem
        }
    }

}

