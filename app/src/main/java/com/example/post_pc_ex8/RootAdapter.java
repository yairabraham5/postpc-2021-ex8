package com.example.post_pc_ex8;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RootAdapter extends  RecyclerView.Adapter<RootViewHolder> {

    interface OnClickCallBack{
        public void onDelete(RootItem rootItem);
    }
    OnClickCallBack onClickCallBack = null;
    RootItemsHolder itemsHolder;
    public RootAdapter(RootItemsHolder itemsHolder){
        this.itemsHolder = itemsHolder;
    }


    @NonNull
    @Override
    public RootViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.root_item, parent, false);
        return new RootViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RootViewHolder holder, int position) {
        List<RootItem> currentItems = this.itemsHolder.getCurrentItems();
        RootItem rootItem = currentItems.get(position);
        if(rootItem.isDone()){
            String text;
            if(rootItem.firstRoot == 0){
                text = "Prime Number";
            }
            else {
                text = +rootItem.firstRoot+ "x" + rootItem.secondRoot;
            }
            holder.result.setText(text);
            holder.rootNumber.setText(Long.toString(rootItem.getNumber()));
            holder.progressBar.setVisibility(View.INVISIBLE);
        }
        else{

            holder.rootNumber.setText(Long.toString(rootItem.getNumber()));
            holder.result.setText("IN PROGRESS");
            holder.progressBar.setMax((int)rootItem.getSqrtOfNum());
            holder.progressBar.setProgress(rootItem.getRootProgress(), true);
            holder.progressBar.setVisibility(View.VISIBLE);
        }

        holder.delete.setOnClickListener(v->{
            if(onClickCallBack == null) return;
            onClickCallBack.onDelete(rootItem);
        });


    }

    @Override
    public int getItemCount() {
        return this.itemsHolder.getCurrentItems().size();
    }
}
