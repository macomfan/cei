#include "exchanges/binance.hpp"
namespace binance {
    Timestamp MarketClient::getTimestamp() {
        RestfulRequest request{this->option};
        request.setTarget("/api/v3/time");
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        Timestamp timestampVar{};
        timestampVar.serverTime = rootObj.getLong("serverTime");
    }
    AggregateTradeList MarketClient::getAggregateTrades(const std::string& symbol, const long& fromId, const long& startTime, const long& endTime, const long& limit) {
        RestfulRequest request{this->option};
        request.setTarget("/api/v3/aggTrades");
        request.setMethod(RestfulRequest.Method.GET);
        request.addQueryString("symbol", symbol);
        request.addQueryString("fromId", Long.toString(fromId));
        request.addQueryString("startTime", Long.toString(startTime));
        request.addQueryString("startTime", Long.toString(endTime));
        request.addQueryString("limit", Long.toString(limit));
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        AggregateTradeList aggregateTradeListVar{};
        for (item : rootObj.Array()) {
            AggregateTrade aggregateTradeVar{};
            aggregateTradeVar.id = item.getLong("a");
            aggregateTradeVar.price = item.getDecimal("p");
            aggregateTradeVar.qty = item.getDecimal("q");
            aggregateTradeVar.firstTradeId = item.getLong("f");
            aggregateTradeVar.lastTradeId = item.getLong("l");
            aggregateTradeVar.timestamp = item.getLong("T");
            aggregateTradeVar.isBuyerMaker = item.getBoolean("m");
            aggregateTradeVar.isBestMatch = item.getBoolean("M");
            aggregateTradeListVar.data = aggregateTradeListVar.data.push_back(aggregateTradeVar);
        }
    }
    TradeList MarketClient::getTrade(const std::string& symbol, const long& limit) {
        RestfulRequest request{this->option};
        request.setTarget("/api/v3/trades");
        request.setMethod(RestfulRequest.Method.GET);
        request.addQueryString("symbol", symbol);
        request.addQueryString("limit", Long.toString(limit));
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        TradeList tradeListVar{};
        for (item : rootObj.Array()) {
            Trade tradeVar{};
            tradeVar.id = item.getLong("id");
            tradeVar.price = item.getDecimal("price");
            tradeVar.qty = item.getDecimal("qty");
            tradeVar.quoteQty = item.getDecimal("quoteQty");
            tradeVar.time = item.getLong("time");
            tradeVar.isBuyerMaker = item.getBoolean("isBuyerMaker");
            tradeVar.isBestMatch = item.getBoolean("isBestMatch");
            tradeListVar.data = tradeListVar.data.push_back(tradeVar);
        }
    }
    TradeList MarketClient::historicalTrades(const std::string& symbol, const long& limit, const long& fromId) {
        RestfulRequest request{this->option};
        request.setTarget("/api/v3/historicalTrades");
        request.setMethod(RestfulRequest.Method.GET);
        request.addQueryString("symbol", symbol);
        request.addQueryString("limit", Long.toString(limit));
        request.addQueryString("fromId", Long.toString(fromId));
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        TradeList tradeListVar{};
        for (item : rootObj.Array()) {
            Trade tradeVar{};
            tradeVar.id = item.getLong("id");
            tradeVar.price = item.getDecimal("price");
            tradeVar.qty = item.getDecimal("qty");
            tradeVar.quoteQty = item.getDecimal("quoteQty");
            tradeVar.time = item.getLong("time");
            tradeVar.isBuyerMaker = item.getBoolean("isBuyerMaker");
            tradeVar.isBestMatch = item.getBoolean("isBestMatch");
            tradeListVar.data = tradeListVar.data.push_back(tradeVar);
        }
    }
    ExchangeInfo MarketClient::getExchangeInfo() {
        RestfulRequest request{this->option};
        request.setTarget("/api/v3/exchangeInfo");
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        ExchangeInfo exchangeInfoVar{};
        exchangeInfoVar.timezone = rootObj.getString("timezone");
        exchangeInfoVar.serverTime = rootObj.getLong("serverTime");
        JsonWrapper obj = rootObj.getArray("rateLimits");
        for (item : obj.Array()) {
            RateLimits rateLimitsVar{};
            rateLimitsVar.rateLimitType = item.getString("rateLimitType");
            rateLimitsVar.interval = item.getString("interval");
            rateLimitsVar.intervalNum = item.getLong("intervalNum");
            rateLimitsVar.limit = item.getLong("limit");
            exchangeInfoVar.rateLimits = exchangeInfoVar.rateLimits.push_back(rateLimitsVar);
        }
        JsonWrapper obj0 = rootObj.getArray("symbols");
        for (item1 : obj0.Array()) {
            Symbol symbolVar{};
            symbolVar.symbol = item1.getString("symbol");
            symbolVar.status = item1.getString("status");
            symbolVar.baseAsset = item1.getString("baseAsset");
            symbolVar.baseAssetPrecision = item1.getLong("baseAssetPrecision");
            symbolVar.quoteAsset = item1.getString("quoteAsset");
            symbolVar.quotePrecision = item1.getLong("quotePrecision");
            symbolVar.orderTypes = item1.getStringArray("orderTypes");
            symbolVar.icebergAllowed = item1.getBoolean("icebergAllowed");
            symbolVar.ocoAllowed = item1.getBoolean("ocoAllowed");
            exchangeInfoVar.symbols = exchangeInfoVar.symbols.push_back(symbolVar);
        }
    }
    Depth MarketClient::getDepth(const std::string& symbol, const long& limit) {
        RestfulRequest request{this->option};
        request.setTarget("/api/v3/depth");
        request.setMethod(RestfulRequest.Method.GET);
        request.addQueryString("symbol", symbol);
        request.addQueryString("limit", Long.toString(limit));
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        Depth depthVar{};
        depthVar.lastUpdateId = rootObj.getLong("lastUpdateId");
        JsonWrapper obj = rootObj.getArray("bids");
        for (item : obj.Array()) {
            DepthEntity depthEntityVar{};
            depthEntityVar.price = item.getDecimal("[0]");
            depthEntityVar.qty = item.getDecimal("[1]");
            depthVar.bids = depthVar.bids.push_back(depthEntityVar);
        }
        JsonWrapper obj0 = rootObj.getArray("asks");
        for (item1 : obj0.Array()) {
            DepthEntity depthEntityVar2{};
            depthEntityVar2.price = item1.getDecimal("[0]");
            depthEntityVar2.qty = item1.getDecimal("[1]");
            depthVar.asks = depthVar.asks.push_back(depthEntityVar2);
        }
    }
    CandlestickDataList MarketClient::getCandlestickData(const std::string& symbol, const std::string& interval, const long& startTime, const long& endTime, const long& limit) {
        RestfulRequest request{this->option};
        request.setTarget("/api/v3/klines");
        request.setMethod(RestfulRequest.Method.GET);
        request.addQueryString("symbol", symbol);
        request.addQueryString("interval", interval);
        request.addQueryString("startTime", Long.toString(startTime));
        request.addQueryString("endTime", Long.toString(endTime));
        request.addQueryString("limit", Long.toString(limit));
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        CandlestickDataList candlestickDataListVar{};
        for (item : rootObj.Array()) {
            CandlestickData candlestickDataVar{};
            candlestickDataVar.open = item.getDecimal("\\[0]");
            candlestickDataVar.high = item.getDecimal("\\[1]");
            candlestickDataListVar.data = candlestickDataListVar.data.push_back(candlestickDataVar);
        }
    }


}
}
