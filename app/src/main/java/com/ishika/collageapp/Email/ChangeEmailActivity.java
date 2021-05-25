package com.ishika.collageapp.Email;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ishika.collageapp.R;

import java.util.HashMap;
import java.util.Objects;

public class ChangeEmailActivity extends AppCompatActivity {
    private EditText newEmail, newPassword, confirmPassword;
    private Button changeEmail;
    private String email, newPass, confirmPass;

    private ProgressBar editEmailProgress;

    private String userId;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        Objects.requireNonNull(getSupportActionBar()).hide();

        editEmailProgress = new ProgressBar(this);
        editEmailProgress = findViewById(R.id.editEmailProgress);
        newPassword = findViewById(R.id.newPass);
        confirmPassword = findViewById(R.id.confirmPass);

        editEmailProgress = findViewById(R.id.editEmailProgress);
        editEmailProgress.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        userId = firebaseUser.getUid();

        newEmail = findViewById(R.id.newEmail);
        changeEmail= findViewById(R.id.changeEmail);
        email = getIntent().getStringExtra("email");
        newEmail.setText(email);

        changeEmail.setOnClickListener(v -> {
            editEmailProgress.setVisibility(View.VISIBLE);
            email = newEmail.getText().toString();
            newPass = newPassword.getText().toString();
            confirmPass = confirmPassword.getText().toString();
            if(email.isEmpty()){
                changeEmail.setError("Email required");
                changeEmail.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                changeEmail.setError("Enter valid email");
                changeEmail.requestFocus();
            }

            if((newPass.isEmpty() && confirmPass.isEmpty())){
                firebaseUser.updateEmail(email).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(ChangeEmailActivity.this, "Email updated", Toast.LENGTH_SHORT).show();
                        updateData();
                        auth.signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }else {
                        Toast.makeText(ChangeEmailActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                if(newPass.equals(confirmPass)){
                    firebaseUser.updateEmail(email).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){

                        }else {
                            Toast.makeText(ChangeEmailActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

                    firebaseUser.updatePassword(newPass).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(ChangeEmailActivity.this, "Email and Password updated", Toast.LENGTH_SHORT).show();
                            updateData();
                            auth.signOut();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }else {
                            Toast.makeText(ChangeEmailActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    editEmailProgress.setVisibility(View.GONE);
                }else{
                    editEmailProgress.setVisibility(View.GONE);
                    Toast.makeText(ChangeEmailActivity.this, "OOPs! New Password and Confirm Password re not identical. Please provide identical Passwords", Toast.LENGTH_SHORT).show();
                    newPassword.setText("");
                    confirmPassword.setText("");
                }
            }
        });
    }

    private void updateData() {
        HashMap hashMap = new HashMap();
        hashMap.put("email", email);
        hashMap.put("password", newPass);

        databaseReference.child(userId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                sharedPreferences = getSharedPreferences("User details", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show());
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}