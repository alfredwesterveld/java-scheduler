/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler.httpserver;

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
}
