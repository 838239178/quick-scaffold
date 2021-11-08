package top.pressed.quickscaffold.core.activity


import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.annotation.LayoutRes

import top.pressed.quickscaffold.core.DataInitializer
import top.pressed.quickscaffold.core.viewmodel.DataBindingViewModel

open class DataBindingActivity<VB : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutId: Int,
    private val vmId: Int,
    private val vmClass: Class<VM>
) : AppCompatActivity() {
    protected lateinit var viewModel: VM
    protected lateinit var binding: VB

    /**
     * create ViewModel
     */
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[vmClass]
    }

    /**
     * create ViewDataBinding
     */
    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }

    /**
     * set assigned variable to ViewModel
     * probably doing:
     * 1. save binding to view model
     * 2. invoke view model data initialize
     */
    private fun doCreate(savedInstanceState: Bundle?) {
        if (viewModel is DataBindingViewModel<*>) {
            (viewModel as DataBindingViewModel<VB>).setBinding(binding)
        }
        if (viewModel is DataInitializer) {
            (viewModel as DataInitializer).attach(savedInstanceState)
        }
        binding.setVariable(vmId, viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initDataBinding()
        preCreate(savedInstanceState)
        doCreate(savedInstanceState)
        postCreate(savedInstanceState)
    }

    protected fun preCreate(savedInstanceState: Bundle?) {}
    protected fun postCreate(savedInstanceState: Bundle?) {}
}