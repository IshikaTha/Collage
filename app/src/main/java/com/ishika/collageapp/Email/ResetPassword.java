package com.ishika.collageapp.Email;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ishika.collageapp.R;

import java.util.Objects;

public class ResetPassword extends AppCompatActivity {

    private EditText newPassword, confirmPassword;
    private String newPass, confirmPass;

    private ProgressBar resetPassProgress;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Objects.requireNonNull(getSupportActionBar()).hide();
        resetPassProgress = new ProgressBar(this);
        resetPassProgress = findViewById(R.id.resetPassProgress);
        newPassword = findViewById(R.id.newPass);
        confirmPassword = findViewById(R.id.confirmPass);
        Button changePass = findViewById(R.id.changePass);

        resetPassProgress = findViewById(R.id.resetPassProgress);
        resetPassProgress.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        changePass.setOnClickListener(v -> {
            resetPassProgress.setVisibility(View.VISIBLE);
            newPass = newPassword.getText().toString();
            confirmPass = confirmPassword.getText().toString();
            if(newPass.equals(confirmPass)){
                firebaseUser.updatePassword(newPass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(ResetPassword.this, "Password changed", Toast.LENGTH_SHORT).show();
                        auth.signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }else {
                        Toast.makeText(ResetPassword.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                resetPassProgress.setVisibility(View.GONE);
            }else{
                resetPassProgress.setVisibility(View.GONE);
                Toast.makeText(ResetPassword.this, "OOPs! New Password and Confirm Password re not identical. Please provide identical Passwords", Toast.LENGTH_SHORT).show();
                newPassword.setText("");
                confirmPassword.setText("");
            }
        });
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
