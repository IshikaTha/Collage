package com.ishika.collageapp.ui.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ishika.collageapp.R;

import java.util.ArrayList;
import java.util.List;

public class FacultyFragment extends Fragment {
    private RecyclerView csDepartment, itDepartment, mDepartment, eeDepartment, ecDepartment, eieDepartment, fDepartment, ashuDepartment;
    private LinearLayout csNoData, itNoData, mNoData, eeNoData, ecNoData, eieNoData, fNoData, ashuNoData;
    private List<TeacherData> list1, list2, list3, list4, list5, list6, list7, list8;
    private DatabaseReference databaseReference, dbRef;

    private TeacherAdapter teacherAdapter;

    ProgressBar progressBar4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);

        progressBar4 = new ProgressBar(getContext());
        progressBar4 = view.findViewById(R.id.progressBar4);

        TextView cs = view.findViewById(R.id.cs);
        TextView it = view.findViewById(R.id.it);
        TextView ee = view.findViewById(R.id.ee);
        TextView eie = view.findViewById(R.id.eie);
        TextView ca = view.findViewById(R.id.ca);
        TextView ece = view.findViewById(R.id.ece);
        TextView ft = view.findViewById(R.id.ft);
        TextView ashu = view.findViewById(R.id.ashu);

        cs.setSelected(true);
        it.setSelected(true);
        ee.setSelected(true);
        eie.setSelected(true);
        ca.setSelected(true);
        ece.setSelected(true);
        ft.setSelected(true);
        ashu.setSelected(true);

        csDepartment = view.findViewById(R.id.csDepartment);
        itDepartment = view.findViewById(R.id.itDepartment);
        mDepartment = view.findViewById(R.id.caDepartment);
        eeDepartment = view.findViewById(R.id.eeDepartment);
        ecDepartment = view.findViewById(R.id.ecDepartment);
        eieDepartment = view.findViewById(R.id.eieDepartment);
        fDepartment = view.findViewById(R.id.fDepartment);
        ashuDepartment = view.findViewById(R.id.ashuDepartment);

        csNoData = view.findViewById(R.id.csNoData);
        itNoData = view.findViewById(R.id.itNoData);
        mNoData = view.findViewById(R.id.caNoData);
        eeNoData = view.findViewById(R.id.eeNoData);
        ecNoData = view.findViewById(R.id.ecNoData);
        eieNoData = view.findViewById(R.id.eieNoData);
        fNoData = view.findViewById(R.id.fNoData);
        ashuNoData = view.findViewById(R.id.ashuNoData);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        csDepartment();
        itDepartment();
        caDepartment();
        eeDepartment();
        ecDepartment();
        eieDepartment();
        fDepartment();
        ashuDepartment();

        return view;
    }

    private void ashuDepartment() {
        databaseReference.child("Applied Science and Humanities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list8 = new ArrayList<>();
                if (!snapshot.exists()) {
                    ashuNoData.setVisibility(View.VISIBLE);
                    ashuDepartment.setVisibility(View.GONE);
                } else {
                    ashuNoData.setVisibility(View.GONE);
                    ashuDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData teacherData = dataSnapshot.getValue(TeacherData.class);
                        list8.add(0,teacherData);
                    }
                    ashuDepartment.setHasFixedSize(true);
                    ashuDepartment.setLayoutManager(new LinearLayoutManager(getContext()));

                    teacherAdapter = new TeacherAdapter(list8, getContext());
                    teacherAdapter.notifyDataSetChanged();
                    progressBar4.setVisibility(View.GONE);
                    ashuDepartment.setAdapter(teacherAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar4.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void csDepartment() {
        databaseReference.child("Computer Science and Engineering").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()) {
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                } else {
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData teacherData = dataSnapshot.getValue(TeacherData.class);
                        list1.add(0,teacherData);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(getContext()));

                    teacherAdapter = new TeacherAdapter(list1, getContext());
                    teacherAdapter.notifyDataSetChanged();
                    progressBar4.setVisibility(View.GONE);
                    csDepartment.setAdapter(teacherAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar4.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void itDepartment() {
        databaseReference.child("Information Technology").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if (!snapshot.exists()) {
                    itNoData.setVisibility(View.VISIBLE);
                    itDepartment.setVisibility(View.GONE);
                } else {
                    itNoData.setVisibility(View.GONE);
                    itDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData teacherData = dataSnapshot.getValue(TeacherData.class);
                        list2.add(0,teacherData);
                    }
                    itDepartment.setHasFixedSize(true);
                    itDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    teacherAdapter = new TeacherAdapter(list2, getContext());
                    progressBar4.setVisibility(View.GONE);
                    itDepartment.setAdapter(teacherAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar4.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void caDepartment() {
        databaseReference.child("Computer Application").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if (!snapshot.exists()) {
                    mNoData.setVisibility(View.VISIBLE);
                    mDepartment.setVisibility(View.GONE);
                } else {
                    mNoData.setVisibility(View.GONE);
                    mDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData teacherData = dataSnapshot.getValue(TeacherData.class);
                        list3.add(0,teacherData);
                    }
                    mDepartment.setHasFixedSize(true);
                    mDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    teacherAdapter = new TeacherAdapter(list3, getContext());
                    progressBar4.setVisibility(View.GONE);
                    mDepartment.setAdapter(teacherAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar4.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eeDepartment() {
        databaseReference.child("Electrical Engineering").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if (!snapshot.exists()) {
                    eeNoData.setVisibility(View.VISIBLE);
                    eeDepartment.setVisibility(View.GONE);
                } else {
                    eeNoData.setVisibility(View.GONE);
                    eeDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData teacherData = dataSnapshot.getValue(TeacherData.class);
                        list4.add(0,teacherData);
                    }
                    eeDepartment.setHasFixedSize(true);
                    eeDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    teacherAdapter = new TeacherAdapter(list4, getContext());
                    progressBar4.setVisibility(View.GONE);
                    eeDepartment.setAdapter(teacherAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar4.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ecDepartment() {
        databaseReference.child("Electronic and Communication Engineering").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list5 = new ArrayList<>();
                if (!snapshot.exists()) {
                    ecNoData.setVisibility(View.VISIBLE);
                    ecDepartment.setVisibility(View.GONE);
                } else {
                    ecNoData.setVisibility(View.GONE);
                    ecDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData teacherData = dataSnapshot.getValue(TeacherData.class);
                        list5.add(0,teacherData);
                    }
                    ecDepartment.setHasFixedSize(true);
                    ecDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    teacherAdapter = new TeacherAdapter(list5, getContext());
                    progressBar4.setVisibility(View.GONE);
                    ecDepartment.setAdapter(teacherAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar4.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eieDepartment() {
        databaseReference.child("Electronics and Instrumentation Engineering").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list6 = new ArrayList<>();
                if (!snapshot.exists()) {
                    eieNoData.setVisibility(View.VISIBLE);
                    eieDepartment.setVisibility(View.GONE);
                } else {
                    eieNoData.setVisibility(View.GONE);
                    eieDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData teacherData = dataSnapshot.getValue(TeacherData.class);
                        list6.add(0,teacherData);
                    }
                    eieDepartment.setHasFixedSize(true);
                    eieDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    teacherAdapter = new TeacherAdapter(list6, getContext());
                    progressBar4.setVisibility(View.GONE);
                    eieDepartment.setAdapter(teacherAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar4.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fDepartment() {
        databaseReference.child("Food Technology").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list7 = new ArrayList<>();
                if (!snapshot.exists()) {
                    fNoData.setVisibility(View.VISIBLE);
                    fDepartment.setVisibility(View.GONE);
                } else {
                    fNoData.setVisibility(View.GONE);
                    fDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherData teacherData = dataSnapshot.getValue(TeacherData.class);
                        list7.add(0,teacherData);
                    }
                    fDepartment.setHasFixedSize(true);
                    fDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    teacherAdapter = new TeacherAdapter(list7, getContext());
                    progressBar4.setVisibility(View.GONE);
                    fDepartment.setAdapter(teacherAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar4.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}