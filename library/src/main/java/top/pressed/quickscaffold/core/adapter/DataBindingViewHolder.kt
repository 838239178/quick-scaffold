package top.pressed.quickscaffold.core.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import top.pressed.quickscaffold.core.viewmodel.DataBinderViewModel

open class DataBindingViewHolder<VB : ViewDataBinding, T>(
    private var binding: VB,
    private var bindId: Int?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: DataBinderViewModel<VB, T>, data: T) {
        viewModel.setBinding(binding)
        bindId?.let {
            binding.setVariable(it, viewModel)
        }
        viewModel.onBind(data)
    }
}