package cn.ma.cei.test.websocket.notification;

import cn.ma.cei.service.WebSocketNotification;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PingPong extends WebSocketNotification {

    public static void initialize(PingPong ping) {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        Runnable runnable = ping::trigger;
        exec.scheduleAtFixedRate(runnable, 3, 3, TimeUnit.SECONDS);
    }

    private static final String Ping = "{\"op\":\"ping\",\"ts\":\"%d\"}";

    @Override
    public void trigger() {
        getClients().forEachValue(1, client -> {
            client.send(String.format(Ping, System.currentTimeMillis() / 1000));
        });
    }
}
