package cn.ma.cei;

import cn.ma.cei.service.Service;
import cn.ma.cei.test.restful.get.*;
import cn.ma.cei.test.restful.get.url.Url;
import cn.ma.cei.test.restful.post.Authentication;
import cn.ma.cei.test.restful.post.Echo;
import cn.ma.cei.test.websocket.Simulation;
import cn.ma.cei.test.websocket.notification.PingPong;
import cn.ma.cei.test.websocket.notification.Second1;
import cn.ma.cei.test.websocket.handler.EventHandler;
import cn.ma.cei.test.websocket.handler.TestHandler;
import cn.ma.cei.test.websocket.notification.Second2;

public class TestMode {
    public static void run(int port) {
        Service service = new Service();
        service.registerRestful("/restful/get/infoList", new InfoList())
                .registerRestful("/restful/get/url/*", new Url())
                .registerRestful("/restful/get/inputsByGet", new InputsByGet())
                .registerRestful("/restful/get/modelInfo", new ModelInfo())
                .registerRestful("/restful/get/priceList", new PriceList())
                .registerRestful("/restful/get/simpleInfo", new SimpleInfo())
                .registerRestful("/restful/get/testArray", new TestArray())
                .registerRestful("/restful/post/echo", new Echo())
                .registerRestful("/restful/post/authentication",  new Authentication());

        service.registerWebSocket("/websocket/event", new EventHandler());
        service.registerWebSocket("/websocket/test", new TestHandler());

        PingPong pingPong = new PingPong();
        Second1 second1 = new Second1();
        Second2 second2 = new Second2();
        PingPong.initialize(pingPong);
        service.registerWebSocketNotification("ping", pingPong);
        service.registerWebSocketNotification("Second1", second1);
        service.registerWebSocketNotification("Second2", second2);

        Simulation simulation = new Simulation();
        simulation.second1 = second1;
        simulation.second2 = second2;
        simulation.initialize();
        service.start(port);
    }
}
