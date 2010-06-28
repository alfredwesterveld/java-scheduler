/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.httpclient;

import java.io.IOException;
import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;

/**
 *
 * @author alfred
 */
public class MultiThreadedHttpClient {
    private final HttpClient client = new HttpClient();
    
    public MultiThreadedHttpClient() {
    }

    public void start() throws Exception {
        client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
        client.start();
    }

    public void get(String URL) throws IOException {
        ContentExchange exchange = getExchange(URL);
        client.send(exchange);
    }

    private ContentExchange getExchange(String URL) {
        ContentExchange exchange = new ContentExchange();
        exchange.setMethod("GET");
        exchange.setURL(URL);
        exchange.setEventListener(new MyHttpEventListener());
        return exchange;
    }
}
