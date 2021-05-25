package com.ishika.collageapp.Email;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ishika.collageapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editName;
    private TextView editEmail;
    private ImageView editImage;
    private String name, email, image, userId;
    private final int req = 20;
    Bitmap bitmap = null;
    private String downUrl ="";
    private String ycategory, scategory;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private Spinner yearCategory, streamCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Objects.requireNonNull(getSupportActionBar()).hide();

        editImage = findViewById(R.id.editImg);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        Button update = findViewById(R.id.update);
        TextView resetPass = findViewById(R.id.resetPass);

        progressDialog = new ProgressDialog(this);
        ProgressBar editProfileProgress = findViewById(R.id.editProfileProgress);
        yearCategory = findViewById(R.id.editYear);
        streamCategory = findViewById(R.id.editStream);


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


        FirebaseAuth auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("Users");

        FirebaseUser firebaseUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);

        assert firebaseUser != null;
        userId = firebaseUser.getUid();

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        image = getIntent().getStringExtra("image");
        ycategory = getIntent().getStringExtra("year");
        scategory = getIntent().getStringExtra("stream");

        editName.setText(name);
        editEmail.setText(email);
        Glide.with(this).load(image).into(editImage);
        editProfileProgress.setVisibility(View.GONE);

        editImage.setOnClickListener(v -> chooseFromGallery());

        editEmail.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChangeEmailActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });
        update.setOnClickListener(v -> {
            name = editName.getText().toString();
            email = editEmail.getText().toString();
            if(name.isEmpty()){
                editName.setError("Please provide username");
                editName.requestFocus();
            }else if(ycategory.equals("Select Category")){
                Toast.makeText(EditProfileActivity.this, "Please select course you are opting for", Toast.LENGTH_SHORT).show();
            }else if(scategory.equals("Select Category")){
                Toast.makeText(EditProfileActivity.this, "Please provide departmental category", Toast.LENGTH_SHORT).show();
            }
            else if(bitmap == null){
                updateData(image);
            }else {
                uploadImage();
            }
        });
        resetPass.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ResetPassword.class)));
    }

    private void updateData(String s) {
        HashMap hashMap = new HashMap();
        hashMap.put("photo", s);
        hashMap.put("name", name);
        hashMap.put("email", email);
        hashMap.put("year", ycategory);
        hashMap.put("stream", scategory);

        databaseReference.child(userId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void uploadImage(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] finalImg = byteArrayOutputStream.toByteArray();
        final StorageReference storagePath1;
        storagePath1 = storageReference.child(finalImg + "jpg");
        final UploadTask uploadTask = storagePath1.putBytes(finalImg);

        uploadTask.addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                uploadTask.addOnSuccessListener(taskSnapshot -> storagePath1.getDownloadUrl().addOnSuccessListener(uri -> {
                    downUrl = String.valueOf(uri);
                    updateData(downUrl);
                }));
            }else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
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
        if(requestCode == req && resultCode == RESULT_OK){

            assert data != null;
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            }catch (IOException e){
                e.printStackTrace();
            }
            editImage.setImageBitmap(bitmap);
        }
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}