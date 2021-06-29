package com.example.post_pc_ex8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RootItemsHolder itemsHolder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WorkManager workManager = WorkManager.getInstance(RootApp.getInstance());
        if(itemsHolder == null){
            itemsHolder = new RootItemsHolderImp(RootApp.getInstance());
        }

        RootAdapter rootAdapter = new RootAdapter(itemsHolder);
        RecyclerView rootRecycler = findViewById(R.id.recyclerRootItemsList);
        rootRecycler.setAdapter(rootAdapter);
        rootRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        EditText editText = findViewById(R.id.editTextInsertTask);
        FloatingActionButton createRoot = findViewById(R.id.buttonCreateRootItem);
        createRoot.setOnClickListener(v->{
            if(!editText.getText().toString().equals("")){

                long root = Long.parseLong(editText.getText().toString());
                OneTimeWorkRequest build = new OneTimeWorkRequest.Builder(RootWorker.class)
                        .setInputData(new Data.Builder().putLong("rootNum", root).build())
                        .addTag("calculateRoots").build();


                workManager.enqueueUniqueWork(editText.getText().toString() , ExistingWorkPolicy.REPLACE, build);
                RootItem rootItem = new RootItem(root, build.getId());
                int index = itemsHolder.addNewInProgressItem(rootItem);
                rootAdapter.notifyItemChanged(index);
                editText.setText("");
            }
        });

        // TODO DEAL WITH DELETE

        rootAdapter.onClickCallBack =  (RootItem rootItem) ->{

            int pos = itemsHolder.itemIndex(rootItem);
            itemsHolder.deleteItem(rootItem);
            rootAdapter.notifyItemRemoved(pos);
            workManager.cancelWorkById(rootItem.getId());
        };

        LiveData<List<WorkInfo>> calculateRoots = workManager.getWorkInfosByTagLiveData("calculateRoots");

        calculateRoots.observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                for(WorkInfo workInfo : workInfos){
                    int pos = itemsHolder.itemPosByID(workInfo.getId());
                    if(pos == -1){
                        continue;
                    }
                    if(workInfo.getState() == WorkInfo.State.SUCCEEDED){

                        long r1 = workInfo.getOutputData().getLong("r1", 0);
                        long r2 = workInfo.getOutputData().getLong("r2", 0);
                        RootItem rootItem = itemsHolder.getCurrentItems().get(pos);
                        rootItem.setRoots(r1, r2);
                        int newPos = itemsHolder.markItemDone(workInfo.getId());
                        workManager.cancelWorkById(workInfo.getId());
                        rootAdapter.notifyItemMoved(pos, newPos);
                        rootAdapter.notifyItemChanged(newPos);
                    }
                    else{
                        Data progress = workInfo.getProgress();
                        int in_progress = progress.getInt("IN PROGRESS", 0);
                        RootItem rootItem = itemsHolder.getCurrentItems().get(pos);
                        rootItem.setRootProgress(in_progress);
                        rootAdapter.notifyItemChanged(pos);
                    }
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }
}