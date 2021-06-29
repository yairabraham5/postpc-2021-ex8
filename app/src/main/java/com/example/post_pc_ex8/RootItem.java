package com.example.post_pc_ex8;

import java.util.UUID;

public class RootItem implements Comparable<RootItem>{

    Long number;
    UUID id;
    int rootProgress;
    long firstRoot;
    long secondRoot;
    boolean done = false;
    long sqrtOfNum;

    public RootItem(Long numberToCalc, UUID rootId){
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

    public UUID getId() {
        return id;
    }

    @Override
    public int compareTo(RootItem o) {
        if (this.done && !o.done) {
            return 1;
        }
        if(!this.done && o.done){
            return -1;
        }
        return this.number.compareTo(o.number);
    }
}
