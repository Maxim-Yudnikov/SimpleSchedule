package com.maxim.simpleschedule.list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maxim.simpleschedule.core.presentation.DayUi
import com.maxim.simpleschedule.databinding.DayLayoutBinding

class ListAdapter(private val listener: Listener) :
    RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {
    private val list = mutableListOf<DayUi>()

    class ItemViewHolder(private val binding: DayLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DayUi, listener: Listener) {
            item.showTitle(binding.dayTitle)
            binding.editButton.setOnClickListener {
                item.edit(listener)
            }
            val adapter = LessonAdapter()
            binding.lessonsRecyclerView.adapter = adapter
            item.updateLessonAdapter(adapter)
        }
    }

    interface Listener {
        fun edit(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            DayLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    fun update(newList: List<DayUi>) {
        val diff = ListDiff(list, newList)
        val result = DiffUtil.calculateDiff(diff)
        list.clear()
        list.addAll(newList)
        result.dispatchUpdatesTo(this)
    }
}

class ListDiff(
    private val oldList: List<DayUi>,
    private val newList: List<DayUi>,
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].same(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].sameContent(newList[newItemPosition])
}