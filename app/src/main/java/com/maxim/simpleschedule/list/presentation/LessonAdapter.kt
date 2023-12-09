package com.maxim.simpleschedule.list.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.maxim.simpleschedule.core.presentation.LessonUi
import com.maxim.simpleschedule.databinding.LessonEmptyBinding
import com.maxim.simpleschedule.databinding.LessonLayoutBinding
import com.maxim.simpleschedule.databinding.LessonTimeBinding

class LessonAdapter : RecyclerView.Adapter<LessonAdapter.ItemViewHolder>() {
    private val list = mutableListOf<LessonUi>()

    abstract class ItemViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(item: LessonUi) {}
    }

    class LessonViewHolder(private val binding: LessonLayoutBinding) : ItemViewHolder(binding) {
        override fun bind(item: LessonUi) {
            item.show(binding.lessonName)
        }
    }

    class TimeViewHolder(private val binding: LessonTimeBinding) : ItemViewHolder(binding) {
        override fun bind(item: LessonUi) {
            item.show(binding.lessonTime)
        }
    }

    class EmptyViewHolder(binding: LessonEmptyBinding) : ItemViewHolder(binding)

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        return if (item is LessonUi.Base) 0 else if (item is LessonUi.Time) 1 else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            0 -> LessonViewHolder(
                LessonLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
            1 -> TimeViewHolder(
                LessonTimeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> EmptyViewHolder(
                LessonEmptyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun update(newList: List<LessonUi>) {
        val diff = LessonDiff(list, newList)
        val result = DiffUtil.calculateDiff(diff)
        list.clear()
        list.addAll(newList)
        result.dispatchUpdatesTo(this)
        Log.d("MyLog", list.size.toString())
    }
}

class LessonDiff(
    private val oldList: List<LessonUi>,
    private val newList: List<LessonUi>,
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].same(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = false
}