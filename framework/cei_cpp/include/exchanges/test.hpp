#pragma once

#include <vector>
#include <string>
#include "impl/decimal.h"


namespace test {
    struct Order {
        long orderId;
    };

    struct PriceEntity {
        Decimal price;
        Decimal volume;
    };

    struct InfoEntity {
        std::string name;
        std::string infoValue;
    };

    struct SimpleInfo {
        std::string name;
        long number;
        Decimal price;
        bool status;
    };

    struct LastTrade {
        Decimal price;
        Decimal volume;
        long timestamp;
        std::string symbol;
    };

    struct ModelValue {
        std::string name;
        long value;
    };

    struct ModelInfo {
        std::string name;
        ModelValue value;
    };

    struct PriceList {
        std::string name;
        std::vector<PriceEntity> prices;
    };

    struct InfoList {
        std::string name;
        std::vector<InfoEntity> values;
    };

    struct TradeEntity {
        Decimal price;
        Decimal volume;
    };

    struct HistoricalTrade {
        std::vector<TradeEntity> data;
    };

    class GetClient {
    public:
        SimpleInfo getSimpleInfo();
        ModelInfo getModelInfo();
        PriceList getPriceList();
        InfoList getInfoList();
        SimpleInfo getTestArray();
        SimpleInfo inputsByGet(const std::string& name, const long& number, const Decimal& price, const bool& status);
        std::string url(const std::string& input);
    };

    class PostClient {
    public:
        SimpleInfo postInputs(const std::string& thisU, const Decimal& price, const long& number, const bool& status);
        SimpleInfo authentication(const std::string& name, const long& number);
    };

    class WSClient {
    };

