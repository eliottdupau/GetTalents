package com.eliottdup.gettalents.ui.address;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.adapter.address.AddressAdapter;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.utils.ItemClickSupport;
import com.eliottdup.gettalents.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddressesFragment extends Fragment {
    public static final String KEY_IS_EDITABLE = "isEditable";

    private RecyclerView recyclerView;

    private UserViewModel viewModel;

    private FragmentManager fragmentManager;

    private boolean isEditable;

    private AddressAdapter adapter;
    private List<Address> addressList;

    public AddressesFragment() { }

    public static AddressesFragment newInstance(boolean isEditable) {
        AddressesFragment addressesFragment = new AddressesFragment();

        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_EDITABLE, isEditable);
        addressesFragment.setArguments(args);

        return addressesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addresses, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            isEditable = getArguments().getBoolean(KEY_IS_EDITABLE);
        }

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        fragmentManager = getParentFragmentManager();

        configureRecyclerView();

        getUser();
    }

    private void configureRecyclerView() {
        addressList = new ArrayList<>();
        adapter = new AddressAdapter(addressList, isEditable);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (isEditable) {
            ItemClickSupport.addTo(recyclerView, R.layout.item_edit_address)
                    .setOnItemClickListener((recyclerView, position, v) -> {
                        Address address = adapter.getAddress(position);

                        UpdateAddressDialogFragment updateAddressDialogFragment = UpdateAddressDialogFragment.newInstance(address.getId());
                        updateAddressDialogFragment.show(fragmentManager, "updateAddressFragment");
                    });
        }
    }

    private void getUser() {
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            addressList = user.getAddresses();
            adapter.updateData(addressList);
        });
    }
}