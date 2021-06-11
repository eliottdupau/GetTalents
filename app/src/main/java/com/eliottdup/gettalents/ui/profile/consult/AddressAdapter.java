package com.eliottdup.gettalents.ui.profile.consult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressAdapter extends RecyclerView.Adapter<AddressViewHolder> {
    private List<Address> addresses;
    private final int layoutId;

    public AddressAdapter(List<Address> addresses, int layoutId) {
        this.addresses = addresses;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);

        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        holder.bind(this.addresses.get(position));
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
