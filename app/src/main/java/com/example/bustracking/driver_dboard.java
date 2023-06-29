package com.example.bustracking;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bustracking.R;
import com.example.bustracking.account;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class driver_dboard extends AppCompatActivity {
    Button logout;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference dataRef;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        setContentView(R.layout.activity_driver_dboard);

// PREV CODE STARTS FROM HERE
        logout = findViewById(R.id.signout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(driver_dboard.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final TextView fname = findViewById(R.id.name);
        final TextView phno1 = findViewById(R.id.phno);
        final TextView route1 = findViewById(R.id.route);
        final TextView id1 = findViewById(R.id.idinfo);
        final TextView coords = findViewById(R.id.coords);

        // Create a database reference to the "drivers" node
        dataRef = FirebaseDatabase.getInstance().getReference().child("drivers");

        String email = mAuth.getCurrentUser().getEmail();
        if (!email.isEmpty()) {
            searchDriverByEmail(email, fname, id1, route1, phno1, coords);
        }

    }

    private void searchDriverByEmail(final String email, final TextView t, final TextView t1, final TextView t2, final TextView t3, final TextView coords) {
        Query query = dataRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (final DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String name = childSnapshot.child("driv").getValue(String.class);
                        String phno = childSnapshot.child("phno").getValue(String.class);
                        String route = childSnapshot.child("route").getValue(String.class);
                        String id = childSnapshot.child("id").getValue(String.class);
                        t.setText(name);
                        t3.setText(phno);
                        t2.setText(route);
                        t1.setText(id);

                        if (childSnapshot.child("latitude").exists() && childSnapshot.child("longitude").exists()) {
                            float latitude = childSnapshot.child("latitude").getValue(float.class);
                            float longitude = childSnapshot.child("longitude").getValue(float.class);
                            String coordinates = "(" + longitude + " , " + latitude + ")";
                            coords.setText(coordinates);
                        }
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        locationListener = new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                // Handle location updates here
                                float latitudeset = (float) location.getLatitude();
                                float longitudeset = (float) location.getLongitude();

//                                 Store the latitude and longitude in the child snapshot
                                childSnapshot.child("latitude").getRef().setValue(latitudeset);
                                childSnapshot.child("longitude").getRef().setValue(longitudeset);

                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {}

                            @Override
                            public void onProviderEnabled(String provider) {}

                            @Override
                            public void onProviderDisabled(String provider) {}


                        };

                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // Request location permission if not granted
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                            return;
                        }

                        // Register the location listener with the location manager
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }

}




