package com.ishika.collageapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ishika.collageapp.Email.LoginActivity;
import com.ishika.collageapp.Email.ProfileActivity;
import com.ishika.collageapp.Email.User;
import com.ishika.collageapp.eBook.PdfListActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkedItem;
    private String selected;
    private final String CHECKEDITEM ="checked_item";
    private FirebaseAuth auth;
    private long backPressed;
    private Toast backToast;
    String year, stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        assert firebaseUser != null;
        String userId = firebaseUser.getUid();

        sharedPreferences = this.getSharedPreferences("themes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        switch (getCheckedItem()){
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }

        if(!isConnected(MainActivity.this)){
            buildDialog(MainActivity.this).show();
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    year = userProfile.getYear();
                    stream = userProfile.getStream();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean isConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null && info.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return mobile != null && mobile.isConnectedOrConnecting() || wifi != null && wifi.isConnectedOrConnecting();
        }else
            return false;
    }


    public AlertDialog.Builder buildDialog(Context context){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You are not connected to internet");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> finish());
        builder.setNegativeButton("Cancel", (dialog, which) -> builder.setCancelable(true));
        return builder;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.navigation_eBook) {
            Intent intent = new Intent(this, PdfListActivity.class);
            intent.putExtra("year", year);
            intent.putExtra("stream", stream);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.navigation_settings)
           startActivity(new Intent(this, ProfileActivity.class));

        if(item.getItemId() == R.id.navigation_website){
            gotoUrl("http://gnit.ac.in/");
        }

        if(item.getItemId() == R.id.navigation_log_out) {
            LogOut();
        }
        if(item.getItemId() == R.id.navigation_color)
            showDialog();
        return true;
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri) );
    }

    private void LogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure, you want to log out?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            auth.signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = null;
        try {
            alertDialog = builder.create();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(alertDialog != null)
            alertDialog.show();
    }


    private void showDialog() {
        String[] themes = this.getResources().getStringArray(R.array.theme);
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setTitle("Select Theme");
        materialAlertDialogBuilder.setSingleChoiceItems(R.array.theme, getCheckedItem(), (dialog, which) -> {
            selected = themes[which];
            checkedItem = which;
        });
        materialAlertDialogBuilder.setPositiveButton("OK", (dialog, which) -> {
            if(selected == null){
                selected = themes[which];
                checkedItem = which;
            }
            switch (checkedItem){
                case 0:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    break;
                case 1:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                case 2:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
            }
            setCheckedItem(checkedItem);
        });
        materialAlertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = materialAlertDialogBuilder.create();
        alertDialog.show();
    }

    private int getCheckedItem(){
        return sharedPreferences.getInt(CHECKEDITEM,0);
    }

    private void setCheckedItem(int i){
        editor.putInt(CHECKEDITEM, i);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(backPressed + 3000 > System.currentTimeMillis()) {
            super.onBackPressed();
            backToast.cancel();
            return;
        }else {
            backToast = Toast.makeText(getApplicationContext(), "press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressed = System.currentTimeMillis();
    }
}