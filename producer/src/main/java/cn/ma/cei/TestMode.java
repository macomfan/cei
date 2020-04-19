package cn.ma.cei;

import cn.ma.cei.service.Service;
import cn.ma.cei.test.restful.get.*;
import cn.ma.cei.test.restful.get.url.Url;
import cn.ma.cei.test.restful.post.Authentication;
import cn.ma.cei.test.restful.post.Echo;
import cn.ma.cei.test.websocket.messages.EchoMessage;
import cn.ma.cei.test.websocket.processor.EchoProcessor;
import cn.ma.cei.test.websocket.request.RequestHandler;

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

        service.registerWebSocket("/request", new RequestHandler());

        EchoMessage.setProcessor(new EchoProcessor());

        service.start(port);
    }
}
