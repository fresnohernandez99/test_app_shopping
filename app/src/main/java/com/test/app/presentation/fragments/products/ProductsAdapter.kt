package com.test.app.presentation.fragments.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.app.R
import com.test.app.data.models.Product
import com.test.app.databinding.ItemProductBinding
import com.test.app.utils.RecyclerViewClickListener
import com.test.app.utils.imageLoader.ImageLoader

class ProductsAdapter(
    val imageLoader: ImageLoader,
    var clickListener: RecyclerViewClickListener,
    val items: ArrayList<Product>
) :
    RecyclerView.Adapter<ProductsAdapter.InnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding =
            DataBindingUtil.inflate<ItemProductBinding>(
                inflater,
                R.layout.item_product,
                parent,
                false
            )

        return InnerViewHolder(binding).apply {
            binding.root.setOnClickListener {
                clickListener.recyclerViewListClicked(it, absoluteAdapterPosition, 0, null)
            }
        }
    }

    fun add(newItems: ArrayList<Product>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.binding.apply {
            val actualItem = items[position]
            name = actualItem.productName
            imageLoader.loadDrawable(actualItem.productImg, imageView, true)

            executePendingBindings()
        }
    }

    override fun getItemCount() = items.size

    class InnerViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)
}