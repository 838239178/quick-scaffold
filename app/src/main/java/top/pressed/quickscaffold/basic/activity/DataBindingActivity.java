package top.pressed.quickscaffold.basic.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import top.pressed.quickscaffold.basic.DataInitializer;
import top.pressed.quickscaffold.basic.viewmodel.DataBindingViewModel;


public class DataBindingActivity <VB extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity {
    protected VM viewModel;
    protected VB binding;
    private final int layoutId;
    private final Class<VM> vmClass;
    private final int vmId;

    public DataBindingActivity(int layoutId, int vmId, Class<VM> vmClass) {
        super();
        this.layoutId = layoutId;
        this.vmClass = vmClass;
        this.vmId = vmId;
    }

    /**
     * create ViewModel
     */
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(vmClass);
    }

    /**
     * create ViewDataBinding
     */
    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId);
        binding.setLifecycleOwner(this);
    }

    /**
     *  set assigned variable to ViewModel
     *  probably doing:
     *  1. save binding to view model
     *  2. invoke view model data initialize
     */
    private void doCreate(Bundle savedInstanceState) {
        if (viewModel instanceof DataBindingViewModel) {
            ((DataBindingViewModel) viewModel).setBinding(binding);
        }
        if (viewModel instanceof DataInitializer) {
            ((DataInitializer) viewModel).attach(savedInstanceState);
        }
        binding.setVariable(vmId, viewModel);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        initDataBinding();
        preCreate(savedInstanceState);
        doCreate(savedInstanceState);
        postCreate(savedInstanceState);
    }

    protected void preCreate(Bundle savedInstanceState) {

    }

    protected void postCreate(Bundle savedInstanceState) {

    }
}
