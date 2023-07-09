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

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerbtn = findViewById(R.id.add);
        EditText phno = findViewById(R.id.emaillog);
        EditText pw1 = findViewById(R.id.pw1);
        EditText pw2 = findViewById(R.id.pw2);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();




    registerbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String password;
            String num = String.valueOf(phno.getText());
            password = String.valueOf(pw1.getText());

            if (TextUtils.isEmpty(num)) {
                Toast.makeText(register.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(register.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            String password1 = String.valueOf(pw2.getText());

            if (password.equals(password1)) {
                mAuth.createUserWithEmailAndPassword(num, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(register.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();
//                                    String uID = mAuth.getCurrentUser();
                                    Intent intent = new Intent(register.this, register2.class);
                                    intent.putExtra("num",num);
                                    startActivity(intent);


                                } else {

                                    Toast.makeText(register.this, "Account could not be created.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }

            else {
                Toast.makeText(register.this, "Please Enter Matching Passwords", Toast.LENGTH_SHORT).show();
                return;
            }

            }
    });
    }
}