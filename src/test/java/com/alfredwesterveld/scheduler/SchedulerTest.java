/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler;

import java.util.ArrayDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author alfred
 */
public class SchedulerTest {

    private Scheduler<String> scheduler;
    public SchedulerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void before() {
         scheduler = new Scheduler<String>();
    }

    @Test @Ignore
    public void testSomeMethod() throws Exception {
        scheduler.schedule(createTask("last", 5000));
        scheduler.schedule(createTask("second", 3000));
        scheduler.schedule(createTask("first", 1000));
        CountDownLatch latch = new CountDownLatch(3);
        while (latch.getCount() != 0) {
            System.out.println(scheduler.execute());
            latch.countDown();
        }
    }

    @Test
    public void testMultiple() throws Exception {
        System.out.println("hieeeeer");
        ExecutorService newSingleThreadExecutor =
            Executors.newSingleThreadExecutor();
        scheduler.schedule(createTask("first", 1000));
        final ArrayDeque<String> results = new ArrayDeque<String>(3);
        final CountDownLatch count = new CountDownLatch(1);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        scheduler.peek();
                        System.out.println("h");
                        count.countDown();
                        Task<String> runTask = scheduler.execute();
                        String job = runTask.getJob();
                        System.out.println(job);
                        results.add(job);
                    } catch (Exception ex) {
                        Logger.getLogger(SchedulerTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        newSingleThreadExecutor.submit(runnable);
        count.await();
        scheduler.schedule(createTask("last", 15000));
        scheduler.schedule(createTask("second", 5000));
        
        String poll = results.poll();
        while (poll != null) {
            System.out.println("poll = " + poll);
            poll = results.poll();
        }
    }

    private static Task<String> createTask(String s, long future) {
        return new Task<String>(s, System.currentTimeMillis() + future);
    }

}