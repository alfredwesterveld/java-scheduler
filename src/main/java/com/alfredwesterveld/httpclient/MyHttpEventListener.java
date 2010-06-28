/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.httpclient;

import java.io.IOException;
import org.eclipse.jetty.client.HttpEventListenerWrapper;

/**
 *
 * @author alfred
 */
public class MyHttpEventListener extends HttpEventListenerWrapper {

    @Override
    public void onConnectionFailed(Throwable ex) {
        super.onConnectionFailed(ex);
        // Need to write this I guess?
    }

    @Override
    public void onRequestComplete() throws IOException {
        super.onRequestComplete();
    }
}
