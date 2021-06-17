package com.victorbrandalise.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victorbrandalise.databinding.LayoutItemDetailBinding
import com.victorbrandalise.model.Item

class ItemAdapter(
    items: List<Item>,
    private val onClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {

    private var items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = LayoutItemDetailBinding.inflate(inflater, parent, false)
        val onClick: (Int) -> Unit = { position -> onClick(items[position]) }

        return ItemViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<Item>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}

class ItemViewHolder(
    private val binding: LayoutItemDetailBinding,
    private val onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { onClick(adapterPosition) }
    }

    fun bind(item: Item) = with(binding) {
        name.text = item.name
        description.text = item.description

        Glide.with(root.context)
            .load(item.image)
            .circleCrop()
            .into(icon)
    }

}