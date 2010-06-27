/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler;

import com.ning.http.client.AsyncHttpClient;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author alfred
 */
public class URLFetcher {
    private final String URL;
    private final LinkedBlockingQueue<Long> retryQueue;
    private static final AsyncHttpClient client = new AsyncHttpClient();


    private URLFetcher(final String URL, final LinkedBlockingQueue<Long> retryQueue) {
        this.URL = URL;
        this.retryQueue = retryQueue;
    }

    public static URLFetcher build(final String URL,
    final LinkedBlockingQueue<Long> retryQueue) {
        return new URLFetcher(URL, retryQueue);
    }

    public String getURL() {
        return URL;
    }

    public void fetch() throws IOException {
        URLFetcherCompletionHandler handler =
            new URLFetcherCompletionHandler(this);
        client.prepareGet(URL).execute(handler);
    }

    public LinkedBlockingQueue<Long> getQueue() {
        return retryQueue;
    }
}
