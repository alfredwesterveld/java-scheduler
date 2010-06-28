package com.alfredwesterveld;

import com.alfredwesterveld.scheduler.Scheduler;
import com.alfredwesterveld.scheduler.TaskRunner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.atmosphere.grizzly.AtmosphereSpadeServer;

/**
 * Hello world!
 *
 */
public class App {
    /**
     * Specify host which Server is running on.
     */
    public static final String HOST = "http://localhost:8888/";

    /**
     * Setup scheduler.
     */
    private static Scheduler<String> SCHEDULER;

    private static final ExecutorService st =
        Executors.newCachedThreadPool();

    
    public static void main(String[] args) throws Exception {
        String host = setupHost(args);
        String resourcesPackage = com.alfredwesterveld.scheduler.httpserver.SchedulerHttpServer.class.getPackage().getName();
        AtmosphereSpadeServer server = AtmosphereSpadeServer.build(host,
            resourcesPackage);
        server.start();
        final TaskRunner taskRunner = new TaskRunner();
        taskRunner.run();
    }

    public static String setupHost(String[] args) {
        if (args.length >= 1 && args[0] != null) {
            return args[0];
        }
        return HOST;
    }

    public static Scheduler<String> getScheduler() {
         if (SCHEDULER == null) {
            App.SCHEDULER = new Scheduler<String>();
        }
        return SCHEDULER;
    }
}
