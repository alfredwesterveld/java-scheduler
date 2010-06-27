/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author alfred
 */
public class Task<E> implements Delayed {

    private final long scheduleMillis;
    private final E e;

    public Task(E e, long executeMillis) {
        this.e = e;
        this.scheduleMillis = executeMillis;
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

    public E getJob() {
        return this.e;
    }

    @Override
    public String toString() {
        return scheduleMillis + "," + e.toString();
    }
}
