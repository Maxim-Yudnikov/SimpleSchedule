package com.maxim.simpleschedule.edit.presentation

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.maxim.simpleschedule.R
import com.maxim.simpleschedule.core.presentation.AbstractFragment
import com.maxim.simpleschedule.core.presentation.ProvideViewModel
import com.maxim.simpleschedule.databinding.FragmentEditBinding

class EditFragment : AbstractFragment<FragmentEditBinding>() {
    private lateinit var viewModel: EditViewModel
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            AlertDialog.Builder(this@EditFragment.context).setTitle(R.string.undo_changes)
                .setPositiveButton(R.string.positive_answer) { _, _ ->
                viewModel.cancel()
            }.create().show()
        }
    }
    override fun bind(inflater: LayoutInflater, container: ViewGroup) =
        FragmentEditBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(EditViewModel::class.java)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

        val adapter = EditLessonAdapter(object : EditLessonAdapter.Listener {
            override fun delete(position: Int) {
                viewModel.deleteItem(position)
            }

            override fun rename(position: Int, newName: String) {
                viewModel.renameItem(position, newName)
            }
        })
        binding.recyclerView.adapter = adapter
        viewModel.observeDay(this) {
            it.updateEditLessonAdapter(adapter)
            it.showTitle(binding.dayTitle)
            if (savedInstanceState == null)
                it.showTime(binding.startTimeEditText, binding.endTimeEditText)
        }
        viewModel.observeError(this) {
            if (it.isNotEmpty()) Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        binding.addItemButton.setOnClickListener {
            viewModel.newItem()
        }

        binding.saveButton.setOnClickListener {
            viewModel.save(binding.startTimeEditText.text.toString(), binding.endTimeEditText.text.toString())
        }

        viewModel.init(savedInstanceState == null, requireArguments().getInt(ID_KEY))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
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