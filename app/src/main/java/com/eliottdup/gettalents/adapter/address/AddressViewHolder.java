package com.eliottdup.gettalents.adapter.address;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressViewHolder extends RecyclerView.ViewHolder {
    private final TextView addressView;
    private final ImageView editIcon;

    public AddressViewHolder(@NonNull View itemView) {
        super(itemView);

        addressView = itemView.findViewById(R.id.textView_address);
        editIcon = itemView.findViewById(R.id.icon_arrowForward_address);
    }

    public void bind(Address address, boolean isEditable) {
        addressView.setText(String.format("%s, %s %s - %s", address.getAddress(), address.getZipCode(), address.getCity(), address.getCountry()));

        editIcon.setVisibility(isEditable ? View.VISIBLE : View.GONE);
    }
}
