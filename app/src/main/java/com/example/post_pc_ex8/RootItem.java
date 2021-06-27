package com.example.post_pc_ex8;

import java.util.UUID;

public class RootItem {

    long number;
    UUID id;
    int rootProgress;
    long firstRoot;
    long secondRoot;
    boolean done = false;
    long sqrtOfNum;

    public RootItem(long numberToCalc, UUID rootId){
        this.number = numberToCalc;
        this.id = rootId;
        this.rootProgress = 0;
        this.sqrtOfNum = (long)Math.sqrt((double) numberToCalc);
    }

    public void setRoots(long r1, long r2){
        this.firstRoot = r1;
        this.secondRoot = r2;
        this.done = true;
    }

    public int getRootProgress() {
        return rootProgress;
    }

    public void setRootProgress(int rootProgress) {
        this.rootProgress = rootProgress;
    }

    public long getSqrtOfNum() {
        return sqrtOfNum;
    }

    public long getNumber() {
        return number;
    }

    public boolean isDone() {
        return done;
    }

}
