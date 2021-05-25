package com.ishika.collageapp.ui.notice;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ishika.collageapp.R;
import com.ishika.collageapp.ui.gallery.PhotoActivity;


import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.viewHolder>{
    private final Context context;
    private final ArrayList<NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        NoticeData noticeData = list.get(position);
        holder.notText.setText(noticeData.getTitle());
        holder.nDate.setText(noticeData.getDate());
        holder.nTime.setText(noticeData.getTime());
        try {
            if(noticeData.getImage() != null)
                Glide.with(context).load(noticeData.getImage()).into(holder.notPrev);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.notPrev.setOnClickListener(v -> {
            Intent intent = new Intent(context, PhotoActivity.class);
            intent.putExtra("photo", noticeData.getImage());
            intent.putExtra("date", noticeData.getDate());
            intent.putExtra("time", noticeData.getTime());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final TextView notText;
        private final TextView nDate;
        private final TextView nTime;
        private final ImageView notPrev;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            notText = itemView.findViewById(R.id.notText);
            notPrev = itemView.findViewById(R.id.notPrev);
            nDate = itemView.findViewById(R.id.nDate);
            nTime = itemView.findViewById(R.id.nTime);
        }
    }
}
