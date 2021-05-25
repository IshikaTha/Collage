package com.ishika.collageapp.Email;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ishika.collageapp.MainActivity;
import com.ishika.collageapp.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email, pawd;
    private ProgressBar progressBar1;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView forgot = findViewById(R.id.forgot);
        forgot.setOnClickListener(this);

        TextView register = findViewById(R.id.register);
        register.setOnClickListener(this);

        Button login = findViewById(R.id.login);
        login.setOnClickListener(this);

        email = findViewById(R.id.editTextTextEmailAddress);
        pawd = findViewById(R.id.editTextTextPassword);

        progressBar1 = findViewById(R.id.progressBar1);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
            if(v.getId() == R.id.register){
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
            }
            if(v.getId() == R.id.login)
                userLogin();
            if(v.getId() == R.id.forgot)
                startActivity(new Intent(this, ForgotPasswordActivity.class));
        }

    private void userLogin() {
        String Email = email.getText().toString().trim();
        String password = pawd.getText().toString().trim();

        if(Email.isEmpty()){
            email.setError("Email required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("Enter valid email");
            email.requestFocus();
            return;
        }

        if(password.isEmpty()){
            pawd.setError("Please set password");
            pawd.requestFocus();
            return;
        }

        if(password.length()<6){
            pawd.setError("Min length should be 6");
            pawd.requestFocus();
            return;
        }

        progressBar1.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                progressBar1.setVisibility(View.GONE);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
//                if(user.isEmailVerified()){
//                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
//                    progressBar1.setVisibility(View.GONE);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                }
//                else {
//                    user.sendEmailVerification();
//                    Toast.makeText(LoginActivity.this, "Check your e-mail to verify your account", Toast.LENGTH_SHORT).show();
//                }

                // redirect to user profile
            }else {
                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                progressBar1.setVisibility(View.GONE);
            }
        });
    }
}