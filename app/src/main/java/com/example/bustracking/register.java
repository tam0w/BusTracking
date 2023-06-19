package com.example.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerbtn = findViewById(R.id.reg);
        EditText phno = findViewById(R.id.phnoreg);
        EditText pw1 = findViewById(R.id.pw1);
        EditText pw2 = findViewById(R.id.pw2);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();




    registerbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email, password;
            String num = String.valueOf(phno.getText());
            num = num.trim();
            password = String.valueOf(pw1.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(register.this, "Please Enter Number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(register.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            if ( password == String.valueOf(pw2.getText());){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(register.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }

            else {
                Toast.makeText(activity_register.this, "Please Enter Matching Passwords", Toast.LENGTH_SHORT).show();
                return;
            }

            }
    }