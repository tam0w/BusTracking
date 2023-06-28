package com.example.bustracking;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Handler;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class osmtrack extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private static final long UPDATE_INTERVAL = 5000; // 5 seconds

    private MapView mapView;
    private IMapController mapController;
    private Marker marker;
    private Handler handler;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the osmdroid configuration
        Configuration.getInstance().load(getApplicationContext(), getPreferences(MODE_PRIVATE));
        setContentView(R.layout.activity_osmtrack);

        // Check for necessary permissions
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            initializeMap();
            startFetchingGeoPoint();
        }
    }

    private boolean checkPermissions() {
        int permissionState = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(osmtrack.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void initializeMap() {
        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        mapController = mapView.getController();
        mapController.setZoom(15.0);

        // Set the initial marker position
        GeoPoint startPoint = new GeoPoint(12.9141, 74.8560);
        mapController.setCenter(startPoint);

        // Create a custom marker with a custom icon
        // Create a custom marker with a custom icon
        Drawable customMarkerIcon = ContextCompat.getDrawable(this, R.drawable.custom_marker);
        int markerWidth = customMarkerIcon.getIntrinsicWidth(); // Original marker width
        int markerHeight = customMarkerIcon.getIntrinsicHeight(); // Original marker height

// Define the desired marker size (e.g., 50x50 pixels)
        int desiredWidth = 50;
        int desiredHeight = 50;

// Calculate the scale factor for resizing the marker
        float scaleWidth = (float) desiredWidth / markerWidth;
        float scaleHeight = (float) desiredHeight / markerHeight;

// Create a new resized marker icon
        int newWidth = Math.round(markerWidth * scaleWidth);
        int newHeight = Math.round(markerHeight * scaleHeight);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) customMarkerIcon).getBitmap(), newWidth, newHeight, true);
        Drawable resizedMarkerIcon = new BitmapDrawable(getResources(), resizedBitmap);

// Create the marker and set the resized icon
        marker = new Marker(mapView);
        marker.setIcon(resizedMarkerIcon);
        marker.setPosition(startPoint);
        mapView.getOverlays().add(marker);
    }


    private void startFetchingGeoPoint() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                fetchGeoPointFromDatabase();
                handler.postDelayed(this, UPDATE_INTERVAL);
            }
        };
        handler.postDelayed(runnable, UPDATE_INTERVAL);
    }

    private void stopFetchingGeoPoint() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private void fetchGeoPointFromDatabase() {
        String childNodeToSearch = getIntent().getStringExtra("numb"); // The child node you want to search for

        FirebaseDatabase.getInstance().getReference("drivers")
                .child(childNodeToSearch)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Double latitude = dataSnapshot.child("latitude").getValue(Double.class);
                            Double longitude = dataSnapshot.child("longitude").getValue(Double.class);

                            if (latitude != null && longitude != null) {
                                updateMarkerPosition(latitude, longitude);
                                Toast.makeText(osmtrack.this, "Position updated.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle case when latitude or longitude is missing
                                Toast.makeText(osmtrack.this, "Coordinates missing.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle case when child node is not found
                            Toast.makeText(osmtrack.this, "Child node not found in database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(osmtrack.this, "Failed to fetch marker location", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void updateMarkerPosition(double latitude, double longitude) {
        GeoPoint newPosition = new GeoPoint(latitude, longitude);
        marker.setPosition(newPosition);
        mapController.setCenter(newPosition);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // User interaction was interrupted, permission request was cancelled.
                Toast.makeText(this, "Permission request was cancelled.", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                initializeMap();
                startFetchingGeoPoint();
            } else {
                // Permission denied.
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume the osmdroid configuration
        Configuration.getInstance().load(getApplicationContext(), getPreferences(MODE_PRIVATE));
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the osmdroid configuration
        Configuration.getInstance().save(getApplicationContext(), getPreferences(MODE_PRIVATE));
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopFetchingGeoPoint();
    }
}

