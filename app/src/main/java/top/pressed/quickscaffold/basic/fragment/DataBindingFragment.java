package top.pressed.quickscaffold.basic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import top.pressed.quickscaffold.basic.DataInitializer;
import top.pressed.quickscaffold.basic.viewmodel.DataBindingViewModel;


public class DataBindingFragment<VB extends ViewDataBinding, VM extends ViewModel> extends Fragment {
    private final int layoutId;
    private final int vmId;
    private final Class<VM> vmClass;
    private VB binding;
    protected VM viewModel;

    public DataBindingFragment(int layoutId, int vmId, Class<VM> vmClass) {
        this.layoutId = layoutId;
        this.vmId = vmId;
        this.vmClass = vmClass;
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(vmClass);
    }

    private void initDataBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        binding.setLifecycleOwner(this);
    }

    private void viewCreated(Bundle savedInstanceState) {
        if (viewModel instanceof DataBindingViewModel) {
            ((DataBindingViewModel) viewModel).setBinding(binding);
        }
        if (viewModel instanceof DataInitializer) {
            ((DataInitializer) viewModel).attach(savedInstanceState);
        }
        binding.setVariable(vmId, viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViewModel();
        initDataBinding(inflater,container);
        afterInit(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewCreated(savedInstanceState);
        afterViewCreated(savedInstanceState);
    }

    protected void afterInit(Bundle savedInstanceState) {

    }

    protected void afterViewCreated(Bundle savedInstanceState) {

    }
}
