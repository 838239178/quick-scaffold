package top.pressed.quickscaffold.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.drakeet.multitype.ItemViewBinder
import top.pressed.quickscaffold.core.viewmodel.DataBinderViewModel

class DataBindingItemViewBinder<VB: ViewDataBinding, T>(
    @LayoutRes private val itemLayoutId: Int
): ItemViewBinder<T, DataBindingViewHolder<VB, T>>() {

    private var itemVmId: Int? = null
    private var outerLifeCycleOwner: LifecycleOwner? = null
    private var binder: DataBinderViewModel<VB,T>? = null

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DataBindingViewHolder<VB,T> {
        val vb = DataBindingUtil.inflate<VB>(inflater, itemLayoutId, parent, false)
        vb.lifecycleOwner = outerLifeCycleOwner
        return DataBindingViewHolder(vb, itemVmId)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<VB,T>, item: T) {
        binder?.let {
            holder.bind(it, item)
        }
    }

    fun setOuterLifeCycleOwner(outerLifeCycleOwner: LifecycleOwner?): DataBindingItemViewBinder<VB,T> {
        this.outerLifeCycleOwner = outerLifeCycleOwner
        return this
    }

    fun setViewModel(binder: DataBinderViewModel<VB,T>, variableId: Int): DataBindingItemViewBinder<VB,T> {
        this.binder = binder
        this.itemVmId = variableId
        return this
    }
}