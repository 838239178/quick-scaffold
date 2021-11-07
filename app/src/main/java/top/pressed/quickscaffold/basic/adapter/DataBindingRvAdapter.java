package top.pressed.quickscaffold.basic.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.pressed.quickscaffold.basic.DataBinder;
import top.pressed.quickscaffold.basic.viewmodel.DataBindingViewModel;


public class DataBindingRvAdapter<ItemDataType> extends RecyclerView.Adapter<DataBindingRvAdapter.ViewHolder> {
    private final int itemLayoutId;
    private int itemVmId = -1;
    private DataBinder<ItemDataType> binder;
    private LifecycleOwner outerLifeCycleOwner;

    private List<ItemDataType> data;

    public DataBindingRvAdapter(int itemLayoutId) {
        super();
        this.itemLayoutId = itemLayoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding vb = DataBindingUtil.inflate(inflater, itemLayoutId, parent, false);
        if(outerLifeCycleOwner != null) {
            vb.setLifecycleOwner(outerLifeCycleOwner);
        }
        if (binder != null && itemVmId != -1) {
            vb.setVariable(itemVmId, binder);
        }
        return new ViewHolder(vb);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (binder != null) {
            if (binder instanceof DataBindingViewModel) {
                ((DataBindingViewModel) binder).setBinding(holder.binding);
            }
            binder.onBind(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public DataBindingRvAdapter<ItemDataType> setItemVmId(int itemVmId) {
        this.itemVmId = itemVmId;
        return this;
    }

    public DataBindingRvAdapter<ItemDataType> setOuterLifeCycleOwner(LifecycleOwner outerLifeCycleOwner) {
        this.outerLifeCycleOwner = outerLifeCycleOwner;
        return this;
    }

    public DataBindingRvAdapter<ItemDataType> setBinder(DataBinder<ItemDataType> binder) {
        this.binder = binder;
        return this;
    }

    public List<ItemDataType> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ItemDataType> data) {
        if (this.data != null) {
            this.data = data;
            notifyDataSetChanged();
        } else {
            this.data = data;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;
        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
        }
    }

}
