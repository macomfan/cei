package cn.ma.cei.service;

import java.util.concurrent.ConcurrentHashMap;

public abstract class WebSocketNotification {
    public ConcurrentHashMap<String, WebSocketClient> clients = new ConcurrentHashMap<>();

    public void registerClient(WebSocketClient client) {
        if (client == null) {
            // TODO
            // error
            return;
        }
        if (!clients.containsKey(client.id())) {
            clients.put(client.id(), client);
        }
    }

    public void unregisterClient(WebSocketClient client) {
        clients.remove(client.id());
    }

    public ConcurrentHashMap<String, WebSocketClient> getClients() {
        return clients;
    }

    public abstract void trigger();
}
