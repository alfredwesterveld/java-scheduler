package com.alfredwesterveld;

import com.alfredwesterveld.scheduler.Scheduler;
import com.alfredwesterveld.scheduler.TaskRunner;
import com.alfredwesterveld.scheduler.URLFetcher;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
    private static Scheduler<URLFetcher> SCHEDULER;

    private static final ExecutorService st =
        Executors.newCachedThreadPool();

    static Logger logger = Logger.getRootLogger();

    private static void setupLogger() {
         BasicConfigurator.configure();
         logger.setLevel(Level.OFF);
    }
    
    public static void main(String[] args) throws Exception {
        setupLogger();
        st.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TaskRunner.run();
                } catch(Exception io) {

                }
            }
        });
        String host = setupHost(args);
        String resourcesPackage = com.alfredwesterveld.scheduler.
            http.SchedulerHttpServer.class.getPackage().getName();
        AtmosphereSpadeServer server = AtmosphereSpadeServer.build(host,
            resourcesPackage);
        server.start();
    }

    public static String setupHost(String[] args) {
        if (args.length >= 1 && args[0] != null) {
            return args[0];
        }
        return HOST;
    }

    public static Scheduler<URLFetcher> getScheduler() {
         if (SCHEDULER == null) {
            App.SCHEDULER = new Scheduler<URLFetcher>();
        }
        return SCHEDULER;
    }
}
