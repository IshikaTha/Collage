package com.ishika.collageapp.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ishika.collageapp.R;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {

    private RecyclerView notRecyclerview;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeAdapter noticeAdapter;
    private DatabaseReference databaseReference;
    SwipeRefreshLayout noticeSwipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        notRecyclerview = view.findViewById(R.id.upNotRecyclerview);

        progressBar = new ProgressBar(getContext());
        progressBar = view.findViewById(R.id.progressBar);
        noticeSwipe = view.findViewById(R.id.noticeSwipe);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notice");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        notRecyclerview.setLayoutManager(linearLayoutManager);
        notRecyclerview.setHasFixedSize(true);
        getNotice();
        noticeSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNotice();
                noticeSwipe.setRefreshing(false);
            }
        });
        return view;
    }

    private void getNotice() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NoticeData noticeData = dataSnapshot.getValue(NoticeData.class);
                    list.add(0, noticeData);
                }
                noticeAdapter = new NoticeAdapter(getContext(), list);
                noticeAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                notRecyclerview.setAdapter(noticeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
