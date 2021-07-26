package com.eliottdup.gettalents.adapter.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressAdapter extends RecyclerView.Adapter<AddressViewHolder> {
    private List<Address> addresses;
    private boolean isEditable;

    public AddressAdapter(List<Address> addresses, boolean isEditable) {
        this.addresses = addresses;
        this.isEditable = isEditable;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_edit_address, parent, false);

        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        holder.bind(this.addresses.get(position), isEditable);
    }

    @Override
    public int getItemCount() {
        return this.addresses.size();
    }

    public void updateData(List<Address> addresses) {
        this.addresses = addresses;
        notifyDataSetChanged();
    }

    public Address getAddress(int position) {
        return this.addresses.get(position);
    }
}
