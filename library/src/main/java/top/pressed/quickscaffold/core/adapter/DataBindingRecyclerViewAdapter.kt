package top.pressed.quickscaffold.core.adapter


import androidx.lifecycle.LifecycleOwner
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import androidx.databinding.DataBindingUtil
import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import top.pressed.quickscaffold.core.viewmodel.DataBinderViewModel

open class DataBindingRecyclerViewAdapter<VB : ViewDataBinding, T>(
    @LayoutRes private val itemLayoutId: Int
) : RecyclerView.Adapter<DataBindingViewHolder<VB, T>>() {

    private var itemVmId: Int? = null
    private var viewModel: DataBinderViewModel<VB, T>? = null
    private var outerLifeCycleOwner: LifecycleOwner? = null
    private var data: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<VB, T> {
        val inflater = LayoutInflater.from(parent.context)
        val vb = DataBindingUtil.inflate<VB>(inflater, itemLayoutId, parent, false)
        vb.lifecycleOwner = outerLifeCycleOwner
        return DataBindingViewHolder(vb, itemVmId)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<VB, T>, position: Int) {
        viewModel?.let {
            holder.bind(it, data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOuterLifeCycleOwner(outerLifeCycleOwner: LifecycleOwner?): DataBindingRecyclerViewAdapter<VB, T> {
        this.outerLifeCycleOwner = outerLifeCycleOwner
        return this
    }

    fun setViewModel(
        model: DataBinderViewModel<VB, T>?,
        variableId: Int
    ): DataBindingRecyclerViewAdapter<VB, T> {
        this.viewModel = model
        this.itemVmId = variableId;
        return this
    }

    fun getData(): List<T> {
        return data
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<T>) {
        this.data = data
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
            this.data.let { dt ->
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