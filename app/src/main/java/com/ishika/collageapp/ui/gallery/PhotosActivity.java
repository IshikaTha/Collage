package com.ishika.collageapp.ui.gallery;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ishika.collageapp.R;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView pRecyclerview;
    private GalleryAdapter galleryAdapter;
    private ShimmerFrameLayout shimmerFrameLayout1;
    private LinearLayout shimmerL, t1;
    private String event;
    private LinearLayout noData;
    private TextView eventHead;
    SwipeRefreshLayout photosSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        shimmerFrameLayout1 = findViewById(R.id.shimmer_view_container1);
        shimmerL = findViewById(R.id.shimmerL);

        eventHead = findViewById(R.id.eventHead);

        event = getIntent().getStringExtra("events");
        eventHead.setText(event);
        t1 = findViewById(R.id.t1);
        noData = findViewById(R.id.noData1);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Gallery");

        photosSwipe = findViewById(R.id.photosSwipe);
        pRecyclerview = findViewById(R.id.pRecyclerview);
        getPhotos();

        photosSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPhotos();
                photosSwipe.setRefreshing(false);
            }
        });
    }

    private void getPhotos() {
        databaseReference.child(event).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<GalleryData> list = new ArrayList<>();
                if(!snapshot.exists()) {
                    noData.setVisibility(View.VISIBLE);
                    pRecyclerview.setVisibility(View.GONE);
                }else {
                    noData.setVisibility(View.GONE);
                    pRecyclerview.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        GalleryData galleryData = dataSnapshot.getValue(GalleryData.class);
                        list.add(0, galleryData);
                    }
                }
                galleryAdapter = new GalleryAdapter(PhotosActivity.this, list);
                galleryAdapter.notifyDataSetChanged();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(PhotosActivity.this, 4);
                pRecyclerview.setLayoutManager(gridLayoutManager);
                pRecyclerview.setHasFixedSize(true);
                pRecyclerview.setAdapter(galleryAdapter);
                shimmerFrameLayout1.stopShimmer();
                shimmerL.setVisibility(View.GONE);
                t1.setVisibility(View.VISIBLE);
                eventHead.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PhotosActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout1.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout1.startShimmer();
        super.onResume();
    }

    public boolean onSupportNavigateUp () {
            onBackPressed();
            return super.onSupportNavigateUp();
    }
}