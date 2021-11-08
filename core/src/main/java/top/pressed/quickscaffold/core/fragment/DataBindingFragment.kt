package top.pressed.quickscaffold.core.fragment

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

import top.pressed.quickscaffold.core.DataInitializer
import top.pressed.quickscaffold.core.viewmodel.DataBindingViewModel

open class DataBindingFragment<VB : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutId: Int,
    private val vmId: Int,
    private val vmClass: Class<VM>
) : Fragment() {
    protected open lateinit var binding: VB
    protected open lateinit var viewModel: VM

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[vmClass]
    }

    private fun initDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
    }

    private fun viewCreated(savedInstanceState: Bundle?) {
        if (viewModel is DataBindingViewModel<*>) {
            (viewModel as DataBindingViewModel<VB>).setBinding(binding)
        }
        if (viewModel is DataInitializer) {
            (viewModel as DataInitializer).attach(savedInstanceState)
        }
        binding.setVariable(vmId, viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        initDataBinding(inflater, container)
        afterInit(savedInstanceState)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated(savedInstanceState)
        afterViewCreated(savedInstanceState)
    }

    protected open fun afterInit(savedInstanceState: Bundle?) {}
    protected open fun afterViewCreated(savedInstanceState: Bundle?) {}
}