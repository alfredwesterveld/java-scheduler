/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler.http;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author alfred
 */
public class SchedulerParamChecker {
    public static String URL(String URL) {
        if (URL == null) {
            ResponseBuilder badRequest = Response.status(Status.BAD_REQUEST);
            Response response = badRequest.entity("[ERROR]: URL param\n").build();
            throw new WebApplicationException(response);
        }
        return URL;
    }

    public static long time(String time) {
        try {
            return Long.parseLong(time);
        } catch(NumberFormatException nfe) {
            ResponseBuilder badRequest = Response.status(Status.BAD_REQUEST);
            Response response = badRequest.entity("[ERROR]: time param\n").build();
            throw new WebApplicationException(response);
        }
    }

    public static LinkedBlockingQueue<Long> 
    retryQueue(String retryStringArray) {
        LinkedBlockingQueue<Long> retryQueue = new LinkedBlockingQueue<Long>();
        if (retryStringArray != null) {
            String[] split = retryStringArray.split(",");
            List<String> asList = Arrays.asList(split);
            for (String s : asList) {
                try {
                    retryQueue.add(Long.parseLong(s));
                } catch(NumberFormatException nfe) {}
            }
        }
        return retryQueue;
    }
}
