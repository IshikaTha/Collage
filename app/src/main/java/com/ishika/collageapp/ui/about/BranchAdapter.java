package com.ishika.collageapp.ui.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ishika.collageapp.R;

import java.util.List;

public class BranchAdapter extends PagerAdapter {
    private final Context context;
    private final List<courseModel> list;

    public BranchAdapter(Context context, List<courseModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_item_layout, container, false);
        ImageView courseIcon;
        TextView courseHeading, courseDesc;

        courseIcon = view.findViewById(R.id.courseIcon);
        courseHeading = view.findViewById(R.id.courseHeading);
        courseDesc = view.findViewById(R.id.courseDesc);

        courseIcon.setImageResource(list.get(position).getImg());
        courseHeading.setText(list.get(position).getTitle());
        courseDesc.setText(list.get(position).getDescription());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
