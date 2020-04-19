package cn.ma.cei.test.websocket;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PingPong {

    //private static Map<String, Se>

    public static void initialize() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        Runnable runnable = () -> {

        };
        exec.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
    }

    public static void register() {

    }
}
