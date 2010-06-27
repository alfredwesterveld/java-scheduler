/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler;

import com.alfredwesterveld.App;
import java.io.IOException;

/**
 *
 * @author alfred
 */
public class TaskRunner {
    private static final Scheduler<URLFetcher> scheduler = App.getScheduler();
    
    private TaskRunner() {
    }

    public static void run() throws Exception {
        _run();
    }

    private static void _run() {
         while (true) {
            try {
                final Task<URLFetcher> task = scheduler.execute();
                final URLFetcher urlf = task.getJob();
                final String url = urlf.getURL();
                urlf.fetch();
            } catch(InterruptedException ex) {
                break;
            } catch(IOException io) {
            }
        }
    }
}
