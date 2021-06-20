package com.victorbrandalise.presentation.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.ArcMotion
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import com.victorbrandalise.R
import com.victorbrandalise.databinding.FragmentItemDetailBinding
import com.victorbrandalise.model.Item

class ItemDetailFragment : Fragment() {

    private lateinit var binding: FragmentItemDetailBinding

    private val navArgs by navArgs<ItemDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.navHostController
            setPathMotion(ArcMotion())
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        initViews()
        display(navArgs.item)
    }

    private fun initViews() = with(binding) {
        back.setOnClickListener { findNavController().popBackStack() }
    }

    private fun display(item: Item) = with(binding) {
        name.text = item.name
        description.text = item.description

        Glide.with(root.context)
            .load(item.image)
            .circleCrop()
            .into(icon)
    }

}