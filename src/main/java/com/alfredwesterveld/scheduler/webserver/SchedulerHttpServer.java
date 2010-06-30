/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler.webserver;

import com.alfredwesterveld.App;
import com.alfredwesterveld.MyYaml;
import com.alfredwesterveld.scheduler.Scheduler;
import com.alfredwesterveld.scheduler.Task;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author alfred
 */
@Path("/scheduler")
public class SchedulerHttpServer {
    private static final Scheduler<String> SCHEDULER = App.getScheduler();
    private static final Yaml YAML = MyYaml.getYaml();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/add")
    public String scheduleTask(MultivaluedMap<String, String> form) {
        final ResponseBuilder badRequest = Response.status(Status.BAD_REQUEST);
        try {
            final String URL = form.getFirst("URL");
            final long epoch = Long.parseLong(form.getFirst("epoch"));
            URL.equals("");
            SCHEDULER.schedule(new Task<String>(URL, epoch));
        } catch(NumberFormatException nfe) {
            Response response = badRequest.entity("epoch param\n").build();
            throw new WebApplicationException(response);
        } catch(NullPointerException ne) {
            Response response = badRequest.entity("URL param\n").build();
            throw new WebApplicationException(response);
        }
        return "+OK\n";
    }

    public String remove() {
        return "+OK\n";
    }

    @GET
    @Path("/stats")
    public String showStats() {
        final Map<String, Object> data = new HashMap<String, Object>();
        data.put("task run", SCHEDULER.getTaskRun());
        data.put("total task", SCHEDULER.getTotalTask());
        return YAML.dump(data);
    }
}
