package com.example.post_pc_ex8;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RootViewHolder extends RecyclerView.ViewHolder {
    public RootViewHolder(@NonNull View itemView) {
        super(itemView);
        ProgressBar progressBar = itemView.findViewById(R.id.progressBar);
        TextView result = itemView.findViewById(R.id.description);
        TextView rootNumber = itemView.findViewById(R.id.rootNumber);
        ImageView delete = itemView.findViewById(R.id.imageView);

    }
}
