package cn.ma.cei.exchanges;

import cn.ma.cei.impl.CEIUtils;
import cn.ma.cei.impl.IWebSocketCallback;
import cn.ma.cei.impl.JsonChecker;
import cn.ma.cei.impl.JsonWrapper;
import cn.ma.cei.impl.RestfulConnection;
import cn.ma.cei.impl.RestfulOptions;
import cn.ma.cei.impl.RestfulRequest;
import cn.ma.cei.impl.RestfulResponse;
import cn.ma.cei.impl.StringWrapper;
import cn.ma.cei.impl.WebSocketConnection;
import cn.ma.cei.impl.WebSocketEvent;
import cn.ma.cei.impl.WebSocketOptions;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class huobipro {

    static public class SymbolsData {
        public String baseCurrency;
        public String quoteCurrency;
        public Long pricePrecision;
        public Long amountPrecision;
        public String symbolPartition;
        public String symbol;
        public String state;
        public Long valuePrecision;
        public BigDecimal minOrderAmt;
        public BigDecimal maxOrderAmt;
        public BigDecimal minOrderValue;
        public Long leverageRatio;
    }

    static public class TradeClearingUpdate {
        public String symbol;
        public Long orderId;
        public String tradePrice;
        public String tradeVolume;
        public String orderSide;
        public String orderType;
        public Boolean aggressor;
        public Long tradeId;
        public String tradeTime;
        public String transactFee;
        public String feeDeduct;
        public String feeDeductType;
    }

    static public class CandlestickData {
        public Long id;
        public BigDecimal amount;
        public Long count;
        public BigDecimal open;
        public BigDecimal close;
        public BigDecimal low;
        public BigDecimal high;
        public BigDecimal vol;
    }

    static public class OrderID {
        public String status;
        public Long data;
    }

    static public class Timestamp {
        public Long timestamp;
    }

    static public class AggregatedMarketData {
        public Long id;
        public BigDecimal amount;
        public Long count;
        public BigDecimal open;
        public BigDecimal close;
        public BigDecimal low;
        public BigDecimal high;
        public BigDecimal vol;
        public BigDecimal bidPrice;
        public BigDecimal bidSize;
        public BigDecimal askPrice;
        public BigDecimal askSize;
    }

    static public class Code {
        public Long code;
    }

    static public class Currencies {
        public String status;
        public List<String> data;
    }

    static public class Quote {
        public BigDecimal price;
        public BigDecimal amount;
    }

    static public class Depth {
        public String status;
        public String ch;
        public Long ts;
        public List<Quote> bids;
        public List<Quote> asks;
    }

    static public class Trade {
        public Long id;
        public Long tradeID;
        public BigDecimal price;
        public BigDecimal amount;
        public String direction;
        public Long ts;
    }

    static public class Candlestick {
        public String status;
        public List<CandlestickData> data;
    }

    static public class OrderUpdate {
        public String eventType;
        public String symbol;
        public Long orderId;
        public String clientOrderId;
        public String orderPrice;
        public String orderSize;
        public String type;
        public String orderStatus;
        public Long orderCreateTime;
        public String tradePrice;
        public String tradeVolume;
        public Long tradeId;
        public Long tradeTime;
        public Boolean aggressor;
        public String remainAmt;
        public String lastActTime;
    }

    static public class AccountData {
        public Long id;
        public String state;
        public String type;
        public String subtype;
    }

    static public class Account {
        public String status;
        public List<AccountData> data;
    }

    static public class Symbols {
        public String status;
        public List<SymbolsData> data;
    }

    static public class LastTrade {
        public String status;
        public String ch;
        public Long ts;
        public Long tsInTick;
        public Long idInTick;
        public List<Trade> data;
    }

    static public class BestQuote {
        public String status;
        public String ch;
        public Long ts;
        public BigDecimal bestBidPrice;
        public BigDecimal bestBidAmount;
        public BigDecimal bestAskPrice;
        public BigDecimal bestAskAmount;
    }

    static public class MarketClient {
        private final RestfulOptions option;
    
        public MarketClient() {
            this.option = new RestfulOptions();
            this.option.url = "https://api.huobi.so";
        }
    
        public MarketClient(RestfulOptions option) {
            this();
            this.option.setFrom(option);
        }
    
        public Timestamp getTimestamp() {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/v1/common/timestamp");
            request.setMethod(RestfulRequest.Method.GET);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            JsonChecker jsonChecker = new JsonChecker();
            jsonChecker.checkNotEqual("stauts", "ok", rootObj);
            jsonChecker.reportError();
            Timestamp timestampVar = new Timestamp();
            timestampVar.timestamp = rootObj.getLong("data");
            return timestampVar;
        }
    
        public Symbols getSymbol() {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/v1/common/symbols");
            request.setMethod(RestfulRequest.Method.GET);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            JsonChecker jsonChecker = new JsonChecker();
            jsonChecker.checkNotEqual("stauts", "ok", rootObj);
            jsonChecker.reportError();
            Symbols symbolsVar = new Symbols();
            symbolsVar.status = rootObj.getString("status");
            JsonWrapper obj = rootObj.getArray("data");
            obj.forEach(item -> {
                SymbolsData symbolsDataVar = new SymbolsData();
                symbolsDataVar.baseCurrency = item.getString("base-currency");
                symbolsDataVar.quoteCurrency = item.getString("quote-currency");
                symbolsDataVar.pricePrecision = item.getLong("price-precision");
                symbolsDataVar.amountPrecision = item.getLong("amount-precision");
                symbolsDataVar.symbolPartition = item.getString("symbol-partition");
                symbolsDataVar.symbol = item.getString("symbol");
                symbolsDataVar.state = item.getString("state");
                symbolsDataVar.valuePrecision = item.getLong("value-precision");
                symbolsDataVar.minOrderAmt = item.getDecimal("min-order-amt");
                symbolsDataVar.maxOrderAmt = item.getDecimal("max-order-amt");
                symbolsDataVar.minOrderValue = item.getDecimal("min-order-value");
                symbolsDataVar.leverageRatio = item.getLong("leverage-ratio");
                if (symbolsVar.data == null) {
                    symbolsVar.data = new LinkedList<>();
                }
                symbolsVar.data.add(symbolsDataVar);
            });
            return symbolsVar;
        }
    
        public Currencies getCurrencies() {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/v1/common/currencys");
            request.setMethod(RestfulRequest.Method.GET);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            Currencies currenciesVar = new Currencies();
            currenciesVar.status = rootObj.getString("status");
            currenciesVar.data = rootObj.getStringArray("data");
            return currenciesVar;
        }
    
        public Candlestick getCandlestick(String symbol, String period, Long size) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/market/history/kline");
            request.setMethod(RestfulRequest.Method.GET);
            request.addQueryString("symbol", symbol);
            request.addQueryString("period", period);
            request.addQueryString("size", Long.toString(size));
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            Candlestick candlestickVar = new Candlestick();
            candlestickVar.status = rootObj.getString("status");
            JsonWrapper obj = rootObj.getArray("data");
            obj.forEach(item -> {
                CandlestickData candlestickDataVar = new CandlestickData();
                candlestickDataVar.id = item.getLong("id");
                candlestickDataVar.amount = item.getDecimal("amount");
                candlestickDataVar.count = item.getLong("count");
                candlestickDataVar.open = item.getDecimal("open");
                candlestickDataVar.close = item.getDecimal("close");
                candlestickDataVar.low = item.getDecimal("low");
                candlestickDataVar.high = item.getDecimal("high");
                candlestickDataVar.vol = item.getDecimal("vol");
                if (candlestickVar.data == null) {
                    candlestickVar.data = new LinkedList<>();
                }
                candlestickVar.data.add(candlestickDataVar);
            });
            return candlestickVar;
        }
    
        public LastTrade getLastTrade(String symbol) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/market/trade");
            request.setMethod(RestfulRequest.Method.GET);
            request.addQueryString("symbol", symbol);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            LastTrade lastTradeVar = new LastTrade();
            lastTradeVar.status = rootObj.getString("status");
            lastTradeVar.ts = rootObj.getLong("ts");
            lastTradeVar.ch = rootObj.getString("ch");
            JsonWrapper obj = rootObj.getObject("tick");
            lastTradeVar.tsInTick = obj.getLong("ts");
            lastTradeVar.idInTick = obj.getLong("id");
            JsonWrapper obj0 = obj.getArray("data");
            obj0.forEach(item -> {
                Trade tradeVar = new Trade();
                tradeVar.amount = item.getDecimal("amount");
                tradeVar.price = item.getDecimal("price");
                tradeVar.ts = item.getLong("ts");
                tradeVar.id = item.getLong("id");
                tradeVar.direction = item.getString("direction");
                tradeVar.tradeID = item.getLong("trade-id");
                if (lastTradeVar.data == null) {
                    lastTradeVar.data = new LinkedList<>();
                }
                lastTradeVar.data.add(tradeVar);
            });
            return lastTradeVar;
        }
    
        public Depth getMarketDepth(String symbol, Long depth, String mergeType) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/market/depth");
            request.setMethod(RestfulRequest.Method.GET);
            request.addQueryString("symbol", symbol);
            request.addQueryString("depth", Long.toString(depth));
            request.addQueryString("type", mergeType);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            Depth depthVar = new Depth();
            depthVar.status = rootObj.getString("status");
            depthVar.ts = rootObj.getLong("ts");
            depthVar.ch = rootObj.getString("ch");
            JsonWrapper obj = rootObj.getObject("tick");
            JsonWrapper obj0 = obj.getArray("bids");
            obj0.forEach(item -> {
                Quote quoteVar = new Quote();
                quoteVar.price = item.getDecimal("[0]");
                quoteVar.amount = item.getDecimal("[1]");
                if (depthVar.bids == null) {
                    depthVar.bids = new LinkedList<>();
                }
                depthVar.bids.add(quoteVar);
            });
            JsonWrapper obj1 = obj.getArray("asks");
            obj1.forEach(item2 -> {
                Quote quoteVar3 = new Quote();
                quoteVar3.price = item2.getDecimal("[0]");
                quoteVar3.amount = item2.getDecimal("[1]");
                if (depthVar.asks == null) {
                    depthVar.asks = new LinkedList<>();
                }
                depthVar.asks.add(quoteVar3);
            });
            return depthVar;
        }
    
        public BestQuote getBestQuote(String symbol) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/market/depth");
            request.setMethod(RestfulRequest.Method.GET);
            request.addQueryString("symbol", symbol);
            request.addQueryString("depth", "5");
            request.addQueryString("type", "step0");
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            BestQuote bestQuoteVar = new BestQuote();
            bestQuoteVar.status = rootObj.getString("status");
            bestQuoteVar.ts = rootObj.getLong("ts");
            JsonWrapper obj = rootObj.getObject("tick");
            JsonWrapper obj0 = obj.getObject("bids");
            JsonWrapper obj1 = obj0.getObject("[0]");
            bestQuoteVar.bestBidPrice = obj1.getDecimal("[0]");
            bestQuoteVar.bestBidAmount = obj1.getDecimal("[1]");
            JsonWrapper obj2 = obj.getObject("asks");
            JsonWrapper obj3 = obj2.getObject("[0]");
            bestQuoteVar.bestAskPrice = obj3.getDecimal("[0]");
            bestQuoteVar.bestAskAmount = obj3.getDecimal("[1]");
            bestQuoteVar.ch = rootObj.getString("ch");
            return bestQuoteVar;
        }
    
        public AggregatedMarketData getLatestAggregatedTicker(String symbol) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/market/detail/merged");
            request.setMethod(RestfulRequest.Method.GET);
            request.addQueryString("symbol", symbol);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            AggregatedMarketData aggregatedMarketDataVar = new AggregatedMarketData();
            JsonWrapper obj = rootObj.getObject("data");
            aggregatedMarketDataVar.id = obj.getLong("id");
            aggregatedMarketDataVar.amount = obj.getDecimal("amount");
            aggregatedMarketDataVar.count = obj.getLong("count");
            aggregatedMarketDataVar.open = obj.getDecimal("open");
            aggregatedMarketDataVar.close = obj.getDecimal("close");
            aggregatedMarketDataVar.low = obj.getDecimal("low");
            aggregatedMarketDataVar.high = obj.getDecimal("high");
            aggregatedMarketDataVar.vol = obj.getDecimal("vol");
            JsonWrapper obj0 = obj.getObject("bid");
            aggregatedMarketDataVar.bidPrice = obj0.getDecimal("[0]");
            aggregatedMarketDataVar.bidSize = obj0.getDecimal("[1]");
            JsonWrapper obj1 = obj.getObject("ask");
            aggregatedMarketDataVar.askPrice = obj1.getDecimal("[0]");
            aggregatedMarketDataVar.askSize = obj1.getDecimal("[1]");
            return aggregatedMarketDataVar;
        }
    }

    static public class TradingClient {
        private final RestfulOptions option;
    
        public TradingClient() {
            this.option = new RestfulOptions();
            this.option.url = "https://api.huobi.so";
        }
    
        public TradingClient(RestfulOptions option) {
            this();
            this.option.setFrom(option);
        }
    
        public OrderID placeOrder(String accountId, String symbol, String orderType, String amount, String price) {
            RestfulRequest request = new RestfulRequest(this.option);
            JsonWrapper postMsg = new JsonWrapper();
            postMsg.addJsonString("account-Id", accountId);
            postMsg.addJsonString("symbol", symbol);
            postMsg.addJsonString("orderType", orderType);
            postMsg.addJsonString("amount", amount);
            postMsg.addJsonString("price", price);
            request.setTarget("/v1/order/orders/place");
            request.setMethod(RestfulRequest.Method.POST);
            request.setPostBody(postMsg.toJsonString());
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            OrderID orderIDVar = new OrderID();
            orderIDVar.status = rootObj.getString("status");
            orderIDVar.data = rootObj.getLong("data");
            return orderIDVar;
        }
    
        public OrderID cancelOrder(Long orderId) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget(CEIUtils.stringReplace("/v1/order/orders/%s/submitcancel", Long.toString(orderId)));
            request.setMethod(RestfulRequest.Method.POST);
            Procedures.restfulAuth(request, this.option);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            OrderID orderIDVar = new OrderID();
            orderIDVar.status = rootObj.getString("status");
            orderIDVar.data = rootObj.getLong("data");
            return orderIDVar;
        }
    }

    static public class AccountClient {
        private final RestfulOptions option;
    
        public AccountClient() {
            this.option = new RestfulOptions();
            this.option.url = "https://api.huobi.so";
        }
    
        public AccountClient(RestfulOptions option) {
            this();
            this.option.setFrom(option);
        }
    
        public Account getAccounts() {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/v1/account/accounts");
            request.setMethod(RestfulRequest.Method.GET);
            Procedures.restfulAuth(request, this.option);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            Account accountVar = new Account();
            accountVar.status = rootObj.getString("status");
            AccountData accountDataVar = new AccountData();
            JsonWrapper obj = rootObj.getObject("data");
            accountDataVar.id = obj.getLong("id");
            accountDataVar.state = obj.getString("state");
            accountDataVar.type = obj.getString("type");
            accountDataVar.subtype = obj.getString("subtype");
            return accountVar;
        }
    }

    static public class MarketChannelClient {
        private final WebSocketOptions option;
        private final WebSocketConnection connection;
    
        public MarketChannelClient() {
            this.option = new WebSocketOptions();
            this.option.url = "wss://api.huobi.pro/ws";
            this.connection = new WebSocketConnection(this.option);
        }
    
        public MarketChannelClient(WebSocketOptions option) {
            this();
            this.option.setFrom(option);
        }
    
        public void open() {
            WebSocketEvent onAnyMessageEvent = new WebSocketEvent(true);
            onAnyMessageEvent.setTrigger((msg) -> {
                return true;
            });
            onAnyMessageEvent.setEvent((connection, msg) -> {
                String decoded = CEIUtils.gzip(msg.getBytes());
                msg.upgrade(decoded);
            });
            this.connection.registerEvent(onAnyMessageEvent);
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
            this.connection.connect("");
        }
    
        public void subscriptCandlestick(String symbol, String period, IWebSocketCallback<CandlestickData> onCandlestick) {
            WebSocketEvent onCandlestickEvent = new WebSocketEvent(true);
            onCandlestickEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("ch", CEIUtils.stringReplace("market.%s.kline.%s", symbol, period), rootObj);
                return jsonChecker.complete();
            });
            onCandlestickEvent.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                CandlestickData candlestickDataVar = new CandlestickData();
                JsonWrapper obj = rootObj.getObject("tick");
                candlestickDataVar.id = obj.getLong("id");
                candlestickDataVar.amount = obj.getDecimal("amount");
                candlestickDataVar.count = obj.getLong("count");
                candlestickDataVar.open = obj.getDecimal("open");
                candlestickDataVar.close = obj.getDecimal("close");
                candlestickDataVar.low = obj.getDecimal("low");
                candlestickDataVar.high = obj.getDecimal("high");
                candlestickDataVar.vol = obj.getDecimal("vol");
                onCandlestick.invoke(candlestickDataVar);
            });
            this.connection.registerEvent(onCandlestickEvent);
            String ts = CEIUtils.getNow("Unix_ms");
            JsonWrapper json = new JsonWrapper();
            json.addJsonString("sub", CEIUtils.stringReplace("market.%s.kline.%s", symbol, period));
            json.addJsonString("id", ts);
            this.connection.send(json.toJsonString());
        }
    
        public void requestCandlestick(String symbol, String period, IWebSocketCallback<Candlestick> onCandlestick) {
            WebSocketEvent onCandlestickEvent = new WebSocketEvent(false);
            onCandlestickEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("rep", CEIUtils.stringReplace("market.%s.kline.%s", symbol, period), rootObj);
                return jsonChecker.complete();
            });
            onCandlestickEvent.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                Candlestick candlestickVar = new Candlestick();
                JsonWrapper obj = rootObj.getArray("data");
                obj.forEach(item -> {
                    CandlestickData candlestickDataVar = new CandlestickData();
                    candlestickDataVar.id = item.getLong("id");
                    candlestickDataVar.amount = item.getDecimal("amount");
                    candlestickDataVar.count = item.getLong("count");
                    candlestickDataVar.open = item.getDecimal("open");
                    candlestickDataVar.close = item.getDecimal("close");
                    candlestickDataVar.low = item.getDecimal("low");
                    candlestickDataVar.high = item.getDecimal("high");
                    candlestickDataVar.vol = item.getDecimal("vol");
                    if (candlestickVar.data == null) {
                        candlestickVar.data = new LinkedList<>();
                    }
                    candlestickVar.data.add(candlestickDataVar);
                });
                onCandlestick.invoke(candlestickVar);
            });
            this.connection.registerEvent(onCandlestickEvent);
            String ts = CEIUtils.getNow("Unix_ms");
            JsonWrapper json = new JsonWrapper();
            json.addJsonString("req", CEIUtils.stringReplace("market.%s.kline.%s", symbol, period));
            json.addJsonString("id", ts);
            this.connection.send(json.toJsonString());
        }
    
        public void subscriptMarketDepth(String symbol, String type, IWebSocketCallback<Depth> onDepth) {
            WebSocketEvent onDepthEvent = new WebSocketEvent(true);
            onDepthEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("ch", CEIUtils.stringReplace("market.%s.depth.%s", symbol, type), rootObj);
                return jsonChecker.complete();
            });
            onDepthEvent.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                Depth depthVar = new Depth();
                depthVar.ch = rootObj.getString("ch");
                JsonWrapper obj = rootObj.getObject("tick");
                JsonWrapper obj0 = obj.getArray("bids");
                obj0.forEach(item -> {
                    Quote quoteVar = new Quote();
                    quoteVar.price = item.getDecimal("[0]");
                    quoteVar.amount = item.getDecimal("[1]");
                    if (depthVar.bids == null) {
                        depthVar.bids = new LinkedList<>();
                    }
                    depthVar.bids.add(quoteVar);
                });
                JsonWrapper obj1 = obj.getArray("asks");
                obj1.forEach(item2 -> {
                    Quote quoteVar3 = new Quote();
                    quoteVar3.price = item2.getDecimal("[0]");
                    quoteVar3.amount = item2.getDecimal("[1]");
                    if (depthVar.asks == null) {
                        depthVar.asks = new LinkedList<>();
                    }
                    depthVar.asks.add(quoteVar3);
                });
                onDepth.invoke(depthVar);
            });
            this.connection.registerEvent(onDepthEvent);
            String ts = CEIUtils.getNow("Unix_ms");
            JsonWrapper json = new JsonWrapper();
            json.addJsonString("sub", CEIUtils.stringReplace("market.%s.depth.%s", symbol, type));
            json.addJsonString("id", ts);
            this.connection.send(json.toJsonString());
        }
    
        public void requestDepth(String symbol, String type, IWebSocketCallback<Depth> onDepth) {
            WebSocketEvent onDepthEvent = new WebSocketEvent(true);
            onDepthEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("ch", CEIUtils.stringReplace("market.%s.depth.%s", symbol, type), rootObj);
                return jsonChecker.complete();
            });
            onDepthEvent.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                Depth depthVar = new Depth();
                depthVar.ch = rootObj.getString("ch");
                JsonWrapper obj = rootObj.getObject("tick");
                JsonWrapper obj0 = obj.getArray("bids");
                obj0.forEach(item -> {
                    Quote quoteVar = new Quote();
                    quoteVar.price = item.getDecimal("[0]");
                    quoteVar.amount = item.getDecimal("[1]");
                    if (depthVar.bids == null) {
                        depthVar.bids = new LinkedList<>();
                    }
                    depthVar.bids.add(quoteVar);
                });
                JsonWrapper obj1 = obj.getArray("asks");
                obj1.forEach(item2 -> {
                    Quote quoteVar3 = new Quote();
                    quoteVar3.price = item2.getDecimal("[0]");
                    quoteVar3.amount = item2.getDecimal("[1]");
                    if (depthVar.asks == null) {
                        depthVar.asks = new LinkedList<>();
                    }
                    depthVar.asks.add(quoteVar3);
                });
                onDepth.invoke(depthVar);
            });
            this.connection.registerEvent(onDepthEvent);
            String ts = CEIUtils.getNow("Unix_ms");
            JsonWrapper json = new JsonWrapper();
            json.addJsonString("sub", CEIUtils.stringReplace("market.%s.depth.%s", symbol, type));
            json.addJsonString("id", ts);
            this.connection.send(json.toJsonString());
        }
    }

    static public class AssetOrderV2ChannelClient {
        private final WebSocketOptions option;
        private final WebSocketConnection connection;
    
        public AssetOrderV2ChannelClient() {
            this.option = new WebSocketOptions();
            this.option.url = "wss://api.huobi.pro/ws/v2";
            this.connection = new WebSocketConnection(this.option);
        }
    
        public AssetOrderV2ChannelClient(WebSocketOptions option) {
            this();
            this.option.setFrom(option);
        }
    
        public void open() {
            WebSocketEvent onPingEvent = new WebSocketEvent(true);
            onPingEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("action", "ping", rootObj);
                return jsonChecker.complete();
            });
            onPingEvent.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                Timestamp ts = new Timestamp();
                JsonWrapper obj = rootObj.getObject("data");
                ts.timestamp = obj.getLong("ts");
                JsonWrapper jsonResult = new JsonWrapper();
                jsonResult.addJsonString("op", "pong");
                jsonResult.addJsonNumber("ts", ts.timestamp);
                connection.send(jsonResult.toJsonString());
            });
            this.connection.registerEvent(onPingEvent);
            this.connection.connect("");
        }
    
        public void setHandler(IWebSocketCallback<OrderUpdate> onOrder, IWebSocketCallback<TradeClearingUpdate> onTrade) {
            WebSocketEvent onOrderEvent = new WebSocketEvent(true);
            onOrderEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("action", "push", rootObj);
                jsonChecker.valueInclude("ch", "order.", rootObj);
                return jsonChecker.complete();
            });
            onOrderEvent.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                OrderUpdate orderUpdateVar = new OrderUpdate();
                JsonWrapper obj = rootObj.getObject("data");
                orderUpdateVar.eventType = obj.getStringOrNull("eventType");
                orderUpdateVar.symbol = obj.getStringOrNull("symbol");
                orderUpdateVar.orderId = obj.getLongOrNull("orderId");
                orderUpdateVar.clientOrderId = obj.getStringOrNull("clientOrderId");
                orderUpdateVar.type = obj.getStringOrNull("type");
                orderUpdateVar.orderPrice = obj.getStringOrNull("orderPrice");
                orderUpdateVar.orderSize = obj.getStringOrNull("orderSize");
                orderUpdateVar.type = obj.getStringOrNull("type");
                orderUpdateVar.orderStatus = obj.getStringOrNull("orderStatus");
                orderUpdateVar.orderCreateTime = obj.getLongOrNull("orderCreateTime");
                orderUpdateVar.tradePrice = obj.getStringOrNull("tradePrice");
                orderUpdateVar.tradeVolume = obj.getStringOrNull("tradeVolume");
                orderUpdateVar.tradeId = obj.getLongOrNull("tradeId");
                orderUpdateVar.tradeTime = obj.getLongOrNull("tradeTime");
                orderUpdateVar.aggressor = obj.getBooleanOrNull("aggressor");
                orderUpdateVar.remainAmt = obj.getStringOrNull("remainAmt");
                orderUpdateVar.lastActTime = obj.getStringOrNull("lastActTime");
                onOrder.invoke(orderUpdateVar);
            });
            this.connection.registerEvent(onOrderEvent);
            WebSocketEvent onTradeEvent = new WebSocketEvent(true);
            onTradeEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("action", "push", rootObj);
                jsonChecker.valueInclude("ch", "trade.clearing.", rootObj);
                return jsonChecker.complete();
            });
            onTradeEvent.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                TradeClearingUpdate tradeClearingUpdateVar = new TradeClearingUpdate();
                JsonWrapper obj = rootObj.getObject("data");
                tradeClearingUpdateVar.symbol = obj.getStringOrNull("symbol");
                tradeClearingUpdateVar.orderId = obj.getLongOrNull("orderId");
                tradeClearingUpdateVar.tradePrice = obj.getStringOrNull("tradePrice");
                tradeClearingUpdateVar.tradeVolume = obj.getStringOrNull("tradeVolume");
                tradeClearingUpdateVar.orderSide = obj.getStringOrNull("orderSide");
                tradeClearingUpdateVar.orderType = obj.getStringOrNull("orderType");
                tradeClearingUpdateVar.aggressor = obj.getBooleanOrNull("aggressor");
                tradeClearingUpdateVar.tradeId = obj.getLongOrNull("tradeId");
                tradeClearingUpdateVar.tradeTime = obj.getStringOrNull("tradeTime");
                tradeClearingUpdateVar.transactFee = obj.getStringOrNull("transactFee");
                tradeClearingUpdateVar.feeDeduct = obj.getStringOrNull("feeDeduct");
                tradeClearingUpdateVar.feeDeductType = obj.getStringOrNull("feeDeductType");
                onTrade.invoke(tradeClearingUpdateVar);
            });
            this.connection.registerEvent(onTradeEvent);
        }
    
        public void subscriptOrder(String symbol, IWebSocketCallback<Code> onSub) {
            WebSocketEvent onSubEvent = new WebSocketEvent(false);
            onSubEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("action", "sub", rootObj);
                jsonChecker.valueInclude("ch", "orders", rootObj);
                return jsonChecker.complete();
            });
            onSubEvent.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                Code codeVar = new Code();
                codeVar.code = rootObj.getLong("code");
                onSub.invoke(codeVar);
            });
            this.connection.registerEvent(onSubEvent);
            JsonWrapper json = new JsonWrapper();
            json.addJsonString("action", "sub");
            json.addJsonString("ch", CEIUtils.stringReplace("orders#%s", symbol));
            this.connection.send(json.toJsonString());
        }
    
        public void subscriptTradeClearing(String symbol, IWebSocketCallback<Code> onSub) {
            WebSocketEvent onSubEvent = new WebSocketEvent(false);
            onSubEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("action", "sub", rootObj);
                jsonChecker.valueInclude("ch", "trade.clearing", rootObj);
                return jsonChecker.complete();
            });
            onSubEvent.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                Code codeVar = new Code();
                codeVar.code = rootObj.getLong("code");
                onSub.invoke(codeVar);
            });
            this.connection.registerEvent(onSubEvent);
            JsonWrapper json = new JsonWrapper();
            json.addJsonString("action", "sub");
            json.addJsonString("ch", CEIUtils.stringReplace("trade.clearing#%s", symbol));
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
            buffer.combineStringItems("\\n");
            byte[] hmacsha256 = CEIUtils.hmacsha256(buffer.toNormalString(), option.secretKey);
            String result = CEIUtils.base64(hmacsha256);
            request.addQueryString("Signature", result);
        }
    }

}
