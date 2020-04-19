/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cei.simulator.server;

import cei.simulator.server.restful.get.*;
import cei.simulator.server.restful.get.url.Url;
import cei.simulator.server.restful.post.Authentication;
import cei.simulator.server.restful.post.Inputs;
import cei.simulator.server.websocket.PingPong;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;

import java.util.HashMap;
import java.util.Map;

/**
 * @author U0151316
 */
public class main {
    private static final Vertx vertx = Vertx.vertx();
    private static Router router = null;
    private static final Map<String, ProcessorWS> processorWSMap = new HashMap<>();


    @FunctionalInterface
    public interface Processor {
        String process(HttpServerRequest request);
    }

    @FunctionalInterface
    public interface ProcessorBody {
        String process(HttpServerRequest request, Buffer body);
    }

    @FunctionalInterface
    public interface ProcessorWS {
        String process(String clientID, Buffer binary, String text);
    }

    public static void routeGet(String path, Processor processor) {
        System.out.println("Registering GET: " + path);
        router.route(HttpMethod.GET, path).handler(routingContext -> {
            System.out.println(path);
            routingContext.response().end(processor.process(routingContext.request()));
        });
    }

    public static void routePost(String path, ProcessorBody processor) {
        System.out.println("Registering POST: " + path);
        router.route(HttpMethod.POST, path).handler(routingContext -> {
            routingContext.request().bodyHandler(body -> {
                routingContext.response().end(processor.process(routingContext.request(), body));
            });
        });
    }

    public static void routeWS(String path, ProcessorWS processorWS) {
        processorWSMap.put(path, processorWS);
    }

    public static void main(String[] args) {

        router = Router.router(vertx);

        SimpleInfo.register();
        ModelInfo.register();
        PriceList.register();
        InfoList.register();
        TestArray.register();
        InputsByGet.register();
        Url.register();
        Inputs.register();
        Authentication.register();

        router.route(HttpMethod.GET, "/").handler(routingContext -> {
            routingContext.response().end("Hello");
        });

        HttpServer server = vertx.createHttpServer().requestHandler(router);
        server.websocketHandler(webSocket -> {
            if (processorWSMap.containsKey(webSocket.path())) {
                // Accept
                clientMap.put(webSocket.textHandlerID(), webSocket);
                webSocket.frameHandler(frame -> {
                    ProcessorWS processorWS = processorWSMap.get(webSocket.path());
                    if (frame.isBinary()) {
                        webSocket.writeTextMessage(processorWS.process(webSocket.textHandlerID(), frame.binaryData(), null));
                    } else if (frame.isText()) {
                        webSocket.writeTextMessage(processorWS.process(webSocket.textHandlerID(), null, frame.textData()));
                    }
                });
                webSocket.closeHandler(close -> {
                    clientMap.remove(webSocket.textHandlerID());
                });
            } else {
                webSocket.reject();
            }
        });
        PingPong.initialize();
        server.listen(8888);
        System.out.println("Server started");


    }
}
