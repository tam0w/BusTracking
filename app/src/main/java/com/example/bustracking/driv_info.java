package com.example.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;

public class driv_info extends AppCompatActivity {

    private DatabaseReference dataRef;

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

        // Create a database reference to the desired node
        dataRef = FirebaseDatabase.getInstance().getReference();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String node = text.getText().toString().trim();
                retrieveDataFromNode(node, name);
            }
        });
    }

    private void retrieveDataFromNode(String node, final TextView textView) {
        dataRef.child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                textView.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
