package fhku.backgroundwork;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DemoWorker extends Worker {
    protected String LOG = "BACKGROUND_WORK";

    public DemoWorker(
        @NonNull Context context,
        @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Log.i(LOG, "Start some pseudo work...");
            for (int i = 0; i < 10; i++) {
                Thread.sleep(500);
            }
            Log.i(LOG, "Stop pseudo work...");
        } catch (Exception e) {}

        // Rückgabe Result.success(),
        // Result.failure(), Result.retry()
        return Result.success();
    }
}
