package com.victorbrandalise.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialFadeThrough
import com.victorbrandalise.databinding.FragmentItemListBinding
import com.victorbrandalise.model.Item
import com.victorbrandalise.presentation.list.adapter.ItemAdapter

class ListFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding

    private val viewModel by viewModels<ItemListViewModel>()

    private val adapter by lazy { ItemAdapter(emptyList(), ::onItemClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeViewModel()
    }

    private fun initViews() = with(binding) {
        items.adapter = adapter

        cart.setOnClickListener {
            findNavController().navigate(ListFragmentDirections.actionListToCart())
        }
    }

    private fun observeViewModel() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.setItems(items)
        }
    }

    private fun onItemClicked(item: Item) {
        findNavController().navigate(ListFragmentDirections.actionListToDetail(item))
    }

}