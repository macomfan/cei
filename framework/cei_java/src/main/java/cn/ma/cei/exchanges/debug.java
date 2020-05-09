package cn.ma.cei.exchanges;

import cn.ma.cei.impl.CEIUtils;
import cn.ma.cei.impl.IWebSocketCallback;
import cn.ma.cei.impl.JsonChecker;
import cn.ma.cei.impl.JsonWrapper;
import cn.ma.cei.impl.WebSocketConnection;
import cn.ma.cei.impl.WebSocketEvent;
import cn.ma.cei.impl.WebSocketOptions;
import java.math.BigDecimal;

public class debug {

    static public class SimpleInfo {
        public String name;
        public Long number;
        public BigDecimal price;
        public Boolean status;
    }

    static public class WSClient {
        private final WebSocketOptions option;
        private final WebSocketConnection connection;
    
        public WSClient() {
            this.option = new WebSocketOptions();
            this.option.url = "http://127.0.0.1:8888";
            this.connection = new WebSocketConnection(this.option);
        }
    
        public WSClient(WebSocketOptions option) {
            this();
            this.option.setFrom(option);
        }
    
        public void open(String channel, String name) {
            this.connection.setOnConnect((connection) -> {
                JsonWrapper login = new JsonWrapper();
                login.addJsonString("op", "echo");
                JsonWrapper obj = new JsonWrapper();
                obj.addJsonString("Name", name);
                login.addJsonObject("param", obj);
                connection.send("login");
            });
            this.connection.connect(CEIUtils.stringReplace("/websocket/%s", channel));
        }
    
        public void requestEcho(String name, BigDecimal price, Long number, Boolean status, IWebSocketCallback<SimpleInfo> onEcho) {
            WebSocketEvent onEchoEvent = new WebSocketEvent(false);
            onEchoEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("op", "echo", rootObj);
                JsonWrapper obj = rootObj.getObjectOrNull("param");
                jsonChecker.checkEqual("Name", name, obj);
                return jsonChecker.complete();
            });
            onEchoEvent.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                SimpleInfo simpleInfoVar = new SimpleInfo();
                simpleInfoVar.name = rootObj.getString("Name");
                simpleInfoVar.number = rootObj.getLong("Number");
                simpleInfoVar.price = rootObj.getDecimal("Price");
                simpleInfoVar.status = rootObj.getBoolean("Status");
                onEcho.invoke(simpleInfoVar);
            });
            this.connection.registerEvent(onEchoEvent);
            JsonWrapper json = new JsonWrapper();
            json.addJsonString("op", "echo");
            JsonWrapper obj = new JsonWrapper();
            obj.addJsonString("Name", name);
            obj.addJsonNumber("Price", price);
            obj.addJsonNumber("Number", number);
            obj.addJsonBoolean("Status", status);
            json.addJsonObject("param", obj);
            this.connection.send(json.toJsonString());
        }
    }

    static public class Procedures {
    }

}
