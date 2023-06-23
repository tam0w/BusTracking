//public class register2 extends AppCompatActivity {
//    private DatabaseReference dataRef;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register2);
//        String registeredEmail = getIntent().getStringExtra("num");
//        Button btnSave = findViewById(R.id.add);
//        TextView name = findViewById(R.id.namereg);
//        TextView phno = findViewById(R.id.phnoreg);
//        TextView route = findViewById(R.id.routenoreg);
//        TextView id = findViewById(R.id.busnoreg);
//        dataRef = FirebaseDatabase.getInstance().getReference();
//    }
//}

package com.example.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register2 extends AppCompatActivity {
    private DatabaseReference dataRef;
    private String registeredEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        registeredEmail = getIntent().getStringExtra("num");
        Button btnSave = findViewById(R.id.add);
        TextView name = findViewById(R.id.namereg);
        TextView phno = findViewById(R.id.phnoreg);
        TextView route = findViewById(R.id.routenoreg);
        TextView id = findViewById(R.id.busnoreg);

        dataRef = FirebaseDatabase.getInstance().getReference().child("drivers");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String driver = name.getText().toString().trim();
                String phoneNumber = phno.getText().toString().trim();
                String routeData = route.getText().toString().trim();
                String idData = id.getText().toString().trim();

                saveData(driver, phoneNumber, routeData, idData);
            }
        });
    }

    private void saveData(String driver, String phoneNumber, String routeData, String idData) {
        String newDriverKey = phoneNumber;

        // Create a new child node under "drivers" with the unique key
        DatabaseReference newDriverRef = dataRef.child(newDriverKey);

        // Set the values of "driv", "phno", "id", and "route" under the new child node
        newDriverRef.child("driv").setValue(driver);
        newDriverRef.child("phno").setValue(phoneNumber);
        newDriverRef.child("email").setValue(registeredEmail);
        newDriverRef.child("id").setValue(idData);
        newDriverRef.child("route").setValue(routeData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Data saved successfully
                            Toast.makeText(register2.this, "Account Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(register2.this,driver_dboard.class);
                            startActivity(intent);
                        } else {
                            // Failed to save data
                            Toast.makeText(register2.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
