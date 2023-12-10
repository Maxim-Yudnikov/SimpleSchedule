package com.maxim.simpleschedule.edit.presentation

import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.maxim.simpleschedule.core.presentation.LessonUi
import com.maxim.simpleschedule.core.presentation.SimpleTextWatcher
import com.maxim.simpleschedule.databinding.EditLessonEmptyBinding
import com.maxim.simpleschedule.databinding.EditLessonLayoutBinding

class EditLessonAdapter(private val listener: Listener) :
    RecyclerView.Adapter<EditLessonAdapter.ItemViewHolder>() {
    private val list = mutableListOf<LessonUi>()
    private val watchers = mutableListOf<Pair<EditText, SimpleTextWatcher>>()
    private var setFocus: Boolean = true

    abstract class ItemViewHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        open fun bind(
            item: LessonUi,
            listener: Listener,
            position: Int,
            watchers: MutableList<Pair<EditText, SimpleTextWatcher>>,
            setFocus: Boolean
        ) {
        }
    }

    class LessonViewHolder(private val binding: EditLessonLayoutBinding) : ItemViewHolder(binding) {
        override fun bind(
            item: LessonUi, listener: Listener,
            position: Int, watchers: MutableList<Pair<EditText, SimpleTextWatcher>>,
            setFocus: Boolean
        ) {
            item.show(binding.lessonNameEditText)
            binding.deleteLessonButton.setOnClickListener {
                listener.delete(position)
            }
            val textWatcher = object : SimpleTextWatcher() {
                override fun afterTextChanged(s: Editable?) {
                    listener.rename(position, s.toString())
                }
            }
            binding.lessonNameEditText.addTextChangedListener(textWatcher)
            watchers.add(Pair(binding.lessonNameEditText, textWatcher))
            if (setFocus)
                binding.lessonNameEditText.requestFocus()
        }
    }

    class EmptyViewHolder(binding: EditLessonEmptyBinding) : ItemViewHolder(binding)

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is LessonUi.Empty) 1 else 0
    }

    interface Listener {
        fun delete(position: Int)
        fun rename(position: Int, newName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return if (viewType == 0) LessonViewHolder(
            EditLessonLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ) else EmptyViewHolder(
            EditLessonEmptyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position], listener, position, watchers, (position + 1) == itemCount && setFocus)
    }

    fun update(newList: List<LessonUi>, setFocus: Boolean) {
        list.clear()
        list.addAll(newList)
        watchers.forEach {
            it.first.removeTextChangedListener(it.second)
        }
        watchers.clear()
        this.setFocus = setFocus
        notifyDataSetChanged()
    }
}