package com.jamalonexpress.testhcut;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private Context mContext;
    public ArrayList<String> titles;
    public ArrayList<Integer> images;

    public RecycleViewAdapter(Context mContext, ArrayList<String> titles, ArrayList<Integer> images) {
        this.mContext = mContext;
        this.titles = titles;
        this.images = images;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder viewHolder, int i) {

        Glide.with(mContext)
                .asBitmap()
                .load(images.get(i))
                .into(viewHolder.imageView);
        viewHolder.imageName.setText(titles.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mContext, ""+titles.get(i), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, MapsActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();

            imageName = itemView.findViewById(R.id.image_name);
            imageView = itemView.findViewById(R.id.image);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
