package com.example.bustracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class bus_track extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_track);
        Button track = findViewById(R.id.osmtrackk);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bus_track.this, maps.class);
                startActivity(intent);
            }
        });
        Button osm = findViewById(R.id.osmtrackk);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bus_track.this, osmtrack.class);
                startActivity(intent);
            }
        });


    }
}