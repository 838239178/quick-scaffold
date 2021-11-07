package top.pressed.quickscaffold.fragment

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import top.pressed.quickscaffold.viewmodel.DataBindingViewModel
import top.pressed.quickscaffold.DataInitializer

open class DataBindingFragment<VB : ViewDataBinding, VM : ViewModel>(
    private val layoutId: Int,
    private val vmId: Int,
    private val vmClass: Class<VM>
) : Fragment() {
    protected lateinit var binding: VB
    protected lateinit var viewModel: VM

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

    protected fun afterInit(savedInstanceState: Bundle?) {}
    protected fun afterViewCreated(savedInstanceState: Bundle?) {}
}