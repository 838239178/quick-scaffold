package top.pressed.quickscaffold.core.viewmodel

import androidx.databinding.ViewDataBinding

abstract class DataBinderViewModel<VB : ViewDataBinding, T> : DataBindingViewModel<VB>() {
    abstract fun onBind(data: T);
}