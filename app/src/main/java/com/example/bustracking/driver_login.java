package com.example.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class driver_login extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        Button loginbtn = findViewById(R.id.add);
        EditText pw = findViewById(R.id.pw1);
        EditText email = findViewById(R.id.emaillog);
        mAuth = FirebaseAuth.getInstance();
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = String.valueOf(email.getText());
                String pw1 = String.valueOf(pw.getText());

                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(driver_login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pw1)) {
                    Toast.makeText(driver_login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email1, pw1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(driver_login.this, "Successfully Signed In.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(driver_login.this, driver_dboard.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(driver_login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }

}