package top.pressed.quickscaffold.core.adapter



import androidx.lifecycle.LifecycleOwner
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import androidx.databinding.DataBindingUtil
import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import top.pressed.quickscaffold.core.DataBinder
import top.pressed.quickscaffold.core.viewmodel.DataBindingViewModel

open class DataBindingRvAdapter<ItemDataType>(private val itemLayoutId: Int) :
    RecyclerView.Adapter<DataBindingRvAdapter.ViewHolder<ItemDataType>>() {

    private var itemVmId: Int? = null
    private var binder: DataBinder<ItemDataType>? = null
    private var outerLifeCycleOwner: LifecycleOwner? = null
    private var data: MutableList<ItemDataType>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemDataType> {
        val inflater = LayoutInflater.from(parent.context)
        val vb = DataBindingUtil.inflate<ViewDataBinding>(inflater, itemLayoutId, parent, false)
        vb.lifecycleOwner = outerLifeCycleOwner
        return ViewHolder<ItemDataType>(vb, itemVmId)
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemDataType>, position: Int) {
        binder?.let {
            holder.bind(it, data!![position])
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun setItemVmId(itemVmId: Int?): DataBindingRvAdapter<ItemDataType> {
        this.itemVmId = itemVmId
        return this
    }

    fun setOuterLifeCycleOwner(outerLifeCycleOwner: LifecycleOwner?): DataBindingRvAdapter<ItemDataType> {
        this.outerLifeCycleOwner = outerLifeCycleOwner
        return this
    }

    fun setBinder(binder: DataBinder<ItemDataType>?): DataBindingRvAdapter<ItemDataType> {
        this.binder = binder
        return this
    }

    fun getData(): List<ItemDataType>? {
        return data
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<ItemDataType>?) {
        if (this.data != null) {
            this.data = data
            notifyDataSetChanged()
        } else {
            this.data = data
        }
    }

    fun addItem(item: ItemDataType) {
        this.data?.let {
            it.add(item)
            notifyItemInserted(it.size - 1)
        }
    }

    fun addItems(items: List<ItemDataType>) {
        this.data?.let {
            val start = it.size
            it.addAll(items)
            notifyItemRangeInserted(start, items.size)
        }
    }

    fun setItem(idx: Int, item: ItemDataType) {
        idx.takeIf {
            idx < itemCount
        }?.let {
           this.data?.let { dt->
               dt[idx] = item
               notifyItemChanged(idx)
           }
        }
    }

    open class ViewHolder<T>(var binding: ViewDataBinding, var bindId: Int?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(binder: DataBinder<T>, data: T) {
            if (binder is DataBindingViewModel<*>) {
                (binder as DataBindingViewModel<ViewDataBinding>).setBinding(binding)
            }
            bindId?.let {
                binding.setVariable(it, binder)
            }
            binder.onBind(data)
        }
    }
}