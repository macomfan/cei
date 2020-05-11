package cei.exchanges;

import cei.impl.CEIUtils;
import cei.impl.IWebSocketCallback;
import cei.impl.JsonChecker;
import cei.impl.JsonWrapper;
import cei.impl.RestfulConnection;
import cei.impl.RestfulOptions;
import cei.impl.RestfulRequest;
import cei.impl.RestfulResponse;
import cei.impl.StringWrapper;
import cei.impl.WebSocketConnection;
import cei.impl.WebSocketEvent;
import cei.impl.WebSocketOptions;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class test {

    static public class Order {
        public Long orderId;
    }

    static public class PriceEntity {
        public BigDecimal price;
        public BigDecimal volume;
    }

    static public class InfoEntity {
        public String name;
        public String infoValue;
    }

    static public class SimpleInfo {
        public String name;
        public Long number;
        public BigDecimal price;
        public Boolean status;
    }

    static public class LastTrade {
        public BigDecimal price;
        public BigDecimal volume;
        public Long timestamp;
        public String symbol;
    }

    static public class ModelValue {
        public String name;
        public Long value;
    }

    static public class ModelInfo {
        public String name;
        public ModelValue value;
    }

    static public class PriceList {
        public String name;
        public List<PriceEntity> prices;
    }

    static public class InfoList {
        public String name;
        public List<InfoEntity> values;
    }

    static public class TradeEntity {
        public BigDecimal price;
        public BigDecimal volume;
    }

    static public class HistoricalTrade {
        public List<TradeEntity> data;
    }

    static public class GetClient {
        private final RestfulOptions option;
    
        public GetClient() {
            this.option = new RestfulOptions();
            this.option.url = "http://127.0.0.1:8888";
        }
    
        public GetClient(RestfulOptions option) {
            this();
            this.option.setFrom(option);
        }
    
        public SimpleInfo getSimpleInfo() {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/restful/get/simpleInfo");
            request.setMethod(RestfulRequest.Method.GET);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            SimpleInfo simpleInfoVar = new SimpleInfo();
            simpleInfoVar.name = rootObj.getString("Name");
            simpleInfoVar.number = rootObj.getLong("Number");
            simpleInfoVar.price = rootObj.getDecimal("Price");
            simpleInfoVar.status = rootObj.getBoolean("Status");
            return simpleInfoVar;
        }
    
        public ModelInfo getModelInfo() {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/restful/get/modelInfo");
            request.setMethod(RestfulRequest.Method.GET);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            JsonChecker jsonChecker = new JsonChecker();
            jsonChecker.checkEqual("aaa", "aa", rootObj);
            jsonChecker.reportError();
            ModelInfo modelInfoVar = new ModelInfo();
            modelInfoVar.name = rootObj.getString("Name");
            JsonWrapper obj = rootObj.getObject("DataL1");
            JsonWrapper obj0 = obj.getObject("DataL2");
            ModelValue modelValueVar = new ModelValue();
            JsonWrapper obj1 = obj0.getObject("Value");
            modelValueVar.name = obj1.getString("Name");
            modelValueVar.value = obj1.getLong("Value");
            modelInfoVar.value = modelValueVar;
            return modelInfoVar;
        }
    
        public PriceList getPriceList() {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/restful/get/priceList");
            request.setMethod(RestfulRequest.Method.GET);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            PriceList priceListVar = new PriceList();
            priceListVar.name = rootObj.getString("Name");
            JsonWrapper obj = rootObj.getArray("Prices");
            obj.forEach(item -> {
                PriceEntity priceEntityVar = new PriceEntity();
                priceEntityVar.price = item.getDecimal("[0]");
                priceEntityVar.volume = item.getDecimal("[1]");
                if (priceListVar.prices == null) {
                    priceListVar.prices = new LinkedList<>();
                }
                priceListVar.prices.add(priceEntityVar);
            });
            return priceListVar;
        }
    
        public InfoList getInfoList() {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/restful/get/infoList");
            request.setMethod(RestfulRequest.Method.GET);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            InfoList infoListVar = new InfoList();
            infoListVar.name = rootObj.getString("Name");
            JsonWrapper obj = rootObj.getArray("Values");
            obj.forEach(item -> {
                InfoEntity infoEntityVar = new InfoEntity();
                infoEntityVar.name = item.getString("Name");
                infoEntityVar.infoValue = item.getString("InfoValue");
                if (infoListVar.values == null) {
                    infoListVar.values = new LinkedList<>();
                }
                infoListVar.values.add(infoEntityVar);
            });
            return infoListVar;
        }
    
        public SimpleInfo getTestArray() {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/restful/get/testArray");
            request.setMethod(RestfulRequest.Method.GET);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            SimpleInfo simpleInfoVar = new SimpleInfo();
            simpleInfoVar.name = rootObj.getString("[0]");
            simpleInfoVar.number = rootObj.getLong("[1]");
            simpleInfoVar.price = rootObj.getDecimal("[2]");
            simpleInfoVar.status = rootObj.getBoolean("[3]");
            return simpleInfoVar;
        }
    
        public SimpleInfo inputsByGet(String name, Long number, BigDecimal price, Boolean status) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/restful/get/inputsByGet");
            request.setMethod(RestfulRequest.Method.GET);
            request.addQueryString("Name", name);
            request.addQueryString("Number", Long.toString(number));
            request.addQueryString("Price", price.toString());
            request.addQueryString("Status", status.toString());
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            SimpleInfo simpleInfoVar = new SimpleInfo();
            simpleInfoVar.name = rootObj.getString("Name");
            simpleInfoVar.number = rootObj.getLong("Number");
            simpleInfoVar.price = rootObj.getDecimal("Price");
            simpleInfoVar.status = rootObj.getBoolean("Status");
            return simpleInfoVar;
        }
    
        public String url(String input) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget(CEIUtils.stringReplace("/restful/get/url/%s", input));
            request.setMethod(RestfulRequest.Method.GET);
            RestfulResponse response = RestfulConnection.query(request);
            return response.getString();
        }
    }

    static public class PostClient {
        private final RestfulOptions option;
    
        public PostClient() {
            this.option = new RestfulOptions();
            this.option.url = "http://127.0.0.1:8888";
        }
    
        public PostClient(RestfulOptions option) {
            this();
            this.option.setFrom(option);
        }
    
        public SimpleInfo postInputs(String this_U, BigDecimal price, Long number, Boolean status) {
            RestfulRequest request = new RestfulRequest(this.option);
            JsonWrapper postMsg = new JsonWrapper();
            postMsg.addJsonString("Name", this_U);
            postMsg.addJsonNumber("Price", price);
            postMsg.addJsonNumber("Number", number);
            postMsg.addJsonBoolean("Status_1", status);
            request.setTarget("/restful/post/echo");
            request.setMethod(RestfulRequest.Method.POST);
            request.setPostBody(postMsg.toJsonString());
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            SimpleInfo simpleInfoVar = new SimpleInfo();
            simpleInfoVar.name = rootObj.getString("Name");
            simpleInfoVar.number = rootObj.getLong("Number");
            simpleInfoVar.price = rootObj.getDecimal("Price");
            simpleInfoVar.status = rootObj.getBoolean("Status_1");
            return simpleInfoVar;
        }
    
        public SimpleInfo authentication(String name, Long number) {
            RestfulRequest request = new RestfulRequest(this.option);
            JsonWrapper postMsg = new JsonWrapper();
            postMsg.addJsonNumber("Number", number);
            request.setTarget("/restful/post/authentication");
            request.setMethod(RestfulRequest.Method.POST);
            request.addQueryString("Name", name);
            request.setPostBody(postMsg.toJsonString());
            Procedures.restfulAuth(request, this.option);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            SimpleInfo simpleInfoVar = new SimpleInfo();
            simpleInfoVar.name = rootObj.getString("Name");
            simpleInfoVar.number = rootObj.getLong("Number");
            simpleInfoVar.price = rootObj.getDecimal("Price");
            return simpleInfoVar;
        }
    }

    static public class WSClient {
        private final WebSocketOptions option;
        private final WebSocketConnection connection;
    
        public WSClient() {
            this.option = new WebSocketOptions();
            this.option.url = "ws://127.0.0.1:8888";
            this.connection = new WebSocketConnection(this.option);
        }
    
        public WSClient(WebSocketOptions option) {
            this();
            this.option.setFrom(option);
        }
    
        public void open(String channel, String name, IWebSocketCallback<WebSocketConnection> onConnect) {
            WebSocketEvent onPingEvent = new WebSocketEvent(true);
            onPingEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("op", "ping", rootObj);
                return jsonChecker.complete();
            });
            onPingEvent.setEvent((connection, msg) -> {
                String ts = CEIUtils.getNow("Unix_ms");
                JsonWrapper jsonResult = new JsonWrapper();
                jsonResult.addJsonString("op", "pong");
                jsonResult.addJsonString("ts", ts);
                connection.send(jsonResult.toJsonString());
            });
            this.connection.registerEvent(onPingEvent);
            this.connection.setOnConnect((connection) -> {
                JsonWrapper login = new JsonWrapper();
                login.addJsonString("op", "login");
                JsonWrapper obj = new JsonWrapper();
                obj.addJsonString("Name", name);
                login.addJsonObject("param", obj);
                JsonWrapper obj0 = new JsonWrapper();
                obj0.addJsonNumber("[]", new BigDecimal("1"));
                obj0.addJsonNumber("[]", new BigDecimal("2"));
                login.addJsonObject("array", obj0);
                connection.send(login.toJsonString());
                onConnect.invoke(connection);
            });
            this.connection.connect(CEIUtils.stringReplace("/websocket/%s", channel));
        }
    
        public void close(IWebSocketCallback<WebSocketConnection> onClose) {
            this.connection.setOnClose((connection) -> {
                onClose.invoke(connection);
            });
            this.connection.close();
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
                JsonWrapper obj = rootObj.getObject("param");
                simpleInfoVar.name = obj.getString("Name");
                simpleInfoVar.number = obj.getLong("Number");
                simpleInfoVar.price = obj.getDecimal("Price");
                simpleInfoVar.status = obj.getBoolean("Status");
                onEcho.invoke(simpleInfoVar);
            });
            this.connection.registerEvent(onEchoEvent);
            JsonWrapper jsonResult = new JsonWrapper();
            jsonResult.addJsonString("op", "echo");
            JsonWrapper obj = new JsonWrapper();
            obj.addJsonString("Name", name);
            obj.addJsonNumber("Price", price);
            obj.addJsonNumber("Number", number);
            obj.addJsonBoolean("Status", status);
            jsonResult.addJsonObject("param", obj);
            this.connection.send(jsonResult.toJsonString());
        }
    
        public void subscribeSecond1(IWebSocketCallback<SimpleInfo> onSecond1) {
            WebSocketEvent onSecond1Event = new WebSocketEvent(true);
            onSecond1Event.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("ch", "Second1", rootObj);
                return jsonChecker.complete();
            });
            onSecond1Event.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                SimpleInfo simpleInfoVar = new SimpleInfo();
                simpleInfoVar.name = rootObj.getString("Name");
                simpleInfoVar.number = rootObj.getLong("Number");
                simpleInfoVar.price = rootObj.getDecimal("Price");
                simpleInfoVar.status = rootObj.getBoolean("Status");
                onSecond1.invoke(simpleInfoVar);
            });
            this.connection.registerEvent(onSecond1Event);
            JsonWrapper jsonResult = new JsonWrapper();
            jsonResult.addJsonString("op", "sub");
            jsonResult.addJsonString("name", "Second1");
            this.connection.send(jsonResult.toJsonString());
        }
    }

    static public class Procedures {
    
        public static void restfulAuth(RestfulRequest request, RestfulOptions option) {
            String timestamp = CEIUtils.getNow("yyyy':'MM':'dd'T'HH':'mm':'ss");
            request.addQueryString("AccessKeyId", option.apiKey);
            request.addQueryString("SignatureMethod", "HmacSHA256");
            request.addQueryString("SignatureVersion", "2");
            request.addQueryString("Timestamp", timestamp);
            String queryString = CEIUtils.combineQueryString(request, CEIUtils.Constant.ASC, "&");
            String method = CEIUtils.getRequestInfo(request, CEIUtils.Constant.METHOD, CEIUtils.Constant.UPPERCASE);
            String host = CEIUtils.getRequestInfo(request, CEIUtils.Constant.HOST, CEIUtils.Constant.NONE);
            String target = CEIUtils.getRequestInfo(request, CEIUtils.Constant.TARGET, CEIUtils.Constant.NONE);
            StringWrapper buffer = new StringWrapper();
            buffer.appendStringItem(method);
            buffer.appendStringItem(host);
            buffer.appendStringItem(target);
            buffer.appendStringItem(queryString);
            buffer.combineStringItems("", "", "\\n");
            byte[] hmacsha256 = CEIUtils.hmacsha256(buffer.toNormalString(), option.secretKey);
            String result = CEIUtils.base64(hmacsha256);
            request.addQueryString("Signature", result);
        }
    }

}
