package com.cardflip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegistered;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewRegistered;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){// if user already logged in
            //start profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), LandingPage.class));
        }
        progressDialog = new ProgressDialog(this);
        buttonRegistered = (Button) findViewById(R.id.register);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewRegistered = (TextView) findViewById(R.id.registered);
        buttonRegistered.setOnClickListener(this);
        textViewRegistered.setOnClickListener(this);
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter your email id", Toast.LENGTH_SHORT).show();
            //stopping teh function from executing further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            //stopping teh function from executing further
            return;
        }

        progressDialog.setMessage("Registering");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    // user registered
                    Toast.makeText(RegistrationActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), LandingPage.class));
                }
                else {
                    Toast.makeText(RegistrationActivity.this, "Registration failed." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegistered){
            registerUser();
        }
        if(v == textViewRegistered){
            // open login activity
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
