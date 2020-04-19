package cei.simulator.server.websocket;

import io.vertx.core.http.ServerWebSocket;

import java.util.HashMap;
import java.util.Map;

public class Clients {
    private static final Map<String, ServerWebSocket> clientMap = new HashMap<>();
}
