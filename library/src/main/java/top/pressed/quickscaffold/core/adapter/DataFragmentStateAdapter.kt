package top.pressed.quickscaffold.core.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

open class DataFragmentStateAdapter<T>(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val createBlock: (T) -> Fragment
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var data: MutableList<T> = mutableListOf()

    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment {
        return createBlock.invoke(data[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<T>) {
        this.data = list
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        this.data.let {
            it.add(item)
            notifyItemInserted(it.size - 1)
        }
    }

    fun addItems(items: List<T>) {
        this.data.let {
            val start = it.size
            it.addAll(items)
            notifyItemRangeInserted(start, items.size)
        }
    }

    fun setItem(idx: Int, item: T) {
        idx.takeIf {
            idx < itemCount
        }?.let {
            this.data.let { dt->
                dt[idx] = item
                notifyItemChanged(idx)
            }
        }
    }

    fun removeItem(idx: Int) {
        idx.takeIf {
            idx < itemCount
        }?.let {
            this.data.removeAt(idx)
            notifyItemRemoved(idx)
        }
    }
}