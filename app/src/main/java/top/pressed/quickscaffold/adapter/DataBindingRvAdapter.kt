package top.pressed.quickscaffold.adapter


import androidx.recyclerview.widget.RecyclerView
import top.pressed.quickscaffold.DataBinder
import androidx.lifecycle.LifecycleOwner
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import androidx.databinding.DataBindingUtil
import top.pressed.quickscaffold.viewmodel.DataBindingViewModel
import android.annotation.SuppressLint

open class DataBindingRvAdapter<ItemDataType>(private val itemLayoutId: Int) :
    RecyclerView.Adapter<DataBindingRvAdapter.ViewHolder>() {

    private var itemVmId: Int? = null
    private var binder: DataBinder<ItemDataType>? = null
    private var outerLifeCycleOwner: LifecycleOwner? = null
    private var data: List<ItemDataType>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vb = DataBindingUtil.inflate<ViewDataBinding>(inflater, itemLayoutId, parent, false)
        vb.lifecycleOwner = outerLifeCycleOwner
        itemVmId?.let {
            vb.setVariable(it, binder)
        }
        return ViewHolder(vb)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binder?.let {
            if (it is DataBindingViewModel<*>) {
                (it as DataBindingViewModel<ViewDataBinding>).setBinding(holder.binding)
            }
            it.onBind(data!![position])
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun setItemVmId(itemVmId: Int): DataBindingRvAdapter<ItemDataType> {
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
    fun setData(data: List<ItemDataType>?) {
        if (this.data != null) {
            this.data = data
            notifyDataSetChanged()
        } else {
            this.data = data
        }
    }

    class ViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var binding: ViewDataBinding
    }
}