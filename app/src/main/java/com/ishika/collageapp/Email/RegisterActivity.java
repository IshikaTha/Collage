package com.ishika.collageapp.Email;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ishika.collageapp.MainActivity;
import com.ishika.collageapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView name, email, pass;
    private String ycategory, scategory;

    Button btnRegister;
    private ImageView pImg;
    private final int req = 26;
    private Bitmap bitmap = null;
    StorageReference storageReference;
    private ProgressDialog progressDialog;
    private String downUrl = "";

    private Spinner yearCategory, streamCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Objects.requireNonNull(getSupportActionBar()).hide();

        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.name);
        email = findViewById(R.id.editEmail);
        pass = findViewById(R.id.pass);
        pImg = findViewById(R.id.pImg);
        yearCategory = findViewById(R.id.yearCategory);
        streamCategory = findViewById(R.id.streamCategory);
        btnRegister = findViewById(R.id.registerbtn);

        progressDialog = new ProgressDialog(this);

        storageReference = FirebaseStorage.getInstance().getReference();

        String[] years = new String[]{"Select Course", "BTech 1st year", "BTech 2nd year", "BTech 3rd year", "BTech 4th year"};

        yearCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years));
        yearCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ycategory = yearCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String[] streams = new String[]{"Select Category", "CSE", "IT", "EE", "EIE", "CA", "ECE", "FT"};

        streamCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, streams));
        streamCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scategory = streamCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        pImg.setOnClickListener(v -> chooseFromGallery());

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String Email = email.getText().toString().trim();
        String Password = pass.getText().toString().trim();
        String FullName = name.getText().toString().trim();

        if(FullName.isEmpty()){
            name.setError("Full name is required");
            name.requestFocus();
            return;
        }

        if(Email.isEmpty()){
            email.setError("Email required");
            email.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            pass.setError("Please set password");
            pass.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("Enter valid email");
            email.requestFocus();
        }

        if(Password.length()<6){
            pass.setError("Min password length is 6");
            pass.requestFocus();
            return;
        }if(ycategory.equals("Select Category")){
            Toast.makeText(RegisterActivity.this, "Please select course you are opting for", Toast.LENGTH_SHORT).show();
            return;
        }if(scategory.equals("Select Category")){
            Toast.makeText(RegisterActivity.this, "Please provide departmental category", Toast.LENGTH_SHORT).show();
            return;
        }
        if(bitmap != null){
            progressDialog.show();
            uploadImage();
        }else {
            progressDialog.show();
            register(FullName, Email, Password);
        }
    }
    private void register(String FullName, String Email, String Password){
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = new User(FullName, Email, downUrl, ycategory, scategory);
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                        .setValue(user).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
            } else {
                Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void uploadImage(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] finalImg = byteArrayOutputStream.toByteArray();
        final StorageReference storagePath1;
        storagePath1 = storageReference.child("Users").child(finalImg + "jpg");
        final UploadTask uploadTask = storagePath1.putBytes(finalImg);

        uploadTask.addOnCompleteListener(RegisterActivity.this, task -> {
            if(task.isSuccessful()){
                uploadTask.addOnSuccessListener(taskSnapshot -> storagePath1.getDownloadUrl().addOnSuccessListener(uri -> {
                    downUrl = String.valueOf(uri);
                    progressDialog.show();
                    String Email = email.getText().toString().trim();
                    String Password = pass.getText().toString().trim();
                    String FullName = name.getText().toString().trim();
                    register(FullName, Email, Password);
                }));
            }else {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Upload Image Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // compare the resultCode with the SELECT_PICTURE constant
        if(requestCode == req && resultCode == RESULT_OK){

            // Get the url of the image from data
            assert data != null;
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            }catch (IOException e){
                e.printStackTrace();
            }
            pImg.setImageBitmap(bitmap);
        }
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}