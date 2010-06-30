/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @param <E> 
 * @author alfred
 */
public class Task<E> implements Delayed {

    private final long scheduleMillis;
    private final E e;
    private final int count;

    public Task(final E e, long scheduleMillis) {
        this(e,scheduleMillis,0);
    }

    public Task(final E e, final long scheduleMillis, final int count) {
        this.e = e;
        this.scheduleMillis = scheduleMillis;
        this.count = count;
    }


    @Override
    public long getDelay(TimeUnit tu) {
        return tu.convert(scheduleMillis - System.currentTimeMillis(), 
            TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed t) {
        long thisDelay = getDelay(TimeUnit.MILLISECONDS);
        long otherDelay = t.getDelay(TimeUnit.MILLISECONDS);
        return  (thisDelay < otherDelay) ? -1 :
                (thisDelay > otherDelay) ? 1 : 0;
    }

    public int getCount() {
        return count;
    }

    public E getJob() {
        return this.e;
    }

    @Override
    public String toString() {
        return scheduleMillis + "," + e.toString();
    }
}
