/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.httpclient;

import com.alfredwesterveld.App;
import com.alfredwesterveld.scheduler.Task;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;

/**
 *
 * @author alfred
 */
public class MultiThreadedHttpClient {
    private final HttpClient client = new HttpClient();
    private final AtomicInteger maxRetries;
    private final AtomicInteger interval;
    
    public MultiThreadedHttpClient() {
        this.maxRetries = new AtomicInteger(5);
        this.interval = new AtomicInteger(30000);
    }

    public void start() throws Exception {
        client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
        client.start();
    }

    public void get(String URL, final int count) throws IOException {
        ContentExchange exchange = getExchange(URL, count);
        client.send(exchange);
    }

    private ContentExchange getExchange(String URL, final int count) {
        ContentExchange exchange = new ContentExchange() {
            @Override
            protected void onException(Throwable x) {
                System.out.println("exception");
            }

            @Override
            protected void onConnectionFailed(Throwable x) {
                System.out.println("count: " + count);
                if (count < maxRetries.get()) {
                    final long scheduleMillis = System.currentTimeMillis() +
                        interval.get();
                    final String URL = this.getScheme().toString() + "://" +
                    this.getAddress().toString() + this.getURI();
                    App.getScheduler().schedule(
                        new Task<String>(URL, scheduleMillis, count+1));
                }
            }
        };
        exchange.setMethod("GET");
        exchange.setURL(URL);
        return exchange;
    }
}
