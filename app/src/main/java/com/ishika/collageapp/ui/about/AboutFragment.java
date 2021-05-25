package com.ishika.collageapp.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ishika.collageapp.R;

import java.util.ArrayList;


public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        ArrayList<courseModel> list = new ArrayList<>();

        list.add(new courseModel(R.drawable.ic_coding, "Computer Science and Engineering", "The Department offers an under-graduate program (B. Tech) in Computer science and Engineering. Department of CSE is committed to impart Technical and Research based quality education and to develop innovative skills among the students and to enrich the academic activities through continual improvement in the teaching and learning processes."));

        list.add(new courseModel(R.drawable.ic_car_engine, "Electronics and Instrumentation Engineering", "It has been increasing steadily over the years due the high standards of teaching and presently the annual intake is 120 students."));

        list.add(new courseModel(R.drawable.ic_developer, "Information Technology", "The Department provides an outstanding research environment with the aid of qualified faculty.  Besides, theoretical research, faculty in the department also network with many reputed national and international societies and are involved in a number of projects in the areas of cutting edge technologies."));

        list.add(new courseModel(R.drawable.ic_engineering, "Electrical Department", " The Department is fully equipped with a state-of-art laboratories with latest equipment, advanced versions of software and demo units. The department has rich experienced and dedicated faculty with the background of Industry, Teaching and Research."));

        list.add(new courseModel(R.drawable.ic_engine, "Electronics and Communication", "The Department has good infrastructural facilities, full-fledged laboratories with well equipped hardware and software which improves the practical working competency in the students. The department aims at educating and training students with sound knowledge and awareness in the latest trends in Electronics and Communication Engineering."));

        list.add(new courseModel(R.drawable.ic_food_blogger, "Food Technology", "The laboratories are developed in consultation with the Industry and Academic experts to provide industrial environment along with the University curriculum. The department has rich experienced and dedicated faculty with the background of Industry, Teaching and Research."));

        list.add(new courseModel(R.drawable.ic_dna, "Applied Science and Humanities", "ASHU Department compraises of Applied Mathematics, Applied Physics, Applied Chemistry and Applied Environmental Studies. The well-equipped laboratories and rich curriculum provide ample opportunities to budding engineers to have hands-on experience of laws and facts of science and develop and inculcate scientific attitude among them."));

        list.add(new courseModel(R.drawable.ic_desktop, "ComputerApplication", "The prime objective of the course is to advance the students with a Computer Applications"));

        BranchAdapter branchAdapter = new BranchAdapter(getContext(), list);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(branchAdapter);

        ImageView collImg = view.findViewById(R.id.collageImage);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/gnit-c38ad.appspot.com/o/IMG-20180514-0154.png?alt=media&token=9b6ab671-851e-4a23-80e9-743c03762361").into(collImg);

        ImageView map = view.findViewById(R.id.map);
        map.setOnClickListener(v -> openMap());
        return view;
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0, 0?q=Guru Nanak Institute of Technology, Sodepur");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}