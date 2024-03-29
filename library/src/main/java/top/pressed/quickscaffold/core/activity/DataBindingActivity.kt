package top.pressed.quickscaffold.core.activity


import androidx.databinding.ViewDataBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.annotation.LayoutRes
import top.pressed.quickscaffold.core.DataInitializer
import top.pressed.quickscaffold.core.viewmodel.DataBindingViewModel

open class DataBindingActivity<VB : ViewDataBinding, VM : DataBindingViewModel<VB>>(
    @LayoutRes private val layoutId: Int,
    private val vmId: Int,
    private val vmClass: Class<VM>
) : AppCompatActivity() {
    protected open lateinit var viewModel: VM
    protected open lateinit var binding: VB


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
        initViewModel()
        initDataBinding()
        viewModel.setBinding(binding)
        if (viewModel is DataInitializer) {
            (viewModel as DataInitializer).attach(savedInstanceState)
        }
        binding.setVariable(vmId, viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preCreate(savedInstanceState)
        doCreate(savedInstanceState)
        postCreate(savedInstanceState)
    }

    /**
     * after init view model
     */
    protected open fun preCreate(savedInstanceState: Bundle?) {}

    /**
     * after init data binding and view model data initialize
     */
    protected open fun postCreate(savedInstanceState: Bundle?) {}
}