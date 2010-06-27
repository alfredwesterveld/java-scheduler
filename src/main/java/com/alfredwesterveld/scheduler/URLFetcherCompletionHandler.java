/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler;

import com.alfredwesterveld.App;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author alfred
 */
public class URLFetcherCompletionHandler extends
AsyncCompletionHandler<Response> {
    private final URLFetcher urlf;

    private final static Scheduler<URLFetcher> SCHEDULER = App.getScheduler();
    
    public URLFetcherCompletionHandler(final URLFetcher urlf) {
        this.urlf = urlf;
    }

    @Override
    public Response onCompleted(Response response) throws Exception {
        return response;
    }

    @Override
    public void onThrowable(Throwable t) {
        retry();
    }

    private void retry() {
        final String URL = urlf.getURL();
        final LinkedBlockingQueue<Long> queue = urlf.getQueue();
        try {
            Long remove = queue.remove();
            if (remove.longValue() > 0) {
                final long time = System.currentTimeMillis() +
                    remove.longValue();
                final URLFetcher nURL = URLFetcher.build(URL, queue);
                Task<URLFetcher> task = new Task<URLFetcher>(nURL, time);
                SCHEDULER.schedule(task);
            }
        } catch(NoSuchElementException nsee) {
        }
    }
}
