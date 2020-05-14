#pragma once

#include <vector>
#include <string>
#include "impl/decimal.h"


namespace binance {
    struct CandlestickStream {
        std::string symbol;
    };

    struct RateLimits {
        std::string rateLimitType;
        std::string interval;
        long intervalNum;
        long limit;
    };

    struct Symbol {
        std::string symbol;
        std::string status;
        std::string baseAsset;
        long baseAssetPrecision;
        std::string quoteAsset;
        long quotePrecision;
        std::vector<std::string> orderTypes;
        bool icebergAllowed;
        bool ocoAllowed;
    };

    struct CandlestickData {
        long openTime;
        Decimal open;
        Decimal high;
        Decimal low;
        Decimal close;
        Decimal volume;
        long closeTime;
        Decimal quoteAssetVolume;
        long numberOfTrades;
        Decimal takerBuyBaseAssetVolume;
        Decimal takerBuyQuoteAssetVolume;
    };

    struct CandlestickDataList {
        std::vector<CandlestickData> data;
    };

    struct AggregateTrade {
        long id;
        Decimal price;
        Decimal qty;
        long timestamp;
        long firstTradeId;
        long lastTradeId;
        bool isBuyerMaker;
        bool isBestMatch;
    };

    struct ExchangeInfo {
        std::string timezone;
        long serverTime;
        std::vector<RateLimits> rateLimits;
        std::vector<Symbol> symbols;
    };

    struct Timestamp {
        long serverTime;
    };

    struct Trade {
        long id;
        Decimal price;
        Decimal qty;
        Decimal quoteQty;
        long time;
        bool isBuyerMaker;
        bool isBestMatch;
    };

    struct TradeList {
        std::vector<Trade> data;
    };

    struct DepthEntity {
        Decimal price;
        Decimal qty;
    };

    struct Depth {
        long lastUpdateId;
        std::vector<DepthEntity> bids;
        std::vector<DepthEntity> asks;
    };

    struct AggregateTradeList {
        std::vector<AggregateTrade> data;
    };

    class MarketClient {
    public:
        Timestamp getTimestamp();
        AggregateTradeList getAggregateTrades(const std::string& symbol, const long& fromId, const long& startTime, const long& endTime, const long& limit);
        TradeList getTrade(const std::string& symbol, const long& limit);
        TradeList historicalTrades(const std::string& symbol, const long& limit, const long& fromId);
        ExchangeInfo getExchangeInfo();
        Depth getDepth(const std::string& symbol, const long& limit);
        CandlestickDataList getCandlestickData(const std::string& symbol, const std::string& interval, const long& startTime, const long& endTime, const long& limit);
    };

    class CandlestickChannel {
    };

