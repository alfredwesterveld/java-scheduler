/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author alfred
 */
public class Scheduler<E> {
    private final DelayQueue<Task<E>> delayedJobQueue;
    private final AtomicInteger totalTask = new AtomicInteger(0);
    private final AtomicInteger taskRun = new AtomicInteger(0);

    public Scheduler() {
        delayedJobQueue = new DelayQueue<Task<E>>();
    }

    public void schedule(final Task<E> j) {
        delayedJobQueue.put(j);
        totalTask.incrementAndGet();
    }

    public Task<E> peek() {
        return delayedJobQueue.peek();
    }

    public Task<E> run() throws InterruptedException {
        try {
            return delayedJobQueue.take();
        } finally {
            taskRun.incrementAndGet();
        }
    }

    public int getTaskRun() {
        return taskRun.get();
    }

    public int getTotalTask() {
        return totalTask.get();
    }
}
