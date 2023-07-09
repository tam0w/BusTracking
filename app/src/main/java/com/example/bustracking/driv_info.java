
package com.example.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        dataRef = FirebaseDatabase.getInstance().getReference().child("drivers");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = text.getText().toString().trim();
                searchIdInDatabase(id, fname, id1, route1, phno1);
            }
        });
    }

    private void searchIdInDatabase(String id, final TextView t, final TextView t1, final TextView t2, final TextView t3) {
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String childId = childSnapshot.child("id").getValue(String.class);
                    if (childId != null && childId.equals(id)) {
                        String name = childSnapshot.child("driv").getValue(String.class);
                        String phno = childSnapshot.child("phno").getValue(String.class);
                        String route = childSnapshot.child("route").getValue(String.class);
                        t.setText(name);
                        t3.setText(phno);
                        t2.setText(route);
                        t1.setText(childId);
                        return;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

