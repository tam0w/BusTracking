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
    TextView fname;
    TextView phno1;
    TextView route1;
    TextView id1;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driv_info);
        Button btn = findViewById(R.id.stop);
        TextView fname = findViewById(R.id.name);
        TextView phno1 = findViewById(R.id.phno);
        TextView route1 = findViewById(R.id.route);
        TextView id1 = findViewById(R.id.idinfo);
        EditText text = findViewById(R.id.id2);

        // Create a database reference to the desired node
        dataRef = FirebaseDatabase.getInstance().getReference();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String node = text.getText().toString().trim();
                retrieveDataFromNode(node, fname, id1, route1, phno1);
            }
        });
    }

    private void retrieveDataFromNode(String node, final TextView t, final TextView t1, final TextView t2, final TextView t3) {
        dataRef.child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("driv").getValue(String.class);
                String phno = dataSnapshot.child("phno").getValue(String.class);
                String route = dataSnapshot.child("route").getValue(String.class);
                String id = dataSnapshot.child("id").getValue(String.class);
                t.setText(name);
                t3.setText(phno);
                t2.setText(route);
                t1.setText(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
