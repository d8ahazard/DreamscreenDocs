package com.philips.lighting.hue.sdk.wrapper.utilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {
    private static ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
    private static int threadPoolSize = 1;

    public static void submit(final Runnable task) {
        executorService.submit(new Runnable() {
            public void run() {
                task.run();
            }
        });
    }
}
