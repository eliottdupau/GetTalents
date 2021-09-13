package com.eliottdup.gettalents.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.profile.consult.other.UserProfileActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class MapFragment extends Fragment {
    public static final String KEY_USER_ID = "userId";

    private GoogleMap map;
    private HashMap<Marker, Integer> markers;
    private MapViewModel viewModel;

    private final OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            googleMap.setMyLocationEnabled(true);

            map = googleMap;

            markers = new HashMap<>();

            checkPermission();
            getUsers();

            map.setOnInfoWindowClickListener(marker -> {
                Intent intent = new Intent(requireActivity(), UserProfileActivity.class);
                intent.putExtra(KEY_USER_ID, markers.get(marker));
                startActivity(intent);
            });
        }
    };

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    getLastLocation(map);
                } else {
                    showPermissionRefusedAlertDialog();
                }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(onMapReadyCallback);
        }

        viewModel = new ViewModelProvider(requireActivity()).get(MapViewModel.class);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLastLocation(map);
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(GoogleMap googleMap) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
            if (location != null) {
                //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                LatLng latLng = new LatLng(50.6228, 3.14417);
                googleMap.addMarker(new MarkerOptions().position(latLng));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            }
        });
    }

    private void getUsers() {
        viewModel.getAllUsers().observe(getViewLifecycleOwner(), users -> {
            for (User user : users) {
                addMarker(user);
            }
        });
    }

    private void addMarker(User user) {
        Address mainAddress = user.getAddresses().get(0);
        LatLng latLng = new LatLng(mainAddress.getLat(), mainAddress.getLng());

        Marker marker = map.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .title(user.getPseudo())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .snippet(String.format("%s - %s", mainAddress.getCity(), mainAddress.getCountry())));

        markers.put(marker, user.getId());
    }

    private void showPermissionRefusedAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        builder.setTitle(R.string.title_location_permission_denied)
                .setMessage(R.string.disclaimer_location_permission_denied)
                .setPositiveButton(R.string.label_ok, (dialogInterface, i) -> { })
                .show();
    }
}