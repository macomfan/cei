#pragma once

#include <vector>
#include <string>
#include "impl/decimal.h"


namespace huobipro {
    struct SymbolsData {
        std::string baseCurrency;
        std::string quoteCurrency;
        long pricePrecision;
        long amountPrecision;
        std::string symbolPartition;
        std::string symbol;
        std::string state;
        long valuePrecision;
        Decimal minOrderAmt;
        Decimal maxOrderAmt;
        Decimal minOrderValue;
        long leverageRatio;
    };

    struct TradeClearingUpdate {
        std::string symbol;
        long orderId;
        std::string tradePrice;
        std::string tradeVolume;
        std::string orderSide;
        std::string orderType;
        bool aggressor;
        long tradeId;
        std::string tradeTime;
        std::string transactFee;
        std::string feeDeduct;
        std::string feeDeductType;
    };

    struct CandlestickData {
        long id;
        Decimal amount;
        long count;
        Decimal open;
        Decimal close;
        Decimal low;
        Decimal high;
        Decimal vol;
    };

    struct OrderID {
        std::string status;
        long data;
    };

    struct Timestamp {
        long timestamp;
    };

    struct AggregatedMarketData {
        long id;
        Decimal amount;
        long count;
        Decimal open;
        Decimal close;
        Decimal low;
        Decimal high;
        Decimal vol;
        Decimal bidPrice;
        Decimal bidSize;
        Decimal askPrice;
        Decimal askSize;
    };

    struct Code {
        long code;
    };

    struct Currencies {
        std::string status;
        std::vector<std::string> data;
    };

    struct Quote {
        Decimal price;
        Decimal amount;
    };

    struct Depth {
        std::string status;
        std::string ch;
        long ts;
        std::vector<Quote> bids;
        std::vector<Quote> asks;
    };

    struct Trade {
        long id;
        long tradeID;
        Decimal price;
        Decimal amount;
        std::string direction;
        long ts;
    };

    struct Candlestick {
        std::string status;
        std::vector<CandlestickData> data;
    };

    struct OrderUpdate {
        std::string eventType;
        std::string symbol;
        long orderId;
        std::string clientOrderId;
        std::string orderPrice;
        std::string orderSize;
        std::string type;
        std::string orderStatus;
        long orderCreateTime;
        std::string tradePrice;
        std::string tradeVolume;
        long tradeId;
        long tradeTime;
        bool aggressor;
        std::string remainAmt;
        std::string lastActTime;
    };

    struct AccountData {
        long id;
        std::string state;
        std::string type;
        std::string subtype;
    };

    struct Account {
        std::string status;
        std::vector<AccountData> data;
    };

    struct Symbols {
        std::string status;
        std::vector<SymbolsData> data;
    };

    struct LastTrade {
        std::string status;
        std::string ch;
        long ts;
        long tsInTick;
        long idInTick;
        std::vector<Trade> data;
    };

    struct BestQuote {
        std::string status;
        std::string ch;
        long ts;
        Decimal bestBidPrice;
        Decimal bestBidAmount;
        Decimal bestAskPrice;
        Decimal bestAskAmount;
    };

    class MarketClient {
    public:
        Timestamp getTimestamp();
        Symbols getSymbol();
        Currencies getCurrencies();
        Candlestick getCandlestick(const std::string& symbol, const std::string& period, const long& size);
        LastTrade getLastTrade(const std::string& symbol);
        Depth getMarketDepth(const std::string& symbol, const long& depth, const std::string& mergeType);
        BestQuote getBestQuote(const std::string& symbol);
        AggregatedMarketData getLatestAggregatedTicker(const std::string& symbol);
    };

    class TradingClient {
    public:
        OrderID placeOrder(const std::string& accountId, const std::string& symbol, const std::string& orderType, const std::string& amount, const std::string& price);
        OrderID cancelOrder(const long& orderId);
    };

    class AccountClient {
    public:
        Account getAccounts();
    };

    class MarketChannelClient {
    };

    class AssetOrderV2ChannelClient {
    };

