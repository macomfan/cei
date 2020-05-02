package cn.ma.cei.test.websocket;

import cn.ma.cei.test.websocket.notification.PingPong;
import cn.ma.cei.test.websocket.notification.Second1;
import cn.ma.cei.test.websocket.notification.Second2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {

    public Second1 second1;
    public Second2 second2;

    public void initialize() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        Runnable runnable = () -> {
           second1.trigger();
           if (System.currentTimeMillis() / 1000 % 2 == 0) {
               second2.trigger();
           }
        };
        exec.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
    }
}
