package com.ishika.collageapp.ui.faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ishika.collageapp.R;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.viewHolder> {
    private final List<TeacherData> list;
    private final Context context;

    public TeacherAdapter(List<TeacherData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        //holder.progressBar.setVisibility(View.GONE);
        TeacherData teacherData = list.get(position);

        holder.name.setText(teacherData.getName());
        holder.email.setText(teacherData.getEmail());
        holder.phone.setText(teacherData.getPhone());
        holder.post.setText(teacherData.getPost());

        try {
            Glide.with(context).load(teacherData.getImage()).placeholder(R.drawable.ic_user).into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView email;
        private final TextView phone;
        private final TextView post;
        private final ImageView image;
        //private ProgressBar progressBar;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.facultyName);
            email = itemView.findViewById(R.id.facultyMail);
            phone = itemView.findViewById(R.id.facultyPhone);
            post = itemView.findViewById(R.id.facultyPost);
            image = itemView.findViewById(R.id.facultyImage);

            name.setSelected(true);
            email.setSelected(true);
            post.setSelected(true);
            //progressBar.setVisibility(View.VISIBLE);
        }
    }
}
