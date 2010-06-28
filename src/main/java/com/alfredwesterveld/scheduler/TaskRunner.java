/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler;

import com.alfredwesterveld.App;
import com.alfredwesterveld.httpclient.MultiThreadedHttpClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author alfred
 */
public class TaskRunner {
    private static final Scheduler<String> scheduler = App.getScheduler();
    private final ExecutorService tp = Executors.newCachedThreadPool();
    private final MultiThreadedHttpClient client =
        new MultiThreadedHttpClient();
    
    public TaskRunner() {
    }

    public void run() throws Exception {
        client.start();
        while (true) {
            final Task<String> task = scheduler.run();
            String job = task.getJob();
            client.get(job);
        }
    }
}
