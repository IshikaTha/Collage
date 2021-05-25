package com.ishika.collageapp.eBook;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ishika.collageapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PdfListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PdfAdapter pdfAdapter;
    private LinearLayout noData, linearLayout;
    private List<PdfData> list;
    private DatabaseReference databaseReference;
    private String batch, stream;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout shimmerLL;
    private EditText searchPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_list);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerLL = findViewById(R.id.shimmerLL);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Your e-Books");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Pdf");

        noData = findViewById(R.id.noData);
        linearLayout = findViewById(R.id.linearLayout);

        searchPdf = findViewById(R.id.searchPdf);

        recyclerView = findViewById(R.id.recyclerview);
        stream = getIntent().getStringExtra("stream");
        batch = getIntent().getStringExtra("year");
        try {
            showPdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPdf() {
        databaseReference.child(batch).child(stream).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list = new ArrayList<>();
                if(!snapshot.exists()) {
                    noData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else{
                    noData.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PdfData pdfData = dataSnapshot.getValue(PdfData.class);
                        list.add(0,pdfData);
                    }
                }
                pdfAdapter = new PdfAdapter(PdfListActivity.this, list, batch, stream);
                recyclerView.setLayoutManager(new LinearLayoutManager(PdfListActivity.this));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(pdfAdapter);
                shimmerFrameLayout.stopShimmer();
                shimmerLL.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PdfListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        searchPdf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<PdfData> filterList = new ArrayList<>();
        for(PdfData pdfData: list){
            if(pdfData.getTitle().toLowerCase().contains(text.toLowerCase())){
                filterList.add(pdfData);
            }
        }
        pdfAdapter.FilteredList(filterList);
    }


    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }

    public boolean onSupportNavigateUp () {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}