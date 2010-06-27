/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler.http;

import com.alfredwesterveld.App;
import com.alfredwesterveld.MyYaml;
import com.alfredwesterveld.scheduler.Scheduler;
import com.alfredwesterveld.scheduler.Task;
import com.alfredwesterveld.scheduler.URLFetcher;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author alfred
 */
@Path("/scheduler")
public class SchedulerHttpServer {
    private static final Scheduler<URLFetcher> SCHEDULER = App.getScheduler();
    private static final Yaml YAML = MyYaml.getYaml();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/time")
    public String scheduleTask(MultivaluedMap<String, String> form) {
        final String URL = SchedulerParamChecker.URL(form.getFirst("URL"));
        final long msLong = SchedulerParamChecker.time(form.getFirst("time"));
        final LinkedBlockingQueue<Long> retry =
            SchedulerParamChecker.retryQueue(form.getFirst("retry"));
        final URLFetcher urlf = URLFetcher.build(URL, retry);
        SCHEDULER.schedule(new Task<URLFetcher>(urlf, msLong));
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
