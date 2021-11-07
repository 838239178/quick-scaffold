package top.pressed.quickscaffold.basic.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

public abstract class DataBindingViewModel<VB extends ViewDataBinding> extends ViewModel {
    private VB binding;

    public VB getBinding() {
        return binding;
    }

    public void setBinding(VB binding) {
        this.binding = binding;
    }

    public View getRoot() {
        return binding.getRoot();
    }

    public Context getContext() {
        return getRoot().getContext();
    }

    public Resources.Theme getTheme() {
        return getContext().getTheme();
    }
}
