package binance

import (
    "../../impl"
    "errors"
)

type Trade struct {
    Id           int64
    Price        float64
    Qty          float64
    QuoteQty     float64
    Time         int64
    IsBuyerMaker bool
    IsBestMatch  bool
}

type RateLimits struct {
    RateLimitType string
    Interval      string
    IntervalNum   int64
    Limit         int64
}

type Symbol struct {
    Symbol             string
    Status             string
    BaseAsset          string
    BaseAssetPrecision int64
    QuoteAsset         string
    QuotePrecision     int64
    OrderTypes         []string
    IcebergAllowed     bool
    OcoAllowed         bool
}

type CandlestickData struct {
    OpenTime                 int64
    Open                     float64
    High                     float64
    Low                      float64
    Close                    float64
    Volume                   float64
    CloseTime                int64
    QuoteAssetVolume         float64
    NumberOfTrades           int64
    TakerBuyBaseAssetVolume  float64
    TakerBuyQuoteAssetVolume float64
}

type CandlestickDataList struct {
    Data []CandlestickData
}

type DepthEntity struct {
    Price float64
    Qty   float64
}

type AggregateTrade struct {
    Id           int64
    Price        float64
    Qty          float64
    Timestamp    int64
    FirstTradeId int64
    LastTradeId  int64
    IsBuyerMaker bool
    IsBestMatch  bool
}

type AggregateTradeList struct {
    Data []AggregateTrade
}

type ExchangeInfo struct {
    Timezone   string
    ServerTime int64
    RateLimits []RateLimits
    Symbols    []Symbol
}

type TradeList struct {
    Data []Trade
}

type Timestamp struct {
    ServerTime int64
}

type Depth struct {
    LastUpdateId int64
    Bids         []DepthEntity
    Asks         []DepthEntity
}

type MarketClient struct {
    option *impl.RestfulOptions
}

func NewMarketClient(option *impl.RestfulOptions) *MarketClient {
    inst := new(MarketClient)
    if option != nil {
        inst.option = option
    } else {
        inst.option.Url = "https://api.binance.com"
    }
    return inst
}

func (inst *MarketClient) GetAggregateTrades(symbol string, fromId int64, startTime int64, endTime int64, limit int64) (data AggregateTradeList , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/api/v3/aggTrades")
    request.SetMethod(impl.GET)
    request.AddQueryString("symbol", symbol)
    request.AddQueryString("fromId", impl.ToString(fromId))
    request.AddQueryString("startTime", impl.ToString(startTime))
    request.AddQueryString("startTime", impl.ToString(endTime))
    request.AddQueryString("limit", impl.ToString(limit))
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    aggregateTradeListVar := AggregateTradeList{}
    for _, item := range rootObj.Array() {
        aggregateTradeVar := AggregateTrade{}
        aggregateTradeVar.Id = item.GetInt64("a")
        aggregateTradeVar.Price = item.GetFloat64("p")
        aggregateTradeVar.Qty = item.GetFloat64("q")
        aggregateTradeVar.FirstTradeId = item.GetInt64("f")
        aggregateTradeVar.LastTradeId = item.GetInt64("l")
        aggregateTradeVar.Timestamp = item.GetInt64("T")
        aggregateTradeVar.IsBuyerMaker = item.GetBool("m")
        aggregateTradeVar.IsBestMatch = item.GetBool("M")
        aggregateTradeListVar.Data = append(aggregateTradeListVar.Data, aggregateTradeVar)
    }
    return aggregateTradeListVar, nil
}

func (inst *MarketClient) GetCandlestickData(symbol string, interval string, startTime int64, endTime int64, limit int64) (data CandlestickDataList , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/api/v3/klines")
    request.SetMethod(impl.GET)
    request.AddQueryString("symbol", symbol)
    request.AddQueryString("interval", interval)
    request.AddQueryString("startTime", impl.ToString(startTime))
    request.AddQueryString("endTime", impl.ToString(endTime))
    request.AddQueryString("limit", impl.ToString(limit))
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    candlestickDataListVar := CandlestickDataList{}
    for _, item := range rootObj.Array() {
        candlestickDataVar := CandlestickData{}
        candlestickDataVar.Open = item.GetFloat64("\\[0]")
        candlestickDataVar.High = item.GetFloat64("\\[1]")
        candlestickDataListVar.Data = append(candlestickDataListVar.Data, candlestickDataVar)
    }
    return candlestickDataListVar, nil
}

func (inst *MarketClient) GetDepth(symbol string, limit int64) (data Depth , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/api/v3/depth")
    request.SetMethod(impl.GET)
    request.AddQueryString("symbol", symbol)
    request.AddQueryString("limit", impl.ToString(limit))
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    depthVar := Depth{}
    depthVar.LastUpdateId = rootObj.GetInt64("lastUpdateId")
    obj := rootObj.GetArray("bids")
    for _, item := range obj.Array() {
        depthEntityVar := DepthEntity{}
        depthEntityVar.Price = item.GetFloat64("[0]")
        depthEntityVar.Qty = item.GetFloat64("[1]")
        depthVar.Bids = append(depthVar.Bids, depthEntityVar)
    }
    obj0 := rootObj.GetArray("asks")
    for _, item1 := range obj0.Array() {
        depthEntityVar2 := DepthEntity{}
        depthEntityVar2.Price = item1.GetFloat64("[0]")
        depthEntityVar2.Qty = item1.GetFloat64("[1]")
        depthVar.Asks = append(depthVar.Asks, depthEntityVar2)
    }
    return depthVar, nil
}

func (inst *MarketClient) GetExchangeInfo() (data ExchangeInfo , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/api/v3/exchangeInfo")
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    exchangeInfoVar := ExchangeInfo{}
    exchangeInfoVar.Timezone = rootObj.GetString("timezone")
    exchangeInfoVar.ServerTime = rootObj.GetInt64("serverTime")
    obj := rootObj.GetArray("rateLimits")
    for _, item := range obj.Array() {
        rateLimitsVar := RateLimits{}
        rateLimitsVar.RateLimitType = item.GetString("rateLimitType")
        rateLimitsVar.Interval = item.GetString("interval")
        rateLimitsVar.IntervalNum = item.GetInt64("intervalNum")
        rateLimitsVar.Limit = item.GetInt64("limit")
        exchangeInfoVar.RateLimits = append(exchangeInfoVar.RateLimits, rateLimitsVar)
    }
    obj0 := rootObj.GetArray("symbols")
    for _, item1 := range obj0.Array() {
        symbolVar := Symbol{}
        symbolVar.Symbol = item1.GetString("symbol")
        symbolVar.Status = item1.GetString("status")
        symbolVar.BaseAsset = item1.GetString("baseAsset")
        symbolVar.BaseAssetPrecision = item1.GetInt64("baseAssetPrecision")
        symbolVar.QuoteAsset = item1.GetString("quoteAsset")
        symbolVar.QuotePrecision = item1.GetInt64("quotePrecision")
        symbolVar.OrderTypes = item1.GetStringArray("orderTypes")
        symbolVar.IcebergAllowed = item1.GetBool("icebergAllowed")
        symbolVar.OcoAllowed = item1.GetBool("ocoAllowed")
        exchangeInfoVar.Symbols = append(exchangeInfoVar.Symbols, symbolVar)
    }
    return exchangeInfoVar, nil
}

func (inst *MarketClient) HistoricalTrades(symbol string, limit int64, fromId int64) (data TradeList , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/api/v3/historicalTrades")
    request.SetMethod(impl.GET)
    request.AddQueryString("symbol", symbol)
    request.AddQueryString("limit", impl.ToString(limit))
    request.AddQueryString("fromId", impl.ToString(fromId))
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    tradeListVar := TradeList{}
    for _, item := range rootObj.Array() {
        tradeVar := Trade{}
        tradeVar.Id = item.GetInt64("id")
        tradeVar.Price = item.GetFloat64("price")
        tradeVar.Qty = item.GetFloat64("qty")
        tradeVar.QuoteQty = item.GetFloat64("quoteQty")
        tradeVar.Time = item.GetInt64("time")
        tradeVar.IsBuyerMaker = item.GetBool("isBuyerMaker")
        tradeVar.IsBestMatch = item.GetBool("isBestMatch")
        tradeListVar.Data = append(tradeListVar.Data, tradeVar)
    }
    return tradeListVar, nil
}

func (inst *MarketClient) GetTimestamp() (data Timestamp , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/api/v3/time")
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    timestampVar := Timestamp{}
    timestampVar.ServerTime = rootObj.GetInt64("serverTime")
    return timestampVar, nil
}

func (inst *MarketClient) GetTrade(symbol string, limit int64) (data TradeList , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/api/v3/trades")
    request.SetMethod(impl.GET)
    request.AddQueryString("symbol", symbol)
    request.AddQueryString("limit", impl.ToString(limit))
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    tradeListVar := TradeList{}
    for _, item := range rootObj.Array() {
        tradeVar := Trade{}
        tradeVar.Id = item.GetInt64("id")
        tradeVar.Price = item.GetFloat64("price")
        tradeVar.Qty = item.GetFloat64("qty")
        tradeVar.QuoteQty = item.GetFloat64("quoteQty")
        tradeVar.Time = item.GetInt64("time")
        tradeVar.IsBuyerMaker = item.GetBool("isBuyerMaker")
        tradeVar.IsBestMatch = item.GetBool("isBestMatch")
        tradeListVar.Data = append(tradeListVar.Data, tradeVar)
    }
    return tradeListVar, nil
}


