package com.victorbrandalise.presentation.list

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victorbrandalise.model.Item

class ItemListViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    init {
        loadItems()
    }

    private fun loadItems() {
        // Load fake items

        _items.value = listOf(
            Item(
                "Android Charger",
                "The aluminum shell and the tangle-free braided nylon make the cable extremely durable! The nylon braid is more flexible, pull-resistant, soft and weighs less when being compared to traditional device cables.",
                Uri.parse("https://m.media-amazon.com/images/I/51B4dMOp49L._AC_SS450_.jpg")
            ),
            Item(
                "Basics Kitchen Rolling",
                "Microwave kitchen cart made of chrome-plated steel with removable wood top (1.5 inches)",
                Uri.parse("https://images-na.ssl-images-amazon.com/images/I/81cxPhqC0cL._AC_SL1500_.jpg")
            ),
            Item(
                "Kitchen Knife",
                "Custom knife set with mallee burr handles and neon green resin liner",
                Uri.parse("https://images.unsplash.com/photo-1602063238855-524c4d8b9b18?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1084&q=80")
            ),
            Item(
                "Charcoal",
                "Highly durable. Up to 8hrs",
                Uri.parse("https://images.unsplash.com/photo-1613897807164-01263a2296e2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80")
            ),
        )
    }

}