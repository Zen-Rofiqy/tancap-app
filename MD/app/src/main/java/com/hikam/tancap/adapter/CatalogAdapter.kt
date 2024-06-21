package com.hikam.tancap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hikam.tancap.R
import com.hikam.tancap.data.response.Catalog
import com.hikam.tancap.databinding.ItemCatalogBinding

class CatalogAdapter : ListAdapter<Catalog, CatalogAdapter.CatalogViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val binding = ItemCatalogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val catalog = getItem(position)
        holder.bind(catalog)
    }

    class CatalogViewHolder(private val binding: ItemCatalogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(catalog: Catalog) {
            binding.tvNamesign.text = catalog.description
            Glide.with(binding.root.context)
                .load(catalog.image)
                .placeholder(R.color.hijau)
                .error(R.drawable.image_placeholder)
                .into(binding.ivItem)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Catalog>() {
        override fun areItemsTheSame(oldItem: Catalog, newItem: Catalog): Boolean {
            return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: Catalog, newItem: Catalog): Boolean {
            return oldItem == newItem
        }
    }
}
