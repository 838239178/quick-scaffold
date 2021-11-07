package top.pressed.quickscaffold.basic.viewmodel

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import android.content.res.Resources.Theme
import android.view.View

abstract class DataBindingViewModel<VB : ViewDataBinding> : ViewModel() {
    var binding: VB? = null
        private set

    fun setBinding(binding: VB) {
        this.binding = binding
    }

    val root: View
        get() = binding!!.root
    val context: Context
        get() = root.context
    val theme: Theme
        get() = context.theme
}