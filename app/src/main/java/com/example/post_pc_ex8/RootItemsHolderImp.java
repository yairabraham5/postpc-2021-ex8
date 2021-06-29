package com.example.post_pc_ex8;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.work.WorkManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RootItemsHolderImp implements RootItemsHolder {
    WorkManager workManager;
    SharedPreferences sharedPreferences;
    ArrayList<RootItem> rootItems;

    public RootItemsHolderImp(Context context){
        sharedPreferences = context.getSharedPreferences("localDb", Context.MODE_PRIVATE);
        // call init from shared preferences
        initFromSp();
        workManager = WorkManager.getInstance(context);
    }

    public void initFromSp() {

        String savedGson = sharedPreferences.getString("itemsHolder", "empty");
        if(savedGson.equals("empty")){
            rootItems = new ArrayList<>();
            return;
        }
        Gson gson = new Gson();
        rootItems = gson.fromJson(savedGson, new TypeToken<ArrayList<RootItem>>(){}.getType());
    }


    public void saveInSp(){
        Gson gson = new Gson();
        String savedArray = gson.toJson(rootItems,  new TypeToken<ArrayList<RootItem>>(){}.getType());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("itemsHolder", savedArray);
        edit.apply();
    }


    @Override
    public List<RootItem> getCurrentItems() {
        return new ArrayList<>(rootItems);

    }

    @Override
    public int addNewInProgressItem(RootItem rootItem) {
        rootItems.add(0, rootItem);
        // checked for changes
        int newIndex = reOrderRootsInProgress();
        saveInSp();
        return newIndex;
    }

    @Override
    public void deleteItem(RootItem item) {
        rootItems.remove(item);
        saveInSp();
    }

    @Override
    public int markItemDone(UUID id) {
        for (int i = 0; i < rootItems.size(); i++) {
            RootItem currentItem = rootItems.get(i);
            if(currentItem.getId().equals(id)){
                currentItem.done = true;
                return reOrderRootToDone(i);
            }
        }
        return -1;
    }


    @Override
    public int itemIndex(RootItem item) {
        for (int i = 0; i < rootItems.size(); i++) {

            RootItem currentItem = rootItems.get(i);
            if(currentItem.equals(item)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int itemPosByID(UUID id) {
        for (int i = 0; i < rootItems.size(); i++) {
            RootItem item = rootItems.get(i);
            if(item.getId().equals(id)){
                return i;
            }
        }

        return -1;
    }

    private int reOrderRootsInProgress(){
        int currentPos = 0;
        for (int i = 0; i < rootItems.size() - 1; i++) {
            RootItem currentRoot = rootItems.get(i);
            RootItem nextRoot = rootItems.get(i + 1);
            if(nextRoot.isDone()){
                break;
            }
            if(currentRoot.compareTo(nextRoot) > 0){
                currentPos++;
                rootItems.set(i, nextRoot);
                rootItems.set(i + 1, currentRoot);
            }
        }
        return currentPos;
    }

    private int reOrderRootToDone(int index){
        int currentPos = index;
        for (int i = 0; i < rootItems.size() - 1; i++) {
            RootItem currentRoot = rootItems.get(i);
            RootItem nextRoot = rootItems.get(i + 1);
            if(nextRoot.isDone()){
                break;
            }
            currentPos++;
            rootItems.set(i, nextRoot);
            rootItems.set(i + 1, currentRoot);
        }
        return currentPos;
    }
}


