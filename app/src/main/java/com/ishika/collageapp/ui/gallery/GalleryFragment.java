package com.ishika.collageapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ishika.collageapp.R;

import java.util.ArrayList;

public class GalleryFragment extends Fragment implements View.OnClickListener {
    private ImageView convoCard, sihCard, icmCard, visCard, indeCard, repCard, othCard;
    private DatabaseReference databaseReference;

    private LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7;
    SwipeRefreshLayout swipeGallery;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        convoCard = view.findViewById(R.id.convoCard);
        sihCard = view.findViewById(R.id.sihCard);
        icmCard = view.findViewById(R.id.icmCard);
        visCard = view.findViewById(R.id.visCard);
        indeCard = view.findViewById(R.id.indeCard);
        repCard = view.findViewById(R.id.repCard);
        othCard = view.findViewById(R.id.othCard);

        ll1 = view.findViewById(R.id.ll1);
        ll2 = view.findViewById(R.id.ll2);
        ll3 = view.findViewById(R.id.ll3);
        ll4 = view.findViewById(R.id.ll4);
        ll5 = view.findViewById(R.id.ll5);
        ll6 = view.findViewById(R.id.ll6);
        ll7 = view.findViewById(R.id.ll7);

        swipeGallery = view.findViewById(R.id.swipeGallery);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Gallery");

        getConvoPhotos();
        getSIHPhotos();
        getIcmPhotos();
        getVisPhotos();
        getIndePhotos();
        getOthPhotos();
        getRepPhotos();

        convoCard.setOnClickListener(this);
        sihCard.setOnClickListener(this);
        icmCard.setOnClickListener(this);
        visCard.setOnClickListener(this);
        indeCard.setOnClickListener(this);
        repCard.setOnClickListener(this);
        othCard.setOnClickListener(this);

        swipeGallery.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getConvoPhotos();
                getSIHPhotos();
                getIcmPhotos();
                getVisPhotos();
                getIndePhotos();
                getOthPhotos();
                getRepPhotos();
                swipeGallery.setRefreshing(false);
            }
        });
        return view;
    }

    private void getConvoPhotos() {
        databaseReference.child("Convocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                ArrayList<GalleryData> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GalleryData galleryData = dataSnapshot.getValue(GalleryData.class);
                    list.add(0, galleryData);
                    count++;
                }
                if(count==0) {
                    ll1.setVisibility(View.GONE);
                }else{
                    String i1 = list.get(0).getImage();
                    try {
                        Glide.with(getContext()).load(i1).into(convoCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSIHPhotos() {
        databaseReference.child("SIH").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                ArrayList<GalleryData> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GalleryData galleryData = dataSnapshot.getValue(GalleryData.class);
                    list.add(0, galleryData);
                    count++;
                }
                if(count==0) {
                    ll2.setVisibility(View.GONE);
                }else{
                    String i1 = list.get(0).getImage();
                    try {
                        Glide.with(getContext()).load(i1).into(sihCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIcmPhotos() {
        databaseReference.child("ICM ICPC").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                ArrayList<GalleryData> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GalleryData galleryData = dataSnapshot.getValue(GalleryData.class);
                    list.add(0, galleryData);
                    count++;
                }
                if(count==0) {
                    ll3.setVisibility(View.GONE);
                }else{
                    String i1 = list.get(0).getImage();
                    try {
                        Glide.with(getContext()).load(i1).into(icmCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getVisPhotos() {
        databaseReference.child("Industrial Visit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                ArrayList<GalleryData> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GalleryData galleryData = dataSnapshot.getValue(GalleryData.class);
                    list.add(0, galleryData);
                    count++;
                }
                if(count==0) {
                    ll4.setVisibility(View.GONE);
                }else{
                    String i1 = list.get(0).getImage();
                    try {
                        Glide.with(getContext()).load(i1).into(visCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIndePhotos() {
        databaseReference.child("Independence Day").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                ArrayList<GalleryData> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GalleryData galleryData = dataSnapshot.getValue(GalleryData.class);
                    list.add(0, galleryData);
                    count++;
                }
                if(count>0) {
                    String i1 = list.get(0).getImage();
                    try {
                        Glide.with(getContext()).load(i1).into(indeCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ll5.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRepPhotos() {
        databaseReference.child("Republic Day").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                ArrayList<GalleryData> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GalleryData galleryData = dataSnapshot.getValue(GalleryData.class);
                    list.add(0, galleryData);
                    count++;
                }
                if(count>0) {
                    String i1 = list.get(0).getImage();
                    try {
                        Glide.with(getContext()).load(i1).into(repCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ll6.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOthPhotos() {
        databaseReference.child("Others").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                ArrayList<GalleryData> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GalleryData galleryData = dataSnapshot.getValue(GalleryData.class);
                    list.add(0, galleryData);
                    count++;
                }
                if(count==0) {
                    ll7.setVisibility(View.GONE);
                }else{
                    String i1 = list.get(0).getImage();
                    try {
                        Glide.with(getContext()).load(i1).into(othCard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.convoCard) {
            Intent i1 = new Intent(getContext(), PhotosActivity.class);
            i1.putExtra("events", "Convocation");
            startActivity(i1);
        }
        if(v.getId() == R.id.sihCard) {
            Intent i2 = new Intent(getContext(), PhotosActivity.class);
            i2.putExtra("events", "SIH");
            startActivity(i2);
        }
        if(v.getId() ==  R.id.icmCard) {
            Intent i3 = new Intent(getContext(), PhotosActivity.class);
            i3.putExtra("events", "ICM ICPC");
            startActivity(i3);
        }
        if(v.getId() ==  R.id.visCard) {
            Intent i4 = new Intent(getContext(), PhotosActivity.class);
            i4.putExtra("events", "Industrial Visit");
            startActivity(i4);
        }
        if(v.getId() == R.id.indeCard) {
            Intent i5 = new Intent(getContext(), PhotosActivity.class);
            i5.putExtra("events", "Independence Day");
            startActivity(i5);
        }
        if(v.getId() ==  R.id.repCard) {
            Intent i6 = new Intent(getContext(), PhotosActivity.class);
            i6.putExtra("events", "Republic Day");
            startActivity(i6);
        }
        if(v.getId() ==  R.id.othCard){
            Intent i7 = new Intent(getContext(), PhotosActivity.class);
            i7.putExtra("events", "Others");
            startActivity(i7);
        }
    }
}