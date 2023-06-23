package com.example.bustracking;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class driver_dboard extends AppCompatActivity {
//    Button logout;
//    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    DatabaseReference dataRef;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        setContentView(R.layout.activity_driver_dboard);
//        logout = findViewById(R.id.signout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(driver_dboard.this, account.class);
//                startActivity(intent);
//            }
//
//        });
//        TextView fname = findViewById(R.id.name);
//        TextView phno1 = findViewById(R.id.phno);
//        TextView route1 = findViewById(R.id.route);
//        TextView id1 = findViewById(R.id.idinfo);
//
//        // Create a database reference to the desired node
//        dataRef = FirebaseDatabase.getInstance().getReference();
//        dataRef = dataRef.child("drivers");
//        retrieveDataFromNode(node, fname, id1, route1, phno1);
//
//
//    }
//
//    private void retrieveDataFromNode(String node, final TextView t, final TextView t1, final TextView t2, final TextView t3) {
//        dataRef.child(node).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String name = dataSnapshot.child("driv").getValue(String.class);
//                String phno = dataSnapshot.child("phno").getValue(String.class);
//                String route = dataSnapshot.child("route").getValue(String.class);
//                String id = dataSnapshot.child("id").getValue(String.class);
//                t.setText(name);
//                t3.setText(phno);
//                t2.setText(route);
//                t1.setText(id);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//    }
//
//}

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        setContentView(R.layout.activity_driver_dboard);
        logout = findViewById(R.id.signout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(driver_dboard.this, account.class);
                startActivity(intent);
            }
        });

        final TextView fname = findViewById(R.id.name);
        final TextView phno1 = findViewById(R.id.phno);
        final TextView route1 = findViewById(R.id.route);
        final TextView id1 = findViewById(R.id.idinfo);

        // Create a database reference to the "drivers" node
        dataRef = FirebaseDatabase.getInstance().getReference().child("drivers");

        String email = mAuth.getCurrentUser().getEmail().toString();
        if (!email.isEmpty()) {
            searchDriverByEmail(email, fname, id1, route1, phno1);
        }

    }

    private void searchDriverByEmail(final String email, final TextView t, final TextView t1, final TextView t2, final TextView t3) {
        Query query = dataRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String name = childSnapshot.child("driv").getValue(String.class);
                        String phno = childSnapshot.child("phno").getValue(String.class);
                        String route = childSnapshot.child("route").getValue(String.class);
                        String id = childSnapshot.child("id").getValue(String.class);
                        t.setText(name);
                        t3.setText(phno);
                        t2.setText(route);
                        t1.setText(id);
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
