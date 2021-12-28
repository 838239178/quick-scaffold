package top.pressed.quickscaffold.core.viewmodel

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import top.pressed.quickscaffold.core.DataInitializer

abstract class DataBindingViewModel<VB: ViewDataBinding>: ViewModel() {
    var binding: VB? = null
        private set

    fun setBinding(binding: VB) {
        this.binding = binding
    }

    private val root: View
        get() = binding!!.root
    private val context: Context
        get() = root.context
    val theme: Resources.Theme
        get() = context.theme
}