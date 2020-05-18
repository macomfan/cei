#include "exchanges/huobipro.hpp"
namespace huobipro {
    Timestamp MarketClient::getTimestamp() {
        RestfulRequest request{this->option};
        request.setTarget("/v1/common/timestamp");
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        Timestamp timestampVar{};
        timestampVar.timestamp = rootObj.getLong("data");
    }
    Symbols MarketClient::getSymbol() {
        RestfulRequest request{this->option};
        request.setTarget("/v1/common/symbols");
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        Symbols symbolsVar{};
        symbolsVar.status = rootObj.getString("status");
        JsonWrapper obj = rootObj.getArray("data");
        for (item : obj.Array()) {
            SymbolsData symbolsDataVar{};
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
            symbolsVar.data = symbolsVar.data.push_back(symbolsDataVar);
        }
    }
    Currencies MarketClient::getCurrencies() {
        RestfulRequest request{this->option};
        request.setTarget("/v1/common/currencys");
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        Currencies currenciesVar{};
        currenciesVar.status = rootObj.getString("status");
        currenciesVar.data = rootObj.getStringArray("data");
    }
    Candlestick MarketClient::getCandlestick(const std::string& symbol, const std::string& period, const long& size) {
        RestfulRequest request{this->option};
        request.setTarget("/market/history/kline");
        request.setMethod(RestfulRequest.Method.GET);
        request.addQueryString("symbol", symbol);
        request.addQueryString("period", period);
        request.addQueryString("size", Long.toString(size));
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        Candlestick candlestickVar{};
        candlestickVar.status = rootObj.getString("status");
        JsonWrapper obj = rootObj.getArray("data");
        for (item : obj.Array()) {
            CandlestickData candlestickDataVar{};
            candlestickDataVar.id = item.getLong("id");
            candlestickDataVar.amount = item.getDecimal("amount");
            candlestickDataVar.count = item.getLong("count");
            candlestickDataVar.open = item.getDecimal("open");
            candlestickDataVar.close = item.getDecimal("close");
            candlestickDataVar.low = item.getDecimal("low");
            candlestickDataVar.high = item.getDecimal("high");
            candlestickDataVar.vol = item.getDecimal("vol");
            candlestickVar.data = candlestickVar.data.push_back(candlestickDataVar);
        }
    }
    LastTrade MarketClient::getLastTrade(const std::string& symbol) {
        RestfulRequest request{this->option};
        request.setTarget("/market/trade");
        request.setMethod(RestfulRequest.Method.GET);
        request.addQueryString("symbol", symbol);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        LastTrade lastTradeVar{};
        lastTradeVar.status = rootObj.getString("status");
        lastTradeVar.ts = rootObj.getLong("ts");
        lastTradeVar.ch = rootObj.getString("ch");
        JsonWrapper obj = rootObj.getObject("tick");
        lastTradeVar.tsInTick = obj.getLong("ts");
        lastTradeVar.idInTick = obj.getLong("id");
        JsonWrapper obj0 = obj.getArray("data");
        for (item : obj0.Array()) {
            Trade tradeVar{};
            tradeVar.amount = item.getDecimal("amount");
            tradeVar.price = item.getDecimal("price");
            tradeVar.ts = item.getLong("ts");
            tradeVar.id = item.getLong("id");
            tradeVar.direction = item.getString("direction");
            tradeVar.tradeID = item.getLong("trade-id");
            lastTradeVar.data = lastTradeVar.data.push_back(tradeVar);
        }
    }
    Depth MarketClient::getMarketDepth(const std::string& symbol, const long& depth, const std::string& mergeType) {
        RestfulRequest request{this->option};
        request.setTarget("/market/depth");
        request.setMethod(RestfulRequest.Method.GET);
        request.addQueryString("symbol", symbol);
        request.addQueryString("depth", Long.toString(depth));
        request.addQueryString("type", mergeType);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        Depth depthVar{};
        depthVar.status = rootObj.getString("status");
        depthVar.ts = rootObj.getLong("ts");
        depthVar.ch = rootObj.getString("ch");
        JsonWrapper obj = rootObj.getObject("tick");
        JsonWrapper obj0 = obj.getArray("bids");
        for (item : obj0.Array()) {
            Quote quoteVar{};
            quoteVar.price = item.getDecimal("[0]");
            quoteVar.amount = item.getDecimal("[1]");
            depthVar.bids = depthVar.bids.push_back(quoteVar);
        }
        JsonWrapper obj1 = obj.getArray("asks");
        for (item2 : obj1.Array()) {
            Quote quoteVar3{};
            quoteVar3.price = item2.getDecimal("[0]");
            quoteVar3.amount = item2.getDecimal("[1]");
            depthVar.asks = depthVar.asks.push_back(quoteVar3);
        }
    }
    BestQuote MarketClient::getBestQuote(const std::string& symbol) {
        RestfulRequest request{this->option};
        request.setTarget("/market/depth");
        request.setMethod(RestfulRequest.Method.GET);
        request.addQueryString("symbol", symbol);
        request.addQueryString("depth", "5");
        request.addQueryString("type", "step0");
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        BestQuote bestQuoteVar{};
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
    }
    AggregatedMarketData MarketClient::getLatestAggregatedTicker(const std::string& symbol) {
        RestfulRequest request{this->option};
        request.setTarget("/market/detail/merged");
        request.setMethod(RestfulRequest.Method.GET);
        request.addQueryString("symbol", symbol);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        AggregatedMarketData aggregatedMarketDataVar{};
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
    }

    OrderID TradingClient::placeOrder(const std::string& accountId, const std::string& symbol, const std::string& orderType, const std::string& amount, const std::string& price) {
        RestfulRequest request{this->option};
        request.setTarget("/v1/order/orders/place");
        request.setMethod(RestfulRequest.Method.POST);
        request.setPostBody(postMsg.toJsonString());
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        OrderID orderIDVar{};
        orderIDVar.status = rootObj.getString("status");
        orderIDVar.data = rootObj.getLong("data");
    }
    OrderID TradingClient::cancelOrder(const long& orderId) {
        RestfulRequest request{this->option};
        request.setTarget(CEIUtils.stringReplace("/v1/order/orders/%s/submitcancel", Long.toString(orderId)));
        request.setMethod(RestfulRequest.Method.POST);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        OrderID orderIDVar{};
        orderIDVar.status = rootObj.getString("status");
        orderIDVar.data = rootObj.getLong("data");
    }

    Account AccountClient::getAccounts() {
        RestfulRequest request{this->option};
        request.setTarget("/v1/account/accounts");
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        Account accountVar{};
        accountVar.status = rootObj.getString("status");
        AccountData accountDataVar{};
        JsonWrapper obj = rootObj.getObject("data");
        accountDataVar.id = obj.getLong("id");
        accountDataVar.state = obj.getString("state");
        accountDataVar.type = obj.getString("type");
        accountDataVar.subtype = obj.getString("subtype");
    }



}
}
