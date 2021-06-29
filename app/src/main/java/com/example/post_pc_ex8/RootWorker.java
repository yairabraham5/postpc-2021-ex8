package com.example.post_pc_ex8;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RootWorker extends Worker {
    private static final String inProgress = "IN PROGRESS";
    Data.Builder builder = new Data.Builder();
    public RootWorker(@NonNull Context context, @NonNull WorkerParameters workerParameters){
        super(context, workerParameters);
        setProgressAsync(new Data.Builder().putLong(inProgress, 0).build());

    }
    @NonNull
    @Override
    public Result doWork() {
        long rootNum = getInputData().getLong("rootNum", 0);
        long sqrtNum = 0;
        if (rootNum != 0){
            sqrtNum = (long) Math.sqrt((double) rootNum);
        }
        long savedCalc = RootApp.getInstance().loadFromSharedPreferences(String.valueOf(rootNum));
        for(long i = savedCalc; i < sqrtNum+1; i++){
            try {
                setProgressAsync(builder.putInt(inProgress, (int)i).build());
                Thread.sleep(50L);
                if(i% 100000 == 0){
                    RootApp.getInstance().savedToSharedPreferences(String.valueOf(rootNum), i);
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(rootNum % i == 0){
                Data data = new Data.Builder()
                        .putLong("r1", i)
                        .putLong("r2", rootNum / i).build();
                setProgressAsync(this.builder.putInt(inProgress,(int) i).build());
                return Result.success(data);
            }
        }
        setProgressAsync(builder.putInt(inProgress, (int)rootNum).build());
        return Result.success();
    }
}
