package com.eliottdup.gettalents.ui.profile.consult;

import android.view.View;
import android.widget.TextView;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressViewHolder extends RecyclerView.ViewHolder {
    private TextView addressView;

    public AddressViewHolder(@NonNull View itemView) {
        super(itemView);

        addressView = itemView.findViewById(R.id.textView_address);
    }

    public void bind(Address address) {
        addressView.setText(String.format("%s, %s %s - %s", address.getStreet(), address.getPostalCode(), address.getCity(), address.getCountry()));
    }
    
}
