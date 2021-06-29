package com.example.post_pc_ex8;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RootViewHolder extends RecyclerView.ViewHolder {
    ProgressBar progressBar;
    TextView result;
    TextView rootNumber;
    ImageView delete;

    public RootViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
        result = itemView.findViewById(R.id.description);
        rootNumber = itemView.findViewById(R.id.rootNumber);
        delete = itemView.findViewById(R.id.imageView);

    }
}
