package com.maxim.simpleschedule.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maxim.simpleschedule.core.presentation.AbstractFragment
import com.maxim.simpleschedule.core.presentation.ProvideViewModel
import com.maxim.simpleschedule.databinding.FragmentListBinding

class ListFragment: AbstractFragment<FragmentListBinding>() {
    private lateinit var viewModel: ListViewModel
    override fun bind(inflater: LayoutInflater, container: ViewGroup) =
        FragmentListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(ListViewModel::class.java)
    }
}