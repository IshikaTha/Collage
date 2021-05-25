package com.ishika.collageapp.Email;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.ishika.collageapp.R;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText forgotEmail;
    private ProgressBar resetProgress;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Objects.requireNonNull(getSupportActionBar()).hide();

        forgotEmail = findViewById(R.id.forgotEmail);
        Button reset = findViewById(R.id.reset);
        resetProgress = findViewById(R.id.resetProgress);
        resetProgress.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();

        reset.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        String email = forgotEmail.getText().toString().trim();
        if(email.isEmpty()){
            forgotEmail.setError("Email is required");
            forgotEmail.requestFocus();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgotEmail.setError("Please provide a valide email");
            forgotEmail.requestFocus();
        }
        resetProgress.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset password", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(ForgotPasswordActivity.this, "Try again! something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}