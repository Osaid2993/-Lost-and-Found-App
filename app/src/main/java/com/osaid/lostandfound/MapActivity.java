package com.osaid.lostandfound;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import com.osaid.lostandfound.data.DatabaseHelper;
import com.osaid.lostandfound.model.Item;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper dbHelper;
    private FusedLocationProviderClient fusedLocationClient;
    private TextInputEditText editRadius;
    private Location currentLocation;

    private static final int LOCATION_PERMISSION_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        dbHelper = new DatabaseHelper(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        editRadius = findViewById(R.id.editRadius);

        MaterialButton btnFilter = findViewById(R.id.btnFilter);
        MaterialButton btnShowAll = findViewById(R.id.btnShowAll);

        btnFilter.setOnClickListener(v -> filterByRadius());
        btnShowAll.setOnClickListener(v -> showAllMarkers());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            return;
        }

        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                currentLocation = location;
                LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 14));
            }
            showAllMarkers();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
                showAllMarkers();
            }
        }
    }

    private void showAllMarkers() {
        mMap.clear();
        List<Item> items = dbHelper.getAllItems();
        addMarkersToMap(items);
    }

    private void filterByRadius() {
        if (currentLocation == null) {
            Toast.makeText(this, "Current location not available", Toast.LENGTH_SHORT).show();
            return;
        }

        String radiusText = editRadius.getText().toString().trim();
        if (radiusText.isEmpty()) {
            Toast.makeText(this, "Please enter a radius", Toast.LENGTH_SHORT).show();
            return;
        }

        double radiusKm = Double.parseDouble(radiusText);
        mMap.clear();

        List<Item> allItems = dbHelper.getAllItems();
        int count = 0;

        for (Item item : allItems) {
            float[] results = new float[1];
            Location.distanceBetween(
                    currentLocation.getLatitude(), currentLocation.getLongitude(),
                    item.getLatitude(), item.getLongitude(), results);

            double distanceKm = results[0] / 1000.0;

            if (distanceKm <= radiusKm) {
                addSingleMarker(item);
                count++;
            }
        }

        Toast.makeText(this, count + " items within " + radiusKm + " km", Toast.LENGTH_SHORT).show();
    }

    private void addMarkersToMap(List<Item> items) {
        if (items.isEmpty()) return;

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (Item item : items) {
            addSingleMarker(item);
            builder.include(new LatLng(item.getLatitude(), item.getLongitude()));
        }

        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSingleMarker(Item item) {
        LatLng position = new LatLng(item.getLatitude(), item.getLongitude());
        float color = item.getPostType().equals("Lost")
                ? BitmapDescriptorFactory.HUE_RED
                : BitmapDescriptorFactory.HUE_GREEN;

        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(item.getPostType() + ": " + item.getName())
                .snippet(item.getDescription())
                .icon(BitmapDescriptorFactory.defaultMarker(color)));
    }
}