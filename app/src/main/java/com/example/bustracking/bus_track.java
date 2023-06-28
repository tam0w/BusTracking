package com.example.bustracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class bus_track extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_track);
        EditText numb1 = findViewById(R.id.search);
        Button track = findViewById(R.id.osmtrackk);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numb = numb1.getText().toString();
                Intent intent1 = new Intent(bus_track.this, osmtrack.class);
                intent1.putExtra("numb",numb);
                startActivity(intent1);
            }
        });


    }
}