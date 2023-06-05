package com.example.bustracking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;

public class driv_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driv_info);
        Button btn = findViewById(R.id.stop);
        TextView name = findViewById(R.id.name);
        TextView phno = findViewById(R.id.phno);
        TextView route = findViewById(R.id.route);
        TextView id = findViewById(R.id.idinfo);
        EditText text = findViewById(R.id.id2);
        String vxtalue = text.getText().toString();

        Reference ref = FirebaseDatabase.getInstance().getReference().child('test');
    }
}