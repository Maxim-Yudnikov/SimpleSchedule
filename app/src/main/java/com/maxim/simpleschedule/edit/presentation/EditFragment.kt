package com.maxim.simpleschedule.edit.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maxim.simpleschedule.core.presentation.AbstractFragment
import com.maxim.simpleschedule.core.presentation.ProvideViewModel
import com.maxim.simpleschedule.databinding.FragmentEditBinding

class EditFragment : AbstractFragment<FragmentEditBinding>() {
    private lateinit var viewModel: EditViewModel
    override fun bind(inflater: LayoutInflater, container: ViewGroup) =
        FragmentEditBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(EditViewModel::class.java)
    }

    companion object {
        fun newInstance(id: Int): EditFragment {
            return EditFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_KEY, id)
                }
            }
        }
        private const val ID_KEY = "ID"
    }
}