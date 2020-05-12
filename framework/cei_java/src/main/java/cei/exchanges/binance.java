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

public class binance {

    static public class CandlestickStream {
        public String symbol;
    }

    static public class RateLimits {
        public String rateLimitType;
        public String interval;
        public Long intervalNum;
        public Long limit;
    }

    static public class Symbol {
        public String symbol;
        public String status;
        public String baseAsset;
        public Long baseAssetPrecision;
        public String quoteAsset;
        public Long quotePrecision;
        public List<String> orderTypes;
        public Boolean icebergAllowed;
        public Boolean ocoAllowed;
    }

    static public class CandlestickData {
        public Long openTime;
        public BigDecimal open;
        public BigDecimal high;
        public BigDecimal low;
        public BigDecimal close;
        public BigDecimal volume;
        public Long closeTime;
        public BigDecimal quoteAssetVolume;
        public Long numberOfTrades;
        public BigDecimal takerBuyBaseAssetVolume;
        public BigDecimal takerBuyQuoteAssetVolume;
    }

    static public class CandlestickDataList {
        public List<CandlestickData> data;
    }

    static public class AggregateTrade {
        public Long id;
        public BigDecimal price;
        public BigDecimal qty;
        public Long timestamp;
        public Long firstTradeId;
        public Long lastTradeId;
        public Boolean isBuyerMaker;
        public Boolean isBestMatch;
    }

    static public class ExchangeInfo {
        public String timezone;
        public Long serverTime;
        public List<RateLimits> rateLimits;
        public List<Symbol> symbols;
    }

    static public class Timestamp {
        public Long serverTime;
    }

    static public class Trade {
        public Long id;
        public BigDecimal price;
        public BigDecimal qty;
        public BigDecimal quoteQty;
        public Long time;
        public Boolean isBuyerMaker;
        public Boolean isBestMatch;
    }

    static public class TradeList {
        public List<Trade> data;
    }

    static public class DepthEntity {
        public BigDecimal price;
        public BigDecimal qty;
    }

    static public class Depth {
        public Long lastUpdateId;
        public List<DepthEntity> bids;
        public List<DepthEntity> asks;
    }

    static public class AggregateTradeList {
        public List<AggregateTrade> data;
    }

    static public class MarketClient {
        private final RestfulOptions option;
    
        public MarketClient() {
            this.option = new RestfulOptions();
            this.option.url = "https://api.binance.com";
        }
    
        public MarketClient(RestfulOptions option) {
            this();
            this.option.setFrom(option);
        }
    
        public Timestamp getTimestamp() {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/api/v3/time");
            request.setMethod(RestfulRequest.Method.GET);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            Timestamp timestampVar = new Timestamp();
            timestampVar.serverTime = rootObj.getLong("serverTime");
            return timestampVar;
        }
    
        public AggregateTradeList getAggregateTrades(String symbol, Long fromId, Long startTime, Long endTime, Long limit) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/api/v3/aggTrades");
            request.setMethod(RestfulRequest.Method.GET);
            request.addQueryString("symbol", symbol);
            request.addQueryString("fromId", Long.toString(fromId));
            request.addQueryString("startTime", Long.toString(startTime));
            request.addQueryString("startTime", Long.toString(endTime));
            request.addQueryString("limit", Long.toString(limit));
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            AggregateTradeList aggregateTradeListVar = new AggregateTradeList();
            rootObj.forEach(item -> {
                AggregateTrade aggregateTradeVar = new AggregateTrade();
                aggregateTradeVar.id = item.getLong("a");
                aggregateTradeVar.price = item.getDecimal("p");
                aggregateTradeVar.qty = item.getDecimal("q");
                aggregateTradeVar.firstTradeId = item.getLong("f");
                aggregateTradeVar.lastTradeId = item.getLong("l");
                aggregateTradeVar.timestamp = item.getLong("T");
                aggregateTradeVar.isBuyerMaker = item.getBoolean("m");
                aggregateTradeVar.isBestMatch = item.getBoolean("M");
                if (aggregateTradeListVar.data == null) {
                    aggregateTradeListVar.data = new LinkedList<>();
                }
                aggregateTradeListVar.data.add(aggregateTradeVar);
            });
            return aggregateTradeListVar;
        }
    
        public TradeList getTrade(String symbol, Long limit) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/api/v3/trades");
            request.setMethod(RestfulRequest.Method.GET);
            request.addQueryString("symbol", symbol);
            request.addQueryString("limit", Long.toString(limit));
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            TradeList tradeListVar = new TradeList();
            rootObj.forEach(item -> {
                Trade tradeVar = new Trade();
                tradeVar.id = item.getLong("id");
                tradeVar.price = item.getDecimal("price");
                tradeVar.qty = item.getDecimal("qty");
                tradeVar.quoteQty = item.getDecimal("quoteQty");
                tradeVar.time = item.getLong("time");
                tradeVar.isBuyerMaker = item.getBoolean("isBuyerMaker");
                tradeVar.isBestMatch = item.getBoolean("isBestMatch");
                if (tradeListVar.data == null) {
                    tradeListVar.data = new LinkedList<>();
                }
                tradeListVar.data.add(tradeVar);
            });
            return tradeListVar;
        }
    
        public TradeList historicalTrades(String symbol, Long limit, Long fromId) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/api/v3/historicalTrades");
            request.setMethod(RestfulRequest.Method.GET);
            request.addQueryString("symbol", symbol);
            request.addQueryString("limit", Long.toString(limit));
            request.addQueryString("fromId", Long.toString(fromId));
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            TradeList tradeListVar = new TradeList();
            rootObj.forEach(item -> {
                Trade tradeVar = new Trade();
                tradeVar.id = item.getLong("id");
                tradeVar.price = item.getDecimal("price");
                tradeVar.qty = item.getDecimal("qty");
                tradeVar.quoteQty = item.getDecimal("quoteQty");
                tradeVar.time = item.getLong("time");
                tradeVar.isBuyerMaker = item.getBoolean("isBuyerMaker");
                tradeVar.isBestMatch = item.getBoolean("isBestMatch");
                if (tradeListVar.data == null) {
                    tradeListVar.data = new LinkedList<>();
                }
                tradeListVar.data.add(tradeVar);
            });
            return tradeListVar;
        }
    
        public ExchangeInfo getExchangeInfo() {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/api/v3/exchangeInfo");
            request.setMethod(RestfulRequest.Method.GET);
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            ExchangeInfo exchangeInfoVar = new ExchangeInfo();
            exchangeInfoVar.timezone = rootObj.getString("timezone");
            exchangeInfoVar.serverTime = rootObj.getLong("serverTime");
            JsonWrapper obj = rootObj.getArray("rateLimits");
            obj.forEach(item -> {
                RateLimits rateLimitsVar = new RateLimits();
                rateLimitsVar.rateLimitType = item.getString("rateLimitType");
                rateLimitsVar.interval = item.getString("interval");
                rateLimitsVar.intervalNum = item.getLong("intervalNum");
                rateLimitsVar.limit = item.getLong("limit");
                if (exchangeInfoVar.rateLimits == null) {
                    exchangeInfoVar.rateLimits = new LinkedList<>();
                }
                exchangeInfoVar.rateLimits.add(rateLimitsVar);
            });
            JsonWrapper obj0 = rootObj.getArray("symbols");
            obj0.forEach(item1 -> {
                Symbol symbolVar = new Symbol();
                symbolVar.symbol = item1.getString("symbol");
                symbolVar.status = item1.getString("status");
                symbolVar.baseAsset = item1.getString("baseAsset");
                symbolVar.baseAssetPrecision = item1.getLong("baseAssetPrecision");
                symbolVar.quoteAsset = item1.getString("quoteAsset");
                symbolVar.quotePrecision = item1.getLong("quotePrecision");
                symbolVar.orderTypes = item1.getStringArray("orderTypes");
                symbolVar.icebergAllowed = item1.getBoolean("icebergAllowed");
                symbolVar.ocoAllowed = item1.getBoolean("ocoAllowed");
                if (exchangeInfoVar.symbols == null) {
                    exchangeInfoVar.symbols = new LinkedList<>();
                }
                exchangeInfoVar.symbols.add(symbolVar);
            });
            return exchangeInfoVar;
        }
    
        public Depth getDepth(String symbol, Long limit) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/api/v3/depth");
            request.setMethod(RestfulRequest.Method.GET);
            request.addQueryString("symbol", symbol);
            request.addQueryString("limit", Long.toString(limit));
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            Depth depthVar = new Depth();
            depthVar.lastUpdateId = rootObj.getLong("lastUpdateId");
            JsonWrapper obj = rootObj.getArray("bids");
            obj.forEach(item -> {
                DepthEntity depthEntityVar = new DepthEntity();
                depthEntityVar.price = item.getDecimal("[0]");
                depthEntityVar.qty = item.getDecimal("[1]");
                if (depthVar.bids == null) {
                    depthVar.bids = new LinkedList<>();
                }
                depthVar.bids.add(depthEntityVar);
            });
            JsonWrapper obj0 = rootObj.getArray("asks");
            obj0.forEach(item1 -> {
                DepthEntity depthEntityVar2 = new DepthEntity();
                depthEntityVar2.price = item1.getDecimal("[0]");
                depthEntityVar2.qty = item1.getDecimal("[1]");
                if (depthVar.asks == null) {
                    depthVar.asks = new LinkedList<>();
                }
                depthVar.asks.add(depthEntityVar2);
            });
            return depthVar;
        }
    
        public CandlestickDataList getCandlestickData(String symbol, String interval, Long startTime, Long endTime, Long limit) {
            RestfulRequest request = new RestfulRequest(this.option);
            request.setTarget("/api/v3/klines");
            request.setMethod(RestfulRequest.Method.GET);
            request.addQueryString("symbol", symbol);
            request.addQueryString("interval", interval);
            request.addQueryString("startTime", Long.toString(startTime));
            request.addQueryString("endTime", Long.toString(endTime));
            request.addQueryString("limit", Long.toString(limit));
            RestfulResponse response = RestfulConnection.query(request);
            JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
            CandlestickDataList candlestickDataListVar = new CandlestickDataList();
            rootObj.forEach(item -> {
                CandlestickData candlestickDataVar = new CandlestickData();
                candlestickDataVar.open = item.getDecimal("\\[0]");
                candlestickDataVar.high = item.getDecimal("\\[1]");
                if (candlestickDataListVar.data == null) {
                    candlestickDataListVar.data = new LinkedList<>();
                }
                candlestickDataListVar.data.add(candlestickDataVar);
            });
            return candlestickDataListVar;
        }
    }

    static public class CandlestickChannel {
        private final WebSocketOptions option;
        private final WebSocketConnection connection;
    
        public CandlestickChannel() {
            this.option = new WebSocketOptions();
            this.option.url = "wss://stream.binance.com:9443";
            this.connection = new WebSocketConnection(this.option);
        }
    
        public CandlestickChannel(WebSocketOptions option) {
            this();
            this.option.setFrom(option);
        }
    
        public void open(List<String> symbolList, String interval) {
            StringWrapper streams = new StringWrapper();
            streams.addStringArray(symbolList, true);
            streams.combineStringItems("", CEIUtils.stringReplace("@kline_%s", interval), "/");
            this.connection.connect(CEIUtils.stringReplace("/ws/%s", streams.toNormalString()));
        }
    
        public void setOnCandlestickHandler(IWebSocketCallback<CandlestickStream> onCandlestick) {
            WebSocketEvent onCandlestickEvent = new WebSocketEvent(true);
            onCandlestickEvent.setTrigger((msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                JsonChecker jsonChecker = new JsonChecker();
                jsonChecker.checkEqual("e", "kline", rootObj);
                return jsonChecker.complete();
            });
            onCandlestickEvent.setEvent((connection, msg) -> {
                JsonWrapper rootObj = JsonWrapper.parseFromString(msg.getString());
                CandlestickStream candlestickStreamVar = new CandlestickStream();
                candlestickStreamVar.symbol = rootObj.getString("s");
                onCandlestick.invoke(candlestickStreamVar);
            });
            this.connection.registerEvent(onCandlestickEvent);
        }
    }

    static public class Procedures {
    
        public static void restfulAuth(RestfulRequest request, RestfulOptions option) {
            request.addHeaderString("X-MBX-APIKEY", option.apiKey);
            String ts = CEIUtils.getNow("Unix_ms");
            request.addQueryString("timestamp", ts);
            String queryString = CEIUtils.combineQueryString(request, CEIUtils.Constant.NONE, "&");
            String postBody = CEIUtils.getRequestInfo(request, CEIUtils.Constant.POSTBODY, CEIUtils.Constant.NONE);
            StringWrapper buffer = new StringWrapper();
            buffer.appendStringItem(queryString);
            buffer.appendStringItem(postBody);
            buffer.combineStringItems("", "", "");
            byte[] hmac = CEIUtils.hmacsha256(buffer.toNormalString(), option.secretKey);
            String output = CEIUtils.encodeHex(hmac);
            request.addQueryString("signature", output);
        }
    }

}
