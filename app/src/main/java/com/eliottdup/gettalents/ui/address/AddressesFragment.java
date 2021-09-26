package com.eliottdup.gettalents.ui.address;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.adapter.address.AddressAdapter;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.utils.ItemClickSupport;
import com.eliottdup.gettalents.viewmodel.AddressViewModel;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AddressesFragment extends Fragment {
    public static final String KEY_IS_EDITABLE = "isEditable";

    private RecyclerView recyclerView;
    private TextView noAddressView;

    private AddressViewModel viewModel;
    private FragmentManager fragmentManager;
    private FirebaseAuth firebaseAuth;

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
        noAddressView = root.findViewById(R.id.textView_noAddress);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            isEditable = getArguments().getBoolean(KEY_IS_EDITABLE);
        }

        viewModel = new ViewModelProvider(requireActivity()).get(AddressViewModel.class);

        firebaseAuth = FirebaseAuth.getInstance();

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
            configureRecyclerViewItemClicked();
            configureRecyclerViewItemLongClicked();
        }
    }

    private void configureRecyclerViewItemClicked() {
        ItemClickSupport.addTo(recyclerView, R.layout.item_edit_address)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Address address = adapter.getAddress(position);

                    UpdateAddressDialogFragment updateAddressDialogFragment = UpdateAddressDialogFragment.newInstance(address);
                    updateAddressDialogFragment.show(fragmentManager, "updateAddressFragment");
                });
    }

    private void configureRecyclerViewItemLongClicked() {
        ItemClickSupport.addTo(recyclerView, R.layout.item_edit_address)
                .setOnItemLongClickListener((recyclerView, position, v) -> {
                    Address address = adapter.getAddress(position);

                    deleteAddressDialog(address);

                    return true;
                });
    }

    private void deleteAddressDialog(Address address) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle(R.string.title_delete_address)
                .setMessage(R.string.disclaimer_delete_data)
                .setPositiveButton(R.string.label_ok, (dialogInterface, i) -> {
                    viewModel.deleteAddress(address.getId());
                    viewModel.deleteAddressInList(address);
                })
                .setNegativeButton(R.string.label_cancel, (dialogInterface, i) -> {})
                .show();
    }

    private void getUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        viewModel.getLoggedUser(firebaseUser.getUid());
        viewModel.user.observe(getViewLifecycleOwner(), this::getUserAddresses);
    }

    private void getUserAddresses(User user) {
        viewModel.getAllAddressesForUser(user.getId());
        viewModel.getAddressList().observe(getViewLifecycleOwner(), addresses -> {
            isAddressListEmpty(addresses.size() == 0);

            if (addresses.size() > 0) {
                addressList = addresses;
                adapter.updateData(addressList);
            }
        });
    }

    private void isAddressListEmpty(boolean isEmpty) {
        noAddressView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }
}