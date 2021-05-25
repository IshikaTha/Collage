package com.ishika.collageapp.Email;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ishika.collageapp.R;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    String name, email, photo, year, stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Objects.requireNonNull(getSupportActionBar()).hide();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.keepSynced(true);
        assert firebaseUser != null;
        String userId = firebaseUser.getUid();
        TextView greetings = findViewById(R.id.greetings);
        TextView email1 = findViewById(R.id.editEmail);
        TextView pName = findViewById(R.id.yourName);
        ImageView pImage = findViewById(R.id.profileImg);
        Button edit = findViewById(R.id.edit);
        TextView yourStream = findViewById(R.id.yourStream);
        TextView yourYear = findViewById(R.id.yourYear);

        ProgressBar profileProgressbar = findViewById(R.id.profileProgress);
        DatabaseReference dbRef = databaseReference.child(userId);
        dbRef.keepSynced(true);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    name = userProfile.getName();
                    email = userProfile.getEmail();
                    photo = userProfile.getPhoto();
                    year = userProfile.getYear();
                    stream = userProfile.getStream();
                    String welcome = "Welcome, " + name + "!";
                    greetings.setText(welcome);
                    pName.setText(name);
                    email1.setText(email);
                    yourStream.setText(stream);
                    yourYear.setText(year);
                    try {
                        Glide.with(ProfileActivity.this).load(userProfile.getPhoto()).into(pImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                profileProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("image", photo);
            intent.putExtra("email", email);
            intent.putExtra("year", year);
            intent.putExtra("stream", stream);
            startActivity(intent);
        });
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}