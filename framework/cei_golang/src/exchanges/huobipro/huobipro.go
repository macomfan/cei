package huobipro

import (
    "../../impl"
    "fmt"
)

type SymbolsData struct {
    BaseCurrency    string
    QuoteCurrency   string
    PricePrecision  int64
    AmountPrecision int64
    SymbolPartition string
    Symbol          string
    State           string
    ValuePrecision  int64
    MinOrderAmt     float64
    MaxOrderAmt     float64
    MinOrderValue   float64
    LeverageRatio   int64
}

type CandlestickData struct {
    Id     int64
    Amount float64
    Count  int64
    Open   float64
    Close  float64
    Low    float64
    High   float64
    Vol    float64
}

type OrderID struct {
    Status string
    Data   int64
}

type Timestamp struct {
    Timestamp int64
}

type AggregatedMarketData struct {
    Id       int64
    Amount   float64
    Count    int64
    Open     float64
    Close    float64
    Low      float64
    High     float64
    Vol      float64
    BidPrice float64
    BidSize  float64
    AskPrice float64
    AskSize  float64
}

type Currencies struct {
    Status string
    Data   []string
}

type Quote struct {
    Price  float64
    Amount float64
}

type Depth struct {
    Status string
    Ch     string
    Ts     int64
    Bids   []Quote
    Asks   []Quote
}

type Trade struct {
    Id        int64
    TradeID   int64
    Price     float64
    Amount    float64
    Direction string
    Ts        int64
}

type Candlestick struct {
    Status string
    Data   []CandlestickData
}

type OrderUpdate struct {
    EventType       string
    Symbol          string
    OrderId         int64
    ClientOrderId   string
    OrderPrice      string
    OrderSize       string
    Type            string
    OrderStatus     string
    OrderCreateTime int64
    TradePrice      string
    TradeVolume     string
    TradeId         int64
    TradeTime       int64
    Aggressor       bool
    RemainAmt       string
    LastActTime     string
}

type AccountData struct {
    Id      int64
    State   string
    Type    string
    Subtype string
}

type Account struct {
    Status string
    Data   []AccountData
}

type Symbols struct {
    Status string
    Data   []SymbolsData
}

type LastTrade struct {
    Status   string
    Ch       string
    Ts       int64
    TsInTick int64
    IdInTick int64
    Data     []Trade
}

type BestQuote struct {
    Status        string
    Ch            string
    Ts            int64
    BestBidPrice  float64
    BestBidAmount float64
    BestAskPrice  float64
    BestAskAmount float64
}

type MarketClient struct {
    option *impl.RestfulOptions
}

func NewMarketClient(option *impl.RestfulOptions) *MarketClient {
    inst := new(MarketClient)
    if option != nil {
        inst.option = option
    } else {
        inst.option.Url = "https://api.huobi.so"
    }
    return inst
}

func (inst *MarketClient) GetTimestamp() (data Timestamp , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/v1/common/timestamp")
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    timestampVar := Timestamp{}
    timestampVar.Timestamp = rootObj.GetInt64("data")
    return timestampVar, nil
}

func (inst *MarketClient) GetSymbol() (data Symbols , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/v1/common/symbols")
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    symbolsVar := Symbols{}
    symbolsVar.Status = rootObj.GetString("status")
    obj := rootObj.GetArray("data")
    for _, item := range obj.Array() {
        symbolsDataVar := SymbolsData{}
        symbolsDataVar.BaseCurrency = item.GetString("base-currency")
        symbolsDataVar.QuoteCurrency = item.GetString("quote-currency")
        symbolsDataVar.PricePrecision = item.GetInt64("price-precision")
        symbolsDataVar.AmountPrecision = item.GetInt64("amount-precision")
        symbolsDataVar.SymbolPartition = item.GetString("symbol-partition")
        symbolsDataVar.Symbol = item.GetString("symbol")
        symbolsDataVar.State = item.GetString("state")
        symbolsDataVar.ValuePrecision = item.GetInt64("value-precision")
        symbolsDataVar.MinOrderAmt = item.GetFloat64("min-order-amt")
        symbolsDataVar.MaxOrderAmt = item.GetFloat64("max-order-amt")
        symbolsDataVar.MinOrderValue = item.GetFloat64("min-order-value")
        symbolsDataVar.LeverageRatio = item.GetInt64("leverage-ratio")
        symbolsVar.Data = append(symbolsVar.Data, symbolsDataVar)
    }
    return symbolsVar, nil
}

func (inst *MarketClient) GetCurrencies() (data Currencies , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/v1/common/currencys")
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    currenciesVar := Currencies{}
    currenciesVar.Status = rootObj.GetString("status")
    currenciesVar.Data = rootObj.GetStringArray("data")
    return currenciesVar, nil
}

type ArgsGetCandlestick struct {
    Symbol string
    Period string
    Size   int64
}

func (inst *MarketClient) GetCandlestick(args ArgsGetCandlestick) (data Candlestick , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/market/history/kline")
    request.SetMethod(impl.GET)
    request.AddQueryString("symbol", args.Symbol)
    request.AddQueryString("period", args.Period)
    request.AddQueryString("size", impl.ToString(args.Size))
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    candlestickVar := Candlestick{}
    candlestickVar.Status = rootObj.GetString("status")
    obj := rootObj.GetArray("data")
    for _, item := range obj.Array() {
        candlestickDataVar := CandlestickData{}
        candlestickDataVar.Id = item.GetInt64("id")
        candlestickDataVar.Amount = item.GetFloat64("amount")
        candlestickDataVar.Count = item.GetInt64("count")
        candlestickDataVar.Open = item.GetFloat64("open")
        candlestickDataVar.Close = item.GetFloat64("close")
        candlestickDataVar.Low = item.GetFloat64("low")
        candlestickDataVar.High = item.GetFloat64("high")
        candlestickDataVar.Vol = item.GetFloat64("vol")
        candlestickVar.Data = append(candlestickVar.Data, candlestickDataVar)
    }
    return candlestickVar, nil
}

func (inst *MarketClient) GetLastTrade() (data LastTrade , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/market/trade")
    request.SetMethod(impl.GET)
    request.AddQueryString("symbol", symbol)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    lastTradeVar := LastTrade{}
    lastTradeVar.Status = rootObj.GetString("status")
    lastTradeVar.Ts = rootObj.GetInt64("ts")
    lastTradeVar.Ch = rootObj.GetString("ch")
    obj := rootObj.GetObject("tick")
    lastTradeVar.TsInTick = obj.GetInt64("ts")
    lastTradeVar.IdInTick = obj.GetInt64("id")
    obj0 := obj.GetArray("data")
    for _, item := range obj0.Array() {
        tradeVar := Trade{}
        tradeVar.Amount = item.GetFloat64("amount")
        tradeVar.Price = item.GetFloat64("price")
        tradeVar.Ts = item.GetInt64("ts")
        tradeVar.Id = item.GetInt64("id")
        tradeVar.Direction = item.GetString("direction")
        tradeVar.TradeID = item.GetInt64("trade-id")
        lastTradeVar.Data = append(lastTradeVar.Data, tradeVar)
    }
    return lastTradeVar, nil
}

type ArgsGetMarketDepth struct {
    Symbol    string
    Depth     int64
    MergeType string
}

func (inst *MarketClient) GetMarketDepth(args ArgsGetMarketDepth) (data Depth , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/market/depth")
    request.SetMethod(impl.GET)
    request.AddQueryString("symbol", args.Symbol)
    request.AddQueryString("depth", impl.ToString(args.Depth))
    request.AddQueryString("type", args.MergeType)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    depthVar := Depth{}
    depthVar.Status = rootObj.GetString("status")
    depthVar.Ts = rootObj.GetInt64("ts")
    depthVar.Ch = rootObj.GetString("ch")
    obj := rootObj.GetObject("tick")
    obj0 := obj.GetArray("bids")
    for _, item := range obj0.Array() {
        quoteVar := Quote{}
        quoteVar.Price = item.GetFloat64("[0]")
        quoteVar.Amount = item.GetFloat64("[1]")
        depthVar.Bids = append(depthVar.Bids, quoteVar)
    }
    obj1 := obj.GetArray("asks")
    for _, item2 := range obj1.Array() {
        quoteVar3 := Quote{}
        quoteVar3.Price = item2.GetFloat64("[0]")
        quoteVar3.Amount = item2.GetFloat64("[1]")
        depthVar.Asks = append(depthVar.Asks, quoteVar3)
    }
    return depthVar, nil
}

func (inst *MarketClient) GetBestQuote() (data BestQuote , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/market/depth")
    request.SetMethod(impl.GET)
    request.AddQueryString("symbol", symbol)
    request.AddQueryString("depth", "5")
    request.AddQueryString("type", "step0")
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    bestQuoteVar := BestQuote{}
    bestQuoteVar.Status = rootObj.GetString("status")
    bestQuoteVar.Ts = rootObj.GetInt64("ts")
    obj := rootObj.GetObject("tick")
    obj0 := obj.GetObject("bids")
    obj1 := obj0.GetObject("[0]")
    bestQuoteVar.BestBidPrice = obj1.GetFloat64("[0]")
    bestQuoteVar.BestBidAmount = obj1.GetFloat64("[1]")
    obj2 := obj.GetObject("asks")
    obj3 := obj2.GetObject("[0]")
    bestQuoteVar.BestAskPrice = obj3.GetFloat64("[0]")
    bestQuoteVar.BestAskAmount = obj3.GetFloat64("[1]")
    bestQuoteVar.Ch = rootObj.GetString("ch")
    return bestQuoteVar, nil
}

func (inst *MarketClient) GetLatestAggregatedTicker() (data AggregatedMarketData , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/market/detail/merged")
    request.SetMethod(impl.GET)
    request.AddQueryString("symbol", symbol)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    aggregatedMarketDataVar := AggregatedMarketData{}
    obj := rootObj.GetObject("data")
    aggregatedMarketDataVar.Id = obj.GetInt64("id")
    aggregatedMarketDataVar.Amount = obj.GetFloat64("amount")
    aggregatedMarketDataVar.Count = obj.GetInt64("count")
    aggregatedMarketDataVar.Open = obj.GetFloat64("open")
    aggregatedMarketDataVar.Close = obj.GetFloat64("close")
    aggregatedMarketDataVar.Low = obj.GetFloat64("low")
    aggregatedMarketDataVar.High = obj.GetFloat64("high")
    aggregatedMarketDataVar.Vol = obj.GetFloat64("vol")
    obj0 := obj.GetObject("bid")
    aggregatedMarketDataVar.BidPrice = obj0.GetFloat64("[0]")
    aggregatedMarketDataVar.BidSize = obj0.GetFloat64("[1]")
    obj1 := obj.GetObject("ask")
    aggregatedMarketDataVar.AskPrice = obj1.GetFloat64("[0]")
    aggregatedMarketDataVar.AskSize = obj1.GetFloat64("[1]")
    return aggregatedMarketDataVar, nil
}


type TradingClient struct {
    option *impl.RestfulOptions
}

func NewTradingClient(option *impl.RestfulOptions) *TradingClient {
    inst := new(TradingClient)
    if option != nil {
        inst.option = option
    } else {
        inst.option.Url = "https://api.huobi.so"
    }
    return inst
}

type ArgsPlaceOrder struct {
    AccountId string
    Symbol    string
    OrderType string
    Amount    string
    Price     string
}

func (inst *TradingClient) PlaceOrder(args ArgsPlaceOrder) (data OrderID , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    postMsg := impl.JsonWrapper{}
    postMsg.AddJsonString("account-Id", args.AccountId)
    postMsg.AddJsonString("symbol", args.Symbol)
    postMsg.AddJsonString("orderType", args.OrderType)
    postMsg.AddJsonString("amount", args.Amount)
    postMsg.AddJsonString("price", args.Price)
    request.SetTarget("/v1/order/orders/place")
    request.SetMethod(impl.POST)
    request.SetPostBody(postMsg.ToJsonString())
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    orderIDVar := OrderID{}
    orderIDVar.Status = rootObj.GetString("status")
    orderIDVar.Data = rootObj.GetInt64("data")
    return orderIDVar, nil
}

func (inst *TradingClient) CancelOrder() (data OrderID , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget(fmt.Sprintf("/v1/order/orders/%s/submitcancel", impl.ToString(orderId)))
    request.SetMethod(impl.POST)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    orderIDVar := OrderID{}
    orderIDVar.Status = rootObj.GetString("status")
    orderIDVar.Data = rootObj.GetInt64("data")
    return orderIDVar, nil
}


type AccountClient struct {
    option *impl.RestfulOptions
}

func NewAccountClient(option *impl.RestfulOptions) *AccountClient {
    inst := new(AccountClient)
    if option != nil {
        inst.option = option
    } else {
        inst.option.Url = "https://api.huobi.so"
    }
    return inst
}

func (inst *AccountClient) GetAccounts() (data Account , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/v1/account/accounts")
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    accountVar := Account{}
    accountVar.Status = rootObj.GetString("status")
    accountDataVar := AccountData{}
    obj := rootObj.GetObject("data")
    accountDataVar.Id = obj.GetInt64("id")
    accountDataVar.State = obj.GetString("state")
    accountDataVar.Type = obj.GetString("type")
    accountDataVar.Subtype = obj.GetString("subtype")
    return accountVar, nil
}


type MarketChannelClient struct {
    option     *impl.WebSocketOptions
    connection *impl.WebSocketConnection
}

func NewMarketChannelClient(option *impl.WebSocketOptions) *MarketChannelClient {
    inst := new(MarketChannelClient)
    if option != nil {
        inst.option = option
    } else {
        inst.option.Url = "wss://api.huobi.pro/ws"
    }
    return inst
}

func (inst *MarketChannelClient) Open() {
    onAnyMessageEvent := impl.NewWebSocketEvent(true)
    inst.connection.RegisterEvent(onAnyMessageEvent)
    onPingEvent := impl.NewWebSocketEvent(true)
    inst.connection.RegisterEvent(onPingEvent)
    inst.connection.Connect("")
}

type ArgsSubscriptCandlestick struct {
    Symbol string
    Period string
}

func (inst *MarketChannelClient) SubscriptCandlestick(args ArgsSubscriptCandlestick, onCandlestick func (data CandlestickData)) {
    onCandlestickEvent := impl.NewWebSocketEvent(false)
    inst.connection.RegisterEvent(onCandlestickEvent)
    ts := impl.GetNow("Unix_ms")
    json := impl.JsonWrapper{}
    json.AddJsonString("sub", fmt.Sprintf("market.%s.kline.%s", args.Symbol, args.Period))
    json.AddJsonString("id", ts)
}

type ArgsRequestCandlestick struct {
    Symbol string
    Period string
}

func (inst *MarketChannelClient) RequestCandlestick(args ArgsRequestCandlestick, onCandlestick func (data CandlestickData)) {
    onCandlestickEvent := impl.NewWebSocketEvent(false)
    inst.connection.RegisterEvent(onCandlestickEvent)
    ts := impl.GetNow("Unix_ms")
    json := impl.JsonWrapper{}
    json.AddJsonString("req", fmt.Sprintf("market.%s.kline.%s", args.Symbol, args.Period))
    json.AddJsonString("id", ts)
}

type ArgsSubscriptMarketDepth struct {
    Symbol string
    TypeU  string
}

func (inst *MarketChannelClient) SubscriptMarketDepth(args ArgsSubscriptMarketDepth, onDepth func (data Depth)) {
    onDepthEvent := impl.NewWebSocketEvent(true)
    inst.connection.RegisterEvent(onDepthEvent)
    ts := impl.GetNow("Unix_ms")
    json := impl.JsonWrapper{}
    json.AddJsonString("sub", fmt.Sprintf("market.%s.depth.%s", args.Symbol, args.TypeU))
    json.AddJsonString("id", ts)
}

type ArgsRequestDepth struct {
    Symbol string
    TypeU  string
}

func (inst *MarketChannelClient) RequestDepth(args ArgsRequestDepth, onDepth func (data Depth)) {
    onDepthEvent := impl.NewWebSocketEvent(true)
    inst.connection.RegisterEvent(onDepthEvent)
    ts := impl.GetNow("Unix_ms")
    json := impl.JsonWrapper{}
    json.AddJsonString("sub", fmt.Sprintf("market.%s.depth.%s", args.Symbol, args.TypeU))
    json.AddJsonString("id", ts)
}


type AssetChannelClient struct {
    option     *impl.WebSocketOptions
    connection *impl.WebSocketConnection
}

func NewAssetChannelClient(option *impl.WebSocketOptions) *AssetChannelClient {
    inst := new(AssetChannelClient)
    if option != nil {
        inst.option = option
    } else {
        inst.option.Url = "wss://api.huobi.pro/ws/v2"
    }
    return inst
}

func (inst *AssetChannelClient) Open() {
    onPingEvent := impl.NewWebSocketEvent(true)
    inst.connection.RegisterEvent(onPingEvent)
    inst.connection.Connect("")
}

func (inst *AssetChannelClient) SubscriptOrder(onOrder func (data OrderUpdate)) {
    onOrderEvent := impl.NewWebSocketEvent(true)
    inst.connection.RegisterEvent(onOrderEvent)
    json := impl.JsonWrapper{}
    json.AddJsonString("action", "sub")
    json.AddJsonString("ch", fmt.Sprintf("orders#%s", symbol))
}


func restfulAuth(request *impl.RestfulRequest, option *impl.RestfulOptions) {
    timestamp := impl.GetNow("%Y:%M:%DT%h:%m:%s")
    request.AddQueryString("AccessKeyId", option.ApiKey)
    request.AddQueryString("SignatureMethod", "HmacSHA256")
    request.AddQueryString("SignatureVersion", "2")
    request.AddQueryString("Timestamp", timestamp)
    queryString := impl.CombineQueryString(request, impl.ASC, "&")
    method := impl.GetRequestInfo(request, impl.METHOD, impl.UPPERCASE)
    host := impl.GetRequestInfo(request, impl.HOST, impl.NONE)
    target := impl.GetRequestInfo(request, impl.TARGET, impl.NONE)
    buffer := impl.StringWrapper{}
    buffer.AppendStringItem(method)
    buffer.AppendStringItem(host)
    buffer.AppendStringItem(target)
    buffer.AppendStringItem(queryString)
    buffer.CombineStringItems("\\n")
    hmacsha256 := impl.HMACSHA256(buffer.ToString(), option.SecretKey)
    result := impl.Base64Encode(hmacsha256)
    request.AddQueryString("Signature", result)
}

